package contagionJVM.System;

import contagionJVM.Data.ItemDTO;
import contagionJVM.Entities.LootTableEntity;
import contagionJVM.Entities.LootTableItemEntity;
import contagionJVM.Entities.PCSearchSiteEntity;
import contagionJVM.Entities.PCSearchSiteItemEntity;
import contagionJVM.GameObject.ItemGO;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.Helper.ColorToken;
import contagionJVM.Helper.LocalVariableHelper;
import contagionJVM.Repository.LootTableRepository;
import contagionJVM.Repository.SearchSiteRepository;
import org.joda.time.DateTime;
import org.nwnx.nwnx2.jvm.*;
import org.nwnx.nwnx2.jvm.constants.*;

import java.sql.Timestamp;
import java.util.Random;

public class SearchSystem {

    private static final String SearchSiteCopyResref = "srch_plc_copy";
    private static final String SearchSiteIDVariableName = "SearchSiteID";
    private static final String SearchSiteDCVariableName = "SearchSiteDC";
    private static final String SearchSiteLootTableVariableName = "SearchLootTable";
    private static final int ExtraSearchPerNumberLevels = 5;
    private static final int SearchLockTimeHours = 2;

    public static void OnChestClose(NWObject oChest)
    {
        NWObject[] items = NWScript.getItemsInInventory(oChest);

        for(NWObject item : items)
        {
            NWScript.destroyObject(item, 0.0f);
        }
    }

    public static void OnChestDisturbed(NWObject oChest)
    {
        NWObject oPC = NWScript.getLastDisturbed();
        if(!NWScript.getIsPC(oPC)) return;

        String pcName = NWScript.getName(oPC, false);
        NWObject oItem = NWScript.getInventoryDisturbItem();
        int disturbType = NWScript.getInventoryDisturbType();

        if(disturbType == InventoryDisturbType.ADDED)
        {
            NWScript.copyItem(oItem, oPC, true);
            NWScript.destroyObject(oItem, 0.0f);
        }
        else if(disturbType == InventoryDisturbType.REMOVED)
        {
            SaveChestInventory(oPC, oChest, false);
            String itemName = NWScript.getName(oItem, false);
            if(itemName.equals("")) itemName = "money";

            Scheduler.assign(oPC, new Runnable() {
                @Override
                public void run() {
                    NWScript.actionPlayAnimation(Animation.LOOPING_GET_LOW, 1.0f, 1.5f);
                }
            });
            NWScript.applyEffectToObject(DurationType.TEMPORARY, NWScript.effectCutsceneImmobilize(), oPC, 1.5f);

            // Notify party members in the vicinity
            NWObject[] players = NWScript.getPCs();
            for(NWObject pc : players)
            {
                if(NWScript.getDistanceBetween(oPC, pc) <= 20.0f &&
                        NWScript.getArea(pc).equals(NWScript.getArea(oPC)) &&
                        NWScript.getFactionEqual(pc, oPC))
                {
                    NWScript.sendMessageToPC(pc, pcName + " found " + itemName + ".");
                }
            }
        }
    }

    public static void OnChestOpen(NWObject oChest)
    {
        NWObject oPC = NWScript.getLastOpenedBy();
        if(!NWScript.getIsPC(oPC) || NWScript.getIsDM(oPC)) return;

        PlayerGO pcGO = new PlayerGO(oPC);
        SearchSiteRepository repo = new SearchSiteRepository();
        String resref = NWScript.getResRef(oChest);
        int chestID = NWScript.getLocalInt(oChest, SearchSiteIDVariableName);
        int skillRank = NWScript.getSkillRank(Skill.SEARCH, oPC, false);
        int numberOfSearches = (skillRank / ExtraSearchPerNumberLevels) + 1;
        PCSearchSiteEntity searchEntity = repo.GetSearchSiteByID(chestID, pcGO.getUUID());
        DateTime timeLock = new DateTime();

        if(numberOfSearches <= 0) numberOfSearches = 1;

        if(searchEntity != null)
        {
            timeLock = new DateTime(searchEntity.getUnlockDateTime());
        }

        if(resref.equals(SearchSiteCopyResref))
        {
            NWScript.setUseableFlag(oChest, false);
        }

        QuestSystem.SpawnQuestItems(oChest, oPC);

        if(timeLock.isBefore(DateTime.now().minus(1)) || searchEntity == null)
        {
            int dc = NWScript.getLocalInt(oChest, SearchSiteDCVariableName);

            for(int search = 1; search <= numberOfSearches; search++)
            {
                RunSearchCycle(oPC, oChest, dc);
                dc += NWScript.random(3) + 1;
            }

            SaveChestInventory(oPC, oChest, true);
        }
        else
        {
            for(PCSearchSiteItemEntity item : searchEntity.getSearchItems())
            {
                NWObject oItem = SCORCO.loadObject(item.getSearchItem(), NWScript.getLocation(oChest), oChest);

                // Prevent item duplication in containers
                if(NWScript.getHasInventory(oItem))
                {
                    NWObject[] containerItems = NWScript.getItemsInInventory(oItem);
                    for(NWObject containerItem : containerItems)
                    {
                        NWScript.destroyObject(containerItem, 0.0f);
                    }
                }
            }
        }
    }

