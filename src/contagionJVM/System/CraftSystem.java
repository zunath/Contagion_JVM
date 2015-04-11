package contagionJVM.System;

import contagionJVM.Entities.*;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.NWNX.NWNX_Funcs;
import contagionJVM.Repository.CraftRepository;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.Scheduler;
import org.nwnx.nwnx2.jvm.constants.Animation;
import org.nwnx.nwnx2.jvm.constants.DurationType;
import org.nwnx.nwnx2.jvm.constants.ObjectType;
import org.nwnx.nwnx2.jvm.constants.VfxComBlood;

import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class CraftSystem {

    private static final float CraftDelay = 14.0f;


    public static void CraftItem(final NWObject oPC, final NWObject device, final int blueprintID)
    {
        boolean isCrafting = NWScript.getLocalInt(oPC, "CRAFT_IS_CRAFTING") == 1;

        if(isCrafting)
        {
            return;
        }
        NWScript.setLocalInt(oPC, "CRAFT_IS_CRAFTING", 1);

        CraftRepository repo = new CraftRepository();
        CraftBlueprintEntity blueprint = repo.GetBlueprintByID(blueprintID);
        if(blueprint == null) return;

        boolean allComponentsFound = CheckItemCounts(oPC, device, blueprint.getComponents());

        if(allComponentsFound)
        {
            NWScript.applyEffectToObject(DurationType.TEMPORARY, NWScript.effectCutsceneImmobilize(), oPC, CraftDelay + 0.1f);
            Scheduler.assign(oPC, new Runnable() {
                @Override
                public void run() {
                    NWScript.clearAllActions(false);
                    NWScript.actionPlayAnimation(Animation.LOOPING_GET_MID, 1.0f, CraftDelay);
                }
            });
            Scheduler.delay(device, 1000 * NWScript.floatToInt(CraftDelay / 2.0f), new Runnable() {
                @Override
                public void run() {
                    NWScript.applyEffectToObject(DurationType.INSTANT, NWScript.effectVisualEffect(VfxComBlood.SPARK_MEDIUM, false), device, 0.0f);
                }
            });

            NWNX_Funcs.StartTimingBar(oPC, NWScript.floatToInt(CraftDelay * 1000), "");

            Scheduler.delay(oPC, 1000 * NWScript.floatToInt(1000 * (CraftDelay + 0.2f)), new Runnable() {
                @Override
                public void run() {
                    RunCreateItem(oPC, device, blueprintID);
                    NWScript.deleteLocalInt(oPC, "CRAFT_IS_CRAFTING");
                }
            });
        }
    }

    private static boolean CheckItemCounts(NWObject oPC, NWObject device, List<CraftComponentEntity> componentList)
    {
        boolean allComponentsFound = false;
        HashMap<String, Integer> components = new HashMap<>();

        for(CraftComponentEntity component : componentList)
        {
            components.put(component.getItemResref(), component.getQuantity());
        }

        NWObject tempStorage = NWScript.createObject(ObjectType.PLACEABLE, "", NWScript.getLocation(device), false, "");
        NWScript.setLocalObject(device, "CRAFT_TEMP_STORAGE", tempStorage);

        for(NWObject item : NWScript.getItemsInInventory(device)) {
            String resref = NWScript.getResRef(item);
            if (components.containsKey(resref) && components.get(resref) > 0) {
                components.put(resref, components.get(resref) - 1);

                NWScript.copyItem(item, tempStorage, true);
                NWScript.destroyObject(item, 0.0f);
            }

            int remainingQuantities = 0;
            for (int quantity : components.values())
                remainingQuantities += quantity;

            if (remainingQuantities <= 0) {
                allComponentsFound = true;
                break;
            }
        }

        if(!allComponentsFound)
        {
            for(NWObject item : NWScript.getItemsInInventory(tempStorage))
            {
                NWScript.copyItem(item, device, true);
                NWScript.destroyObject(item, 0.0f);
            }
            NWScript.deleteLocalInt(oPC, "CRAFT_IS_CRAFTING");
        }

        return allComponentsFound;
    }

    private static void RunCreateItem(NWObject oPC, NWObject device, int blueprintID)
    {
        NWObject tempStorage = NWScript.getLocalObject(device, "CRAFT_TEMP_STORAGE");
        PlayerGO pcGO = new PlayerGO(oPC);
        CraftRepository repo = new CraftRepository();
        PCBlueprintEntity pcBlueprint = repo.GetPCBlueprintByID(pcGO.getUUID(), blueprintID);
        CraftBlueprintEntity blueprint = pcBlueprint.getBlueprint();
        PCCraftEntity craft = repo.GetPCCraftByID(pcGO.getUUID(), blueprint.getCraftID());
        float chance = CalculateChanceToCreateItem(craft.getLevel(), blueprint.getLevel());
        Random random = new Random();
        float roll = random.nextFloat() * 100.0f;

        if(roll < chance)
        {
            // Failure...
            NWObject[] items = NWScript.getItemsInInventory(tempStorage);
            NWObject destroyItem = items[random.nextInt(items.length-1)];
            NWScript.destroyObject(destroyItem, 0.0f);

            for(NWObject item : items)
            {
                if(random.nextInt(100) > 20 && !item.equals(destroyItem))
                {
                    NWScript.copyItem(item, device, true);
                }
                NWScript.destroyObject(item, 0.0f);
            }

            NWScript.sendMessageToPC(oPC, "You failed to create that item...");
        }
        else
        {
            // Success!
            for(NWObject item : NWScript.getItemsInInventory(tempStorage))
            {
                NWScript.destroyObject(item, 0.0f);
            }

            NWScript.createItemOnObject(blueprint.getItemResref(), oPC, blueprint.getQuantity(), "");
            NWScript.sendMessageToPC(oPC, "You created " + blueprint.getQuantity() + "x " + blueprint.getItemName() + "!");
            GiveCraftingExperience(oPC, blueprint.getCraftID(), CalculateExperience(craft.getLevel(), blueprint.getLevel()));
        }

        NWScript.destroyObject(tempStorage, 0.0f);
    }


    private static float CalculateChanceToCreateItem(int pcLevel, int blueprintLevel)
    {
        int delta = pcLevel - blueprintLevel;
        float percentage = 0.0f;

        if (delta <= -5)
        {
            percentage = 0.0f;
        }
        else if(delta >= 5)
        {
            percentage = 95.0f;
        }
        else
        {
            switch (delta)
            {
                case -4:
                    percentage = 15.0f;
                    break;
                case -3:
                    percentage = 25.0f;
                    break;
                case -2:
                    percentage = 40.0f;
                    break;
                case -1:
                    percentage = 60.0f;
                    break;
                case 0:
                    percentage = 75.0f;
                    break;
                case 1:
                    percentage = 82.5f;
                    break;
                case 2:
                    percentage = 87.0f;
                    break;
                case 3:
                    percentage = 90.0f;
                    break;
                case 4:
                    percentage = 93.0f;
                    break;
            }
        }


        return percentage;
    }

    private static int CalculateExperience(int pcLevel, int blueprintLevel)
    {
        int exp = 0;
        int delta = pcLevel - blueprintLevel;

        if(delta <= -5) exp = 200;
        else if(delta >= 5) exp = 0;
        else
        {
            switch (delta)
            {
                case -4:
                    exp = 200;
                    break;
                case -3:
                    exp = 170;
                    break;
                case -2:
                    exp = 140;
                    break;
                case -1:
                    exp = 120;
                    break;
                case 0:
                    exp = 100;
                    break;
                case 1:
                    exp = 80;
                    break;
                case 2:
                    exp = 50;
                    break;
                case 3:
                    exp = 30;
                    break;
                case 4:
                    exp = 15;
                    break;
            }
        }

        return exp;
    }


    public static void GiveCraftingExperience(NWObject oPC, int craftID, int experience)
    {
        if(experience <= 0 || !NWScript.getIsPC(oPC) || NWScript.getIsDM(oPC)) return;

        PlayerGO pcGO = new PlayerGO(oPC);
        CraftRepository repo = new CraftRepository();
        PCCraftEntity entity = repo.GetPCCraftByID(pcGO.getUUID(), craftID);
        entity.setExperience(entity.getExperience() + experience);
        CraftLevelEntity level = repo.GetCraftLevelByLevel(craftID, entity.getLevel());
        CraftEntity craft = repo.GetCraftByID(craftID);
        long maxLevel = repo.GetCraftMaxLevel(craftID);

        NWScript.sendMessageToPC(oPC, "You earned " + experience + " " + craft.getName() + " experience.");
        if(entity.getLevel() >= maxLevel)
        {
            entity.setExperience(level.getExperience() - 1);
        }

        while(entity.getExperience() >= level.getExperience())
        {
            entity.setExperience(entity.getExperience() - level.getExperience());
            entity.setLevel(entity.getLevel() + 1);
            NWScript.sendMessageToPC(oPC, "You attained level " + entity.getLevel() + " in " + craft.getName() + "!");
        }

        repo.Save(entity);
    }

}
