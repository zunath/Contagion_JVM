package contagionJVM.Item.Medical;

import contagionJVM.Bioware.Position;
import contagionJVM.Enumerations.CustomEffectType;
import contagionJVM.GameObject.PlayerGO;
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
public class Bandage implements IScriptEventHandler {
    @Override
    public void runScript(final NWObject oPC) {
        final NWObject target = NWNX_Events.GetEventTarget();
        final PlayerGO pcGO = new PlayerGO(oPC);

        if(pcGO.isBusy())
        {
            NWScript.sendMessageToPC(oPC, "You are busy.");
            return;
        }

        if(!NWScript.getIsPC(target) || NWScript.getIsDM(target))
        {
            NWScript.sendMessageToPC(oPC, "Only bleeding players may be targeted with this item.");
            return;
        }

        if(!CustomEffectSystem.HasCustomEffect(target, CustomEffectType.Bleeding))
        {
            NWScript.sendMessageToPC(oPC, "Your target is not bleeding.");
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
        final float delay = 8.0f - (skill * 0.5f);

        NWScript.sendMessageToPC(oPC, "You begin bandaging " + NWScript.getName(target, false) + "'s wounds.");

        if(!oPC.equals(target))
            NWScript.sendMessageToPC(target, NWScript.getName(oPC, false) + " begins bandaging your wounds.");

        NWNX_Funcs.StartTimingBar(oPC, (int) delay, "");

        Scheduler.assign(oPC, new Runnable() {
            @Override
            public void run() {
                pcGO.setIsBusy(true);
                NWScript.setFacingPoint(NWScript.getPosition(target));
                NWScript.actionPlayAnimation(Animation.LOOPING_GET_MID, 1.0f, delay);
                NWScript.setCommandable(false, oPC);
            }
        });


        Scheduler.delay(oPC, (int) (delay * 1000), new Runnable() {
            @Override
            public void run() {
                pcGO.setIsBusy(false);

                float distance = NWScript.getDistanceBetween(oPC, target);
                NWScript.setCommandable(true, oPC);

                if(distance > 3.5f)
                {
                    NWScript.sendMessageToPC(oPC, "Your target is too far away.");
                    return;
                }

                CustomEffectSystem.RemoveCustomEffect(target, CustomEffectType.Bleeding);
                NWScript.destroyObject(item, 0.0f);

                NWScript.sendMessageToPC(oPC, "You successfully bandage " + NWScript.getName(target, false) + ".");
            }
        });
    }
}
