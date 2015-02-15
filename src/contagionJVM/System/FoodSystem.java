package contagionJVM.System;

import contagionJVM.Constants;
import contagionJVM.Entities.PlayerEntity;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.constants.DurationType;

public class FoodSystem {


    public static PlayerEntity RunHungerCycle(NWObject pc, PlayerEntity entity)
    {
        String sAreaTag = NWScript.getTag(NWScript.getArea(pc));
        if(sAreaTag.equals("ooc_area") || sAreaTag.equals("death_realm")) return entity;
        entity.setCurrentHungerTick(entity.getCurrentHungerTick() - 1);

        if(entity.getCurrentHungerTick() <= 0)
        {
            entity.setCurrentHunger(entity.getCurrentHunger() - 1);
            entity.setCurrentThirst(entity.getCurrentThirst() - 1);
            entity.setCurrentHungerTick(Constants.BaseHungerRate);

            if(entity.getCurrentHunger() == 70 || entity.getCurrentHunger() == 60 || entity.getCurrentHunger() == 50 || entity.getCurrentHunger() == 40)
            {
                NWScript.floatingTextStringOnCreature("You are hungry.", pc, false);
            }
            else if(entity.getCurrentHunger() == 30 || entity.getCurrentHunger() == 20 || entity.getCurrentHunger() <= 10)
            {
                NWScript.floatingTextStringOnCreature("You are starving!", pc, false);
            }

            if(entity.getCurrentThirst() == 70 || entity.getCurrentThirst() == 60 || entity.getCurrentThirst() == 50 || entity.getCurrentThirst() == 40)
            {
                NWScript.floatingTextStringOnCreature("You are thirsty.", pc, false);
            }
            else if(entity.getCurrentThirst() == 30 || entity.getCurrentThirst() == 20 || entity.getCurrentThirst() <= 10)
            {
                NWScript.floatingTextStringOnCreature("You are dying of thirst!", pc, false);
            }

            if(entity.getCurrentHunger() <= 0 || entity.getCurrentThirst() <= 0)
            {
                NWScript.applyEffectToObject(DurationType.INSTANT, NWScript.effectDeath(false, true), pc, 0.0f);
            }
        }

        return entity;
    }

}
