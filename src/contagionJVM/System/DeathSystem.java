package contagionJVM.System;

import contagionJVM.Entities.PCCorpseEntity;
import contagionJVM.Entities.PCCorpseItemEntity;
import contagionJVM.Repository.PCCorpseRepository;
import org.nwnx.nwnx2.jvm.*;
import org.nwnx.nwnx2.jvm.constants.DurationType;
import org.nwnx.nwnx2.jvm.constants.InventoryDisturbType;
import org.nwnx.nwnx2.jvm.constants.ObjectType;

import java.util.List;

public class DeathSystem {
    // Resref and tag of the player corpse placeable
    private static final String CorpsePlaceableResref = "pc_corpse";

// Message which displays on the Respawn pop up menu
    private static final String  RespawnMessage = "You have died. You can wait for another player to revive you or give up and permanently go to the death realm.";


    public static void OnModuleLoad()
    {
        PCCorpseRepository repo = new PCCorpseRepository();
        List<PCCorpseEntity> entities = repo.GetAll();

        for(PCCorpseEntity entity : entities)
        {
            NWObject area = NWScript.getObjectByTag(entity.getAreaTag(), 0);
            NWVector position = new NWVector(entity.getPositionX(), entity.getPositionY(), entity.getPositionZ());
            NWLocation location = NWScript.location(area, position, entity.getOrientation());
            NWObject corpse = NWScript.createObject(ObjectType.PLACEABLE, CorpsePlaceableResref, location, false, "");
            NWScript.setName(corpse, entity.getName());
            NWScript.setDescription(corpse, entity.getName(), true);

            for(PCCorpseItemEntity item : entity.getCorpseItems())
            {
                SCORCO.loadObject(item.getItem(), location, corpse);
            }
        }
    }

    public static void OnPlayerDeath()
    {
        final NWObject oPC = NWScript.getLastPlayerDied();
        String corpseName = NWScript.getName(oPC, false) + "'s Corpse";
        NWObject oHostileActor = NWScript.getLastHostileActor(oPC);
        NWLocation location = NWScript.getLocation(oPC);
        boolean hasItems = false;

        for(NWObject oMember : NWScript.getFactionMembers(oHostileActor, false))
        {
            NWScript.clearPersonalReputation(oPC, oMember);
        }

        NWScript.popUpDeathGUIPanel(oPC, true, true, 0, RespawnMessage);

        NWObject corpse = NWScript.createObject(ObjectType.PLACEABLE, CorpsePlaceableResref, location, false, "");
        PCCorpseEntity entity = new PCCorpseEntity();
        entity.setAreaTag(NWScript.getTag(location.getArea()));
        entity.setName(corpseName);
        entity.setOrientation(location.getFacing());
        entity.setPositionX(location.getX());
        entity.setPositionY(location.getY());
        entity.setPositionZ(location.getZ());

        if(NWScript.getGold(oPC) > 0)
        {
            Scheduler.assign(corpse, new Runnable() {
                @Override
                public void run() {
                    NWScript.takeGoldFromCreature(NWScript.getGold(oPC), oPC, false);
                }
            });

            hasItems = true;
        }

        for(NWObject item : NWScript.getItemsInInventory(oPC))
        {
            if(!NWScript.getItemCursedFlag(item))
            {
                NWScript.copyItem(item, corpse, true);
                NWScript.destroyObject(item, 0.0f);
                hasItems = true;
            }
        }

        if(!hasItems)
        {
            NWScript.destroyObject(corpse, 0.0f);
            return;
        }

        NWScript.setName(corpse, corpseName);
        NWScript.setDescription(corpse, corpseName, true);

        for(NWObject corpseItem : NWScript.getItemsInInventory(corpse))
        {
            PCCorpseItemEntity corpseItemEntity = new PCCorpseItemEntity();
            byte[] data = SCORCO.saveObject(corpseItem);
            corpseItemEntity.setItem(data);
            corpseItemEntity.setCorpse(entity);
            entity.getCorpseItems().add(corpseItemEntity);
        }

        PCCorpseRepository repo = new PCCorpseRepository();
        repo.Save(entity);
        NWScript.setLocalInt(corpse, "CORPSE_ID", entity.getPcCorpseID());
        Scheduler.flushQueues();
    }

    public static void OnPlayerRespawn()
    {
        NWObject oPC = NWScript.getLastRespawnButtonPresser();

        NWScript.applyEffectToObject(DurationType.INSTANT, NWScript.effectResurrection(), oPC, 0.0f);
        NWScript.applyEffectToObject(DurationType.INSTANT, NWScript.effectHeal(1), oPC, 0.0f);

        Scheduler.assign(oPC, new Runnable() {
            @Override
            public void run() {
                NWLocation lLocation = NWScript.getLocation(NWScript.getWaypointByTag("DEATH_WP"));
                NWScript.actionJumpToLocation(lLocation);
            }
        });

        Scheduler.flushQueues();
    }

    public static void OnCorpseDisturb(NWObject corpse)
    {
        NWObject oPC = NWScript.getLastDisturbed();

        if(!NWScript.getIsPC(oPC)) return;

        int corpseID = NWScript.getLocalInt(corpse, "CORPSE_ID");
        NWObject oItem = NWScript.getInventoryDisturbItem();
        int disturbType = NWScript.getInventoryDisturbType();

        if(disturbType == InventoryDisturbType.ADDED)
        {
            NWScript.actionGiveItem(oItem, oPC);
            NWScript.floatingTextStringOnCreature("You cannot put items into corpses.", oPC, false);
        }
        else
        {
            PCCorpseRepository repo = new PCCorpseRepository();
            PCCorpseEntity entity = repo.GetByID(corpseID);

            entity.getCorpseItems().clear();
            for(NWObject corpseItem : NWScript.getItemsInInventory(corpse))
            {
                PCCorpseItemEntity corpseItemEntity = new PCCorpseItemEntity();
                byte[] data = SCORCO.saveObject(corpseItem);
                corpseItemEntity.setItem(data);
                entity.getCorpseItems().add(corpseItemEntity);
            }

            repo.Save(entity);
        }


        Scheduler.flushQueues();
    }

    public static void OnCorpseClose(NWObject corpse)
    {
        NWObject[] items = NWScript.getItemsInInventory(corpse);
        if(items.length <= 0)
        {
            int corpseID = NWScript.getLocalInt(corpse, "CORPSE_ID");
            PCCorpseRepository repo = new PCCorpseRepository();
            PCCorpseEntity entity = repo.GetByID(corpseID);
            repo.Delete(entity);
        }
    }
}
