package contagionJVM.Item.Medical;

import contagionJVM.GameObject.PlayerGO;
import contagionJVM.IScriptEventHandler;
import contagionJVM.NWNX.NWNX_Events;
import contagionJVM.NWNX.NWNX_Funcs;
import contagionJVM.System.ProgressionSystem;
import org.nwnx.nwnx2.jvm.NWEffect;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.Scheduler;
import org.nwnx.nwnx2.jvm.constants.Animation;
import org.nwnx.nwnx2.jvm.constants.DurationType;
import org.nwnx.nwnx2.jvm.constants.EffectType;

@SuppressWarnings("unused")
public class MedicalSupplies implements IScriptEventHandler {
    @Override
    public void runScript(final NWObject oPC) {

        final NWObject oTarget = NWNX_Events.GetEventTarget();
        if(!NWScript.getIsPC(oTarget) || NWScript.getIsDM(oTarget))
        {
            NWScript.sendMessageToPC(oPC, "Only players may be targeted with this item.");
            return;
        }

        if(NWScript.getCurrentHitPoints(oTarget) >= NWScript.getMaxHitPoints(oTarget))
        {
            NWScript.sendMessageToPC(oPC, "Your target is not hurt.");
            return;
        }

        float distance = NWScript.getDistanceBetween(oPC, oTarget);
        if(distance > 3.5f)
        {
            NWScript.sendMessageToPC(oPC, "Your target is out of range.");
            return;
        }

        int skill = ProgressionSystem.GetPlayerSkillLevel(oPC, ProgressionSystem.SkillType_FIRST_AID);
        final float duration = 30.0f + (skill * 6.0f);
        final float delay = 12.0f - (skill * 0.5f);
        final NWObject item = NWNX_Events.GetEventItem();
        final int restoreAmount = 1 + NWScript.getLocalInt(item, "ENHANCED_AMOUNT");

        NWNX_Funcs.StartTimingBar(oPC, (int) delay, "");
        NWScript.sendMessageToPC(oPC, "You begin treating " + NWScript.getName(oTarget, false) + "'s wounds.");
        if(!oPC.equals(oTarget))
            NWScript.sendMessageToPC(oTarget, NWScript.getName(oPC, false) + " begins treating your wounds.");

        Scheduler.assign(oPC, new Runnable() {
            @Override
            public void run() {
                NWScript.setFacingPoint(NWScript.getPosition(oTarget));
                NWScript.actionPlayAnimation(Animation.LOOPING_GET_MID, 1.0f, delay);
                NWScript.setCommandable(false, oPC);
            }
        });

        Scheduler.delay(oPC, (int) (1000 * delay), new Runnable() {
            @Override
            public void run() {
                NWScript.setCommandable(true, oPC);
                float distance = NWScript.getDistanceBetween(oPC, oTarget);

                if(distance > 3.5f)
                {
                    NWScript.sendMessageToPC(oPC, "Your target is too far away.");
                    return;
                }
                PlayerGO targetGO = new PlayerGO(oTarget);
                targetGO.removeEffect(EffectType.REGENERATE);

                NWEffect regeneration = NWScript.effectRegenerate(restoreAmount, 6.0f);
                NWScript.applyEffectToObject(DurationType.TEMPORARY, regeneration, oTarget, duration);

                NWScript.destroyObject(item, 0.0f);
                NWScript.sendMessageToPC(oPC, "You successfully treat " + NWScript.getName(oTarget, false) + "'s wounds.");

            }
        });
    }
}
