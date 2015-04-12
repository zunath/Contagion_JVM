package contagionJVM.Event;

import contagionJVM.GameObject.PlayerGO;
import contagionJVM.Helper.EffectHelper;
import contagionJVM.IScriptEventHandler;
import contagionJVM.NWNX.NWNX_Structs;
import org.nwnx.nwnx2.jvm.NWEffect;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

@SuppressWarnings("UnusedDeclaration")
public class TestEvent implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        NWObject oPC = NWScript.getLastUsedBy();
        PlayerGO pcGO = new PlayerGO(oPC);

        EffectHelper.ApplyEffectIcon(oPC, NWNX_Structs.EFFECT_ICON_WOUNDING, 12.0f);

        for(NWEffect effect : NWScript.getEffects(oPC))
        {
            NWScript.sendMessageToPC(oPC, "" +NWScript.getEffectType(effect));
        }

    }
}
