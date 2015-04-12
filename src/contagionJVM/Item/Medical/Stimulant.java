package contagionJVM.Item.Medical;

import contagionJVM.IScriptEventHandler;
import contagionJVM.NWNX.NWNX_Events;
import contagionJVM.System.ProgressionSystem;
import org.nwnx.nwnx2.jvm.NWEffect;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.constants.DurationType;

@SuppressWarnings("unused")
public class Stimulant implements IScriptEventHandler {
    @Override
    public void runScript(NWObject oPC) {
        NWObject item = NWNX_Events.GetEventItem();
        int attribute = NWScript.getLocalInt(item, "STIMULANT_TYPE");
        int skill = ProgressionSystem.GetPlayerSkillLevel(oPC, ProgressionSystem.SkillType_FIRST_AID);
        float duration = 60.0f * (skill * 6.0f);
        int power = 1 + NWScript.getLocalInt(item, "STIMULANT_POWER");

        NWEffect effect = NWScript.effectAbilityIncrease(attribute, power);
        NWScript.applyEffectToObject(DurationType.TEMPORARY, effect, oPC, duration);

        NWScript.destroyObject(item, 0.0f);
    }
}
