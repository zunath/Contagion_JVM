package contagionJVM.Creature.Zombie;

import contagionJVM.Entities.ZombieClothesEntity;
import contagionJVM.IScriptEventHandler;
import contagionJVM.Repository.ZombieClothesRepository;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.Scheduler;
import org.nwnx.nwnx2.jvm.constants.DamagePower;
import org.nwnx.nwnx2.jvm.constants.DamageType;
import org.nwnx.nwnx2.jvm.constants.DurationType;
import org.nwnx.nwnx2.jvm.constants.InventorySlot;

@SuppressWarnings("unused")
public class Zombie_OnSpawn implements IScriptEventHandler {
    @Override
    public void runScript(NWObject oZombie) {
        String sName = NWScript.getName(oZombie, false);

        NWScript.executeScript("ss_treasure_spwn", oZombie);

        if(NWScript.getLocalInt(oZombie, "BYPASS_SPAWN_DAMAGE") == 0)
        {
            int randomDamage = (int)(NWScript.getMaxHitPoints(oZombie) * 0.33);
            randomDamage = NWScript.random(randomDamage);

            NWScript.applyEffectToObject(DurationType.INSTANT, NWScript.effectDamage(randomDamage, DamageType.MAGICAL, DamagePower.NORMAL), oZombie, 0.0f);
        }

        if(NWScript.getStringLeft(sName, 5).equals("(Tier"))
        {
            sName = NWScript.getSubString(sName, 10, NWScript.getStringLength(sName) - 10);
            NWScript.setName(oZombie, sName);
        }

        if(NWScript.getLocalInt(oZombie, "SPAWN_RANDOM_CLOTHES") == 1)
        {
            SpawnRandomClothes(oZombie);
        }

        Scheduler.assign(oZombie, new Runnable() {
            @Override
            public void run() {
                NWScript.setFacing((float)NWScript.random(361));
                NWScript.actionRandomWalk();
            }
        });
        Scheduler.flushQueues();

    }

    private void SpawnRandomClothes(NWObject oZombie)
    {
        ZombieClothesRepository repo = new ZombieClothesRepository();
        ZombieClothesEntity entity = repo.GetRandomClothes();

        if(entity != null)
        {
            NWObject oItem = NWScript.createItemOnObject(entity.getResref(), oZombie, 1, "");
            NWScript.setDroppableFlag(oItem, false);
            NWScript.setItemCursedFlag(oItem, true);
            NWScript.actionEquipItem(oItem, InventorySlot.CHEST);
        }
    }
}
