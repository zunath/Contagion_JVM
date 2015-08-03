package contagionJVM.Item.Medical;

import contagionJVM.Helper.ItemHelper;
import contagionJVM.IScriptEventHandler;
import contagionJVM.NWNX.NWNX_Events;
import contagionJVM.System.ProgressionSystem;
import org.nwnx.nwnx2.jvm.NWEffect;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.constants.DurationType;

@SuppressWarnings("unused")
public class Herb implements IScriptEventHandler {
    @Override
    public void runScript(NWObject oPC) {
        NWObject item = NWNX_Events.GetEventItem();
        String type = NWScript.getLocalString(item, "HERB_TYPE");
        float recoverPercentage = 0.0f;

        if(type.equals("Green"))
        {
            recoverPercentage = 10.0f;
        }
        else if(type.equals("Mixed"))
        {
            recoverPercentage = 30.0f;
        }
        if(recoverPercentage <= 0.0f) return;

        int skill = ProgressionSystem.GetPlayerSkillLevel(oPC, ProgressionSystem.SkillType_FIRST_AID);
        recoverPercentage += (skill * 2.0f);
        int hitPoints = (int)(NWScript.getMaxHitPoints(oPC) * recoverPercentage);

        NWEffect healEffect = NWScript.effectHeal(hitPoints);
        NWScript.applyEffectToObject(DurationType.INSTANT, healEffect, oPC, 0.0f);

        ItemHelper.ReduceItemStack(item);
    }
}
