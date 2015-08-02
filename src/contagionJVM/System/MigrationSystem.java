package contagionJVM.System;

import contagionJVM.Constants;
import contagionJVM.Entities.PCMigrationEntity;
import contagionJVM.Entities.PCMigrationItemEntity;
import contagionJVM.Entities.PCOverflowItemEntity;
import contagionJVM.Entities.PlayerEntity;
import contagionJVM.GameObject.ItemGO;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.Helper.ColorToken;
import contagionJVM.Repository.OverflowItemRepository;
import contagionJVM.Repository.PCMigrationRepository;
import contagionJVM.Repository.PlayerRepository;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.SCORCO;
import org.nwnx.nwnx2.jvm.Scheduler;

import java.util.ArrayList;
import java.util.HashMap;

public class MigrationSystem {

    public static void OnAreaEnter(NWObject oPC)
    {
        if(!NWScript.getIsPC(oPC) ||
                NWScript.getIsDM(oPC) ||
                NWScript.getLocalInt(oPC, "MIGRATION_SYSTEM_LOGGED_IN_ONCE") == 1 ||
                !NWScript.getTag(NWScript.getArea(oPC)).equals("ooc_area")) return;
        PerformMigration(oPC);
    }


    private static void PerformMigration(final NWObject oPC)
    {
        PlayerGO pcGO = new PlayerGO(oPC);
        PlayerRepository playerRepo = new PlayerRepository();
        PCMigrationRepository migrationRepo = new PCMigrationRepository();
        PlayerEntity entity = playerRepo.getByUUID(pcGO.getUUID());

        // This piece of code migrates characters from MZS3 to the new version's database structure.
        if(entity == null &&
                (!NWScript.getLocalString(pcGO.GetDatabaseItem(), Constants.PCIDNumberVariable).equals("") ||
                NWScript.getLocalInt(pcGO.GetDatabaseItem(), Constants.PCIDNumberVariable) > 0))
        {
            entity = pcGO.createEntity();
            entity.setVersionNumber(0);
            playerRepo.save(entity);
        }

        for(int version = entity.getVersionNumber() + 1; version <= Constants.PlayerVersionNumber; version++)
        {
            HashMap<String, PCMigrationItemEntity> itemMap = new HashMap<>();
            ArrayList<Integer> stripItemList = new ArrayList<>();
            PCMigrationEntity migration = migrationRepo.GetByMigrationID(version);

            for(PCMigrationItemEntity item : migration.getPcMigrationItems())
            {
                if(!item.getCurrentResref().equals(""))
                {
                    itemMap.put(item.getCurrentResref(), item);
                }
                else if(item.getBaseItemTypeID() > -1 && item.isStripItemProperties())
                {
                    stripItemList.add(item.getBaseItemTypeID());
                }
            }

            for(int slot = 0; slot < Constants.NumberOfInventorySlots; slot++)
            {
                NWObject item = NWScript.getItemInSlot(slot, oPC);
                ProcessItem(oPC, item, itemMap, stripItemList);
            }

            for(NWObject item : NWScript.getItemsInInventory(oPC))
            {
                ProcessItem(oPC, item, itemMap, stripItemList);
            }

            RunCustomMigrationProcess(oPC, version);
            entity = playerRepo.getByUUID(pcGO.getUUID());
            entity.setVersionNumber(version);
            playerRepo.save(entity);
            NWScript.setLocalInt(oPC, "MIGRATION_SYSTEM_LOGGED_IN_ONCE", 1);

            OverflowItemRepository overflowRepo = new OverflowItemRepository();
            long overflowCount = overflowRepo.GetPlayerOverflowItemCount(pcGO.getUUID());

            final String message = ColorToken.Green() + "Your character has been updated!" +
                    (overflowCount > 0 ? " Items which could not be created have been placed into overflow inventory. You can access this from the rest menu." : "") +
                    ColorToken.End();
            Scheduler.delay(oPC, 8000, new Runnable() {
                @Override
                public void run() {
                    NWScript.floatingTextStringOnCreature(message, oPC, false);
                }
            });
        }
    }