    public static void OnChestUsed(NWObject oChest)
    {
        NWObject oPC = NWScript.getLastUsedBy();
        if(!NWScript.getIsPC(oPC) || NWScript.getIsDM(oPC)) return;

        NWLocation location = NWScript.getLocation(oChest);
        final NWObject oCopy = NWScript.createObject(ObjectType.PLACEABLE, SearchSiteCopyResref, location, false, "");
        NWScript.setName(oCopy, NWScript.getName(oChest, false));
        NWScript.setFacingPoint(NWScript.getPosition(oPC));

        LocalVariableHelper.CopyVariables(oChest, oCopy);

        Scheduler.assign(oPC, new Runnable() {
            @Override
            public void run() {
                NWScript.actionInteractObject(oCopy);
            }
        });
    }

    private static void SaveChestInventory(NWObject oPC, NWObject oChest, boolean resetTimeLock)
    {
        PlayerGO pcGO = new PlayerGO(oPC);
        SearchSiteRepository repo = new SearchSiteRepository();
        int chestID = NWScript.getLocalInt(oChest, SearchSiteIDVariableName);
        PCSearchSiteEntity entity = repo.GetSearchSiteByID(chestID, pcGO.getUUID());
        Timestamp lockTime = new Timestamp(DateTime.now().plusHours(SearchLockTimeHours).getMillis());
        if(entity != null)
        {
            if(resetTimeLock)
            {
                lockTime = entity.getUnlockDateTime();
            }
            repo.Delete(entity);
        }

        entity = new PCSearchSiteEntity();
        entity.setPcID(pcGO.getUUID());
        entity.setSearchSiteID(chestID);
        entity.setUnlockDateTime(lockTime);

        NWObject[] inventory = NWScript.getItemsInInventory(oChest);
        for(NWObject item : inventory)
        {
            PCSearchSiteItemEntity itemEntity = new PCSearchSiteItemEntity();
            itemEntity.setSearchItem(SCORCO.saveObject(item));
            itemEntity.setSearchSite(entity);

            entity.getSearchItems().add(itemEntity);
        }

        repo.Save(entity);
    }


    private static void RunSearchCycle(NWObject oPC, NWObject oChest, int iDC)
    {
        int lootTable = NWScript.getLocalInt(oChest, SearchSiteLootTableVariableName);
        int skill = NWScript.getSkillRank(Skill.SEARCH, oPC, false);
        if(skill > 10) skill = 10;
        else if(skill < 0) skill = 0;

        int roll = NWScript.random(20) + 1;
        int combinedRoll = roll + skill;
        if(roll + skill >= iDC)
        {
            NWScript.floatingTextStringOnCreature(ColorToken.SkillCheck() + "Search: *success*: (" + roll + " + " + skill + " = " + combinedRoll + " vs. DC: " + iDC + ")" + ColorToken.End(), oPC, false);
            ItemDTO spawnItem = PickResultItem(lootTable);

            if(!spawnItem.getResref().equals("") && spawnItem.getQuantity() > 0)
            {
                NWObject foundItem = NWScript.createItemOnObject(spawnItem.getResref(), oChest, spawnItem.getQuantity(), "");
                ItemGO itemGO = new ItemGO(foundItem);
                itemGO.setDurability(NWScript.random(70) + 1);
            }
        }
        else
        {
            NWScript.floatingTextStringOnCreature(ColorToken.SkillCheck() + "Search: *failure*: (" + roll + " + " + skill + " = " + combinedRoll + " vs. DC: " + iDC + ")" + ColorToken.End(), oPC, false);
        }
    }


    private static ItemDTO PickResultItem(int lootTable)
    {
        LootTableRepository repo = new LootTableRepository();
        LootTableEntity entity = repo.GetByLootTableID(lootTable);

        int totalWeight = 0;
        for(LootTableItemEntity item : entity.getLootTableItems())
        {
            totalWeight += item.getWeight();
        }

        int randomIndex = -1;
        double random = Math.random() * totalWeight;
        for(int i = 0; i < entity.getLootTableItems().size(); ++i)
        {
            random -= entity.getLootTableItems().get(i).getWeight();
            if(random <= 0.0d)
            {
                randomIndex = i;
                break;
            }
        }

        LootTableItemEntity itemEntity = entity.getLootTableItems().get(randomIndex);
        Random rand = new Random();
        int quantity = rand.nextInt(itemEntity.getMaxQuantity()) + 1;

        ItemDTO result = new ItemDTO();
        result.setQuantity(quantity);
        result.setResref(itemEntity.getResref());

        return result;
    }
}
