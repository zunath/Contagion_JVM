package contagionJVM.Event;

import contagionJVM.Enumerations.CustomEffectType;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.IScriptEventHandler;
import contagionJVM.NWNX.NWNX_Structs;
import contagionJVM.System.CustomEffectSystem;
import org.nwnx.nwnx2.jvm.NWEffect;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

@SuppressWarnings("UnusedDeclaration")
public class TestEvent implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        NWObject oPC = NWScript.getLastUsedBy();
        PlayerGO pcGO = new PlayerGO(oPC);

        CustomEffectSystem.ApplyCustomEffect(oPC, CustomEffectType.Bleeding, 3);
        CustomEffectSystem.ApplyCustomEffect(oPC, CustomEffectType.InfectionOverTime, 3);

        for(NWEffect effect : NWScript.getEffects(oPC))
        {
            NWScript.sendMessageToPC(oPC, "Effect Type = " + NWScript.getEffectType(effect));
        }
    }
}
