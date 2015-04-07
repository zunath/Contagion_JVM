package contagionJVM.Placeable.StructureSystem;


import contagionJVM.Entities.ConstructionSiteEntity;
import contagionJVM.IScriptEventHandler;
import contagionJVM.Repository.StructureRepository;
import contagionJVM.System.StructureSystem;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.Scheduler;
import org.nwnx.nwnx2.jvm.constants.BaseItem;
import org.nwnx.nwnx2.jvm.constants.InventorySlot;

@SuppressWarnings("unused")
public class ConstructionSite_OnAttacked implements IScriptEventHandler {
    @Override
    public void runScript(NWObject oSite) {

        final NWObject oPC = NWScript.getLastAttacker(oSite);
        int constructionSiteID = StructureSystem.GetConstructionSiteID(oSite);

        if(constructionSiteID <= 0)
        {
            NWScript.floatingTextStringOnCreature("You must select a blueprint before you can build.", oPC, false);
            Scheduler.assign(oPC, new Runnable() {
                @Override
                public void run() {
                    NWScript.clearAllActions(false);
                }
            });
            return;
        }

        NWObject weapon = NWScript.getLastWeaponUsed(oPC);
        int weaponType = NWScript.getBaseItemType(weapon);

        if(weaponType != BaseItem.LIGHTHAMMER)
        {
            NWScript.floatingTextStringOnCreature("A hammer must be equipped to build this structure.", oPC, false);
            Scheduler.assign(oPC, new Runnable() {
                @Override
                public void run() {
                    NWScript.clearAllActions(false);
                }
            });
            return;
        }

        // Offhand weapons don't contribute to building.
        if(NWScript.getItemInSlot(InventorySlot.LEFTHAND, oPC).equals(weapon))
        {
            return;
        }

        if(!StructureSystem.IsConstructionSiteValid(oSite))
        {
            NWScript.floatingTextStringOnCreature("Construction site is invalid. Please click the construction site to find out more.", oPC, false);
            Scheduler.assign(oPC, new Runnable() {
                @Override
                public void run() {
                    NWScript.clearAllActions(false);
                }
            });
            return;
        }

        StructureRepository repo = new StructureRepository();
        ConstructionSiteEntity entity = repo.GetConstructionSiteByID(constructionSiteID);
        NWObject wood = NWScript.getItemPossessedBy(oPC, "reo_wood");
        NWObject nails = NWScript.getItemPossessedBy(oPC, "reo_nails");
        NWObject metal = NWScript.getItemPossessedBy(oPC, "reo_metal");
        NWObject cloth = NWScript.getItemPossessedBy(oPC, "reo_cloth");
        NWObject leather = NWScript.getItemPossessedBy(oPC, "reo_leather");
        boolean foundResource = false;
        String updateMessage;

        if(entity.getWoodRequired() > 0 && !wood.equals(NWObject.INVALID))
        {
            entity.setWoodRequired(entity.getWoodRequired() - 1);
            NWScript.destroyObject(wood, 0.0f);
            updateMessage = "You need " + entity.getWoodRequired() + " wood to complete this project.";
            foundResource = true;
        }
        else if(entity.getNailsRequired() > 0 && !nails.equals(NWObject.INVALID))
        {
            entity.setNailsRequired(entity.getNailsRequired() - 1);
            NWScript.destroyObject(nails, 0.0f);
            updateMessage = "You need " + entity.getNailsRequired() + " nails to complete this project.";
            foundResource = true;
        }
        else if(entity.getMetalRequired() > 0 && !metal.equals(NWObject.INVALID))
        {
            entity.setMetalRequired(entity.getMetalRequired() - 1);
            NWScript.destroyObject(metal, 0.0f);
            updateMessage = "You need " + entity.getMetalRequired() + " metal to complete this project.";
            foundResource = true;
        }
        else if(entity.getClothRequired() > 0 && !cloth.equals(NWObject.INVALID))
        {
            entity.setClothRequired(entity.getClothRequired() - 1);
            NWScript.destroyObject(cloth, 0.0f);
            updateMessage = "You need " + entity.getClothRequired() + " cloth to complete this project.";
            foundResource = true;
        }
        else if(entity.getLeatherRequired() > 0 && !leather.equals(NWObject.INVALID))
        {
            entity.setLeatherRequired(entity.getLeatherRequired() - 1);
            NWScript.destroyObject(leather, 0.0f);
            updateMessage = "You need " + entity.getLeatherRequired() + " leather to complete this project.";
            foundResource = true;
        }
        else
        {
            updateMessage = "You lack the necessary resources...\n\n";
            if(entity.getWoodRequired() > 0) updateMessage += "Wood Required: " + entity.getWoodRequired() + "\n";
            if(entity.getNailsRequired() > 0) updateMessage += "Nails Required: " + entity.getNailsRequired() + "\n";
            if(entity.getMetalRequired() > 0) updateMessage += "Metal Required: " + entity.getMetalRequired() + "\n";
            if(entity.getClothRequired() > 0) updateMessage += "Cloth Required: " + entity.getClothRequired() + "\n";
            if(entity.getLeatherRequired() > 0) updateMessage += "Leather Required: " + entity.getLeatherRequired() + "\n";
        }

        final String messageCopy = updateMessage;
        Scheduler.delay(oPC, 750, new Runnable() {
            @Override
            public void run() {
                NWScript.sendMessageToPC(oPC, messageCopy);
            }
        });

        if(entity.getWoodRequired() <= 0 &&
                entity.getClothRequired() <= 0 &&
                entity.getNailsRequired() <= 0 &&
                entity.getMetalRequired() <= 0 &&
                entity.getLeatherRequired() <= 0)
        {
            StructureSystem.CompleteStructure(oSite);
        }
        else if(foundResource)
        {
            repo.Save(entity);
        }
        else
        {
            Scheduler.assign(oPC, new Runnable() {
                @Override
                public void run() {
                    NWScript.clearAllActions(false);
                }
            });
        }
    }
}
