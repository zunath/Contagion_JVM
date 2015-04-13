package contagionJVM.Item.Medical;

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

@SuppressWarnings("unused")
public class Stimulant implements IScriptEventHandler {
    @Override
    public void runScript(final NWObject oPC) {
        if(NWScript.getIsDM(oPC) || !NWScript.getIsPC(oPC)) return;

        final NWObject item = NWNX_Events.GetEventItem();
        final int attribute = NWScript.getLocalInt(item, "STIMULANT_TYPE");
        int skill = ProgressionSystem.GetPlayerSkillLevel(oPC, ProgressionSystem.SkillType_FIRST_AID);
        final float duration = 60.0f * (skill * 6.0f);
        final int power = 1 + NWScript.getLocalInt(item, "STIMULANT_POWER");
        final float delay = 8.0f - (skill * 0.5f);

        NWNX_Funcs.StartTimingBar(oPC, (int) delay, "");

        Scheduler.assign(oPC, new Runnable() {
            @Override
            public void run() {
                NWScript.actionPlayAnimation(Animation.LOOPING_GET_MID, 1.0f, delay);
                NWScript.setCommandable(false, oPC);
            }
        });

        Scheduler.delay(oPC, (int) (delay * 1000), new Runnable() {
            @Override
            public void run() {
                NWScript.setCommandable(false, oPC);

                NWEffect effect = NWScript.effectAbilityIncrease(attribute, power);
                NWScript.applyEffectToObject(DurationType.TEMPORARY, effect, oPC, duration);

                NWScript.destroyObject(item, 0.0f);
            }
        });


    }
}
