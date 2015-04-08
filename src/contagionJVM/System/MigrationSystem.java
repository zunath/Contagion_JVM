package contagionJVM.System;

import contagionJVM.Bioware.XP2;
import contagionJVM.Constants;
import contagionJVM.Entities.PCMigrationEntity;
import contagionJVM.Entities.PCMigrationItemEntity;
import contagionJVM.Entities.PlayerEntity;
import contagionJVM.GameObject.ItemGO;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.Helper.ColorToken;
import contagionJVM.NWNX.NWNX_Funcs;
import contagionJVM.Repository.PCMigrationRepository;
import contagionJVM.Repository.PlayerRepository;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.Scheduler;

import java.util.ArrayList;
import java.util.HashMap;

public class MigrationSystem {

    public static void OnAreaEnter(NWObject oPC)
    {
        if(!NWScript.getIsPC(oPC) ||
                NWScript.getIsDM(oPC) ||
                NWScript.getLocalInt(oPC, "MIGRATION_SYSTEM_LOGGED_IN_ONCE") == 1 ||
                NWScript.getTag(NWScript.getArea(oPC)).equals("ooc_area")) return;
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
                !NWScript.getLocalString(pcGO.GetDatabaseItem(), Constants.PCIDNumberVariable).equals(""))
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

            Scheduler.delay(oPC, 8000, new Runnable() {
                @Override
                public void run() {
                    NWScript.floatingTextStringOnCreature(ColorToken.Green() + "Your character has been updated! Please check near your feet to make sure no items were dropped during the migration." + ColorToken.End(), oPC, false);
                }
            });
        }
    }

    private static void ProcessItem(NWObject oPC, NWObject item, HashMap<String, PCMigrationItemEntity> itemMap, ArrayList<Integer> stripItemList)
    {
        ItemGO itemGO = new ItemGO(item);
        String resref = NWScript.getResRef(item);
        int quantity = NWScript.getItemStackSize(item);
        int baseItemTypeID = NWScript.getBaseItemType(item);
        PCMigrationItemEntity migrationItem = itemMap.get(resref);
        if(itemMap.containsKey(resref))
        {
            NWScript.destroyObject(item, 0.0f);
            if(!migrationItem.getNewResref().equals(""))
            {
                NWScript.createItemOnObject(migrationItem.getNewResref(), oPC, quantity, "");
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
        PlayerRepository playerRepo = new PlayerRepository();
        PlayerGO pcGO = new PlayerGO(oPC);
        PlayerEntity pcEntity = playerRepo.getByUUID(pcGO.getUUID());
        pcGO.setCreateDate(pcEntity.getCreateTimestamp());

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
        NWScript.createItemOnObject(Constants.PCDatabaseTag, oPC, 1, "");
    }

}
