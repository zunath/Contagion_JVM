package contagionJVM.System;

import contagionJVM.Constants;
import contagionJVM.Entities.PlayerEntity;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.Helper.MenuHelper;
import contagionJVM.Repository.PlayerRepository;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.Scheduler;
import org.nwnx.nwnx2.jvm.constants.Animation;
import org.nwnx.nwnx2.jvm.constants.DurationType;

public class FoodSystem {


    public static PlayerEntity RunHungerCycle(NWObject pc, PlayerEntity entity)
    {
        String sAreaTag = NWScript.getTag(NWScript.getArea(pc));
        if(sAreaTag.equals("ooc_area") || sAreaTag.equals("death_realm")) return entity;
        int hungerTick = entity.getCurrentHungerTick() - 1;

        if(hungerTick <= 0 && entity.getCurrentHunger() >= 0)
        {
            hungerTick = Constants.BaseHungerRate;
            entity.setCurrentHunger(entity.getCurrentHunger() - 1);

            if(entity.getCurrentHunger() == 70 || entity.getCurrentHunger() == 60 || entity.getCurrentHunger() == 50 || entity.getCurrentHunger() == 40)
            {
                NWScript.floatingTextStringOnCreature("You are hungry.", pc, false);
            }
            else if(entity.getCurrentHunger() == 30 || entity.getCurrentHunger() == 20 || entity.getCurrentHunger() <= 10)
            {
                NWScript.floatingTextStringOnCreature("You are starving!", pc, false);
            }

            if(entity.getCurrentHunger() <= 0)
            {
                NWScript.applyEffectToObject(DurationType.INSTANT, NWScript.effectDeath(false, true), pc, 0.0f);
            }
        }

        entity.setCurrentHungerTick(hungerTick);

        return entity;
    }

    public static void IncreaseHungerLevel(NWObject oPC, int amount)
    {
        if(!NWScript.getIsPC(oPC) || NWScript.getIsDM(oPC)) return;

        PlayerGO pcGO = new PlayerGO(oPC);
        PlayerRepository repo = new PlayerRepository();
        PlayerEntity entity = repo.getByUUID(pcGO.getUUID());
        entity.setCurrentHunger(entity.getCurrentHunger() + amount);
        if(entity.getCurrentHunger() > entity.getMaxHunger()) entity.setCurrentHunger(entity.getMaxHunger());

        Scheduler.assign(oPC, new Runnable() {
            @Override
            public void run() {
                NWScript.actionPlayAnimation(Animation.FIREFORGET_SALUTE, 1.0f, 0.0f);
            }
        });

        NWScript.sendMessageToPC(oPC, "Hunger: " + MenuHelper.BuildBar(entity.getCurrentHunger(), entity.getMaxHunger(), 100));
        repo.save(entity);
    }
}
