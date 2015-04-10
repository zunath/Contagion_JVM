package contagionJVM.Item.Medical;

import contagionJVM.IScriptEventHandler;
import contagionJVM.NWNX.NWNX_Events;
import contagionJVM.System.ProgressionSystem;
import org.nwnx.nwnx2.jvm.NWEffect;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.constants.DurationType;

@SuppressWarnings("unused")
public class MedicalSupplies implements IScriptEventHandler {
    @Override
    public void runScript(NWObject oPC) {

        NWObject oTarget = NWNX_Events.GetEventTarget();
        if(!NWScript.getIsPC(oTarget) || NWScript.getIsDM(oTarget)) return;
        NWObject oItem = NWNX_Events.GetEventItem();
        int skillLevel = ProgressionSystem.GetPlayerSkillLevel(oPC, ProgressionSystem.SkillType_FIRST_AID);
        int bonusHP = skillLevel / 5;
        int roll = NWScript.random(20) + 1 + skillLevel;
        String userName = NWScript.getName(oPC, false);
        String targetName = NWScript.getName(oTarget, false);
        String userMessage;
        String targetMessage;
        int recoveryAmount = 0;
        float interval;
        float duration;

        if(roll > 25)
        {
            userMessage = "You were able to treat " + targetName + "'s wounds exceptionally well.";
            targetMessage = userName + " was able to treat your wounds exceptionally well.";
            recoveryAmount = 2;
            interval = 10.0f;
            duration = 60.0f;
        }
        else if(roll > 15)
        {
            userMessage = "You were able to treat " + targetName + "'s wounds fairly well.";
            targetMessage = userName + " was able to treat your wounds fairly well.";
            recoveryAmount = 1;
            interval = 15.0f;
            duration = 120.0f;
        }
        else if(roll > 7)
        {
            userMessage = "You were able to treat " + targetName + "'s wounds poorly.";
            targetMessage = userName + " was able to treat your wounds poorly.";
            recoveryAmount = 1;
            interval = 22.0f;
            duration = 110.0f;
        }
        else if(roll > 3)
        {
            userMessage = "You were barely able to treat " + targetName + "'s wounds.";
            targetMessage = userName + " was barely able to treat your wounds.";
            recoveryAmount = 1;
            interval = 30.0f;
            duration = 120.0f;
        }
        else
        {
            userMessage = "You failed to treat " + targetName + "'s wounds.";
            targetMessage = userName + " failed to treat your wounds.";
            interval = 0.0f;
            duration = 0.0f;
        }


        NWScript.sendMessageToPC(oPC, userMessage);
        NWScript.sendMessageToPC(oTarget, targetMessage);

        if(recoveryAmount > 0)
        {
            NWEffect regen = NWScript.effectRegenerate(recoveryAmount + NWScript.random(bonusHP), interval);
            NWScript.applyEffectToObject(DurationType.TEMPORARY, regen, oTarget, duration);
        }

        NWScript.destroyObject(oItem, 0.0f);

    }
}
