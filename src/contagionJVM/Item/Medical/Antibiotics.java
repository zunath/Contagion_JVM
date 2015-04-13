package contagionJVM.Item.Medical;

import contagionJVM.Enumerations.CustomEffectType;
import contagionJVM.IScriptEventHandler;
import contagionJVM.NWNX.NWNX_Events;
import contagionJVM.NWNX.NWNX_Funcs;
import contagionJVM.System.CustomEffectSystem;
import contagionJVM.System.ProgressionSystem;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.Scheduler;
import org.nwnx.nwnx2.jvm.constants.Animation;

@SuppressWarnings("unused")
public class Antibiotics implements IScriptEventHandler {
    @Override
    public void runScript(final NWObject oPC) {
        final NWObject target = NWNX_Events.GetEventTarget();
        if(!NWScript.getIsPC(target) || NWScript.getIsDM(target))
        {
            NWScript.sendMessageToPC(oPC, "Only players who are suffering from an infection may be targeted with this item.");
            return;
        }

        if(!CustomEffectSystem.HasCustomEffect(target, CustomEffectType.InfectionOverTime))
        {
            NWScript.sendMessageToPC(oPC, "Your target is not suffering from an infection.");
            return;
        }
        float distance = NWScript.getDistanceBetween(oPC, target);
        if(distance > 3.5f)
        {
            NWScript.sendMessageToPC(oPC, "Your target is too far away.");
            return;
        }

        final NWObject item = NWNX_Events.GetEventItem();
        int skill = ProgressionSystem.GetPlayerSkillLevel(oPC, ProgressionSystem.SkillType_FIRST_AID);
        final float delay = 7.0f - (skill * 0.5f);


        NWScript.sendMessageToPC(oPC, "You begin administering antibiotics to " + NWScript.getName(target, false) + ".");

        if(!oPC.equals(target))
            NWScript.sendMessageToPC(target, NWScript.getName(oPC, false) + " begins administering antibiotics to you..");

        NWNX_Funcs.StartTimingBar(oPC, (int) delay, "");

        Scheduler.assign(oPC, new Runnable() {
            @Override
            public void run() {
                NWScript.setFacingPoint(NWScript.getPosition(target));
                NWScript.actionPlayAnimation(Animation.LOOPING_GET_MID, 1.0f, delay);
                NWScript.setCommandable(false, oPC);
            }
        });

        Scheduler.delay(oPC, (int) (delay * 1000), new Runnable() {
            @Override
            public void run() {

                float distance = NWScript.getDistanceBetween(oPC, target);
                NWScript.setCommandable(true, oPC);

                if(distance > 3.5f)
                {
                    NWScript.sendMessageToPC(oPC, "Your target is too far away.");
                    return;
                }

                CustomEffectSystem.RemoveCustomEffect(target, CustomEffectType.InfectionOverTime);
                NWScript.destroyObject(item, 0.0f);

                NWScript.sendMessageToPC(oPC, "You successfully administer antibiotics to " + NWScript.getName(target, false) + ".");
            }
        });
    }
}