    private static void ProcessItem(NWObject oPC, NWObject item, HashMap<String, PCMigrationItemEntity> itemMap, ArrayList<Integer> stripItemList)
    {
        PlayerGO pcGO = new PlayerGO(oPC);
        ItemGO itemGO = new ItemGO(item);
        String resref = NWScript.getResRef(item);
        int quantity = NWScript.getItemStackSize(item);
        int baseItemTypeID = NWScript.getBaseItemType(item);
        PCMigrationItemEntity migrationItem = itemMap.get(resref);
        OverflowItemRepository repo = new OverflowItemRepository();

        if(itemMap.containsKey(resref))
        {
            NWScript.destroyObject(item, 0.0f);
            if(!migrationItem.getNewResref().equals(""))
            {
                NWObject newItem = NWScript.createItemOnObject(migrationItem.getNewResref(), oPC, quantity, "");
                if(NWScript.getItemPossessor(newItem).equals(NWObject.INVALID))
                {
                    PCOverflowItemEntity overflow = new PCOverflowItemEntity();
                    overflow.setItemResref(NWScript.getResRef(newItem));
                    overflow.setItemTag(NWScript.getTag(newItem));
                    overflow.setItemName(NWScript.getName(newItem, false));
                    overflow.setItemObject(SCORCO.saveObject(newItem));
                    overflow.setPlayerID(pcGO.getUUID());
                    repo.Save(overflow);

                    NWScript.destroyObject(newItem, 0.0f);
                }
            }
        }
        else if(stripItemList.contains(baseItemTypeID))
        {
            itemGO.StripAllItemProperties();
        }
    }

    private static void RunCustomMigrationProcess(NWObject oPC, int versionNumber)
    {
        switch(versionNumber)
        {
            // Initial migration from MZS to Contagion
            case 1:
            {
                Migrate_ToVersion1(oPC);
                break;
            }
        }
    }

    private static void Migrate_ToVersion1(NWObject oPC)
    {
        PlayerGO pcGO = new PlayerGO(oPC);

        int level = NWScript.getLevelByPosition(1, oPC) +
                NWScript.getLevelByPosition(2, oPC) +
                NWScript.getLevelByPosition(3, oPC);
        int nwnEXP = NWScript.getXP(oPC);
        ProgressionSystem.InitializePlayer(oPC);
        int migrationEXP = 0;

        switch(level)
        {
            case 1: migrationEXP = 500; break;
            case 2: migrationEXP = 500; break;
            case 3:
                if(nwnEXP >= 4500) migrationEXP = 1250;
                else migrationEXP = 500;
                break;
            case 4:
                if(nwnEXP >= 8000) migrationEXP = 3500;
                else migrationEXP = 2250;
                break;
            case 5: migrationEXP = 5000; break;
        }

        ProgressionSystem.GiveExperienceToPC(oPC, migrationEXP);
        NWScript.destroyObject(pcGO.GetDatabaseItem(), 0.0f);
        NWObject databaseItem = NWScript.createItemOnObject(Constants.PCDatabaseTag, oPC, 1, "");
        pcGO.setCreateDate(databaseItem);
        NWScript.setLocalInt(databaseItem, "MIGRATED_TO_OAL", 1);

        for(NWObject inventory : NWScript.getItemsInInventory(oPC))
        {
            if(NWScript.getLocalInt(inventory, "HUNGER_RESTORE") > 0)
            {
                NWScript.setLocalInt(inventory, "SKIP_ANIMATION", 1);
                NWScript.setLocalString(inventory, "JAVA_SCRIPT", "Food");
            }

            if(NWScript.getLocalInt(inventory, "THIRST_RESTORE") > 0)
            {
                NWScript.destroyObject(inventory, 0.0f);
            }
        }

    }

}
