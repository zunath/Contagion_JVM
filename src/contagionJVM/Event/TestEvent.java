package contagionJVM.Event;

import contagionJVM.IScriptEventHandler;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.constants.Duration;

@SuppressWarnings("UnusedDeclaration")
public class TestEvent implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        NWObject oPC = NWScript.getLastUsedBy();

        NWScript.applyEffectToObject(Duration.TYPE_INSTANT, NWScript.effectDeath(false, true), oPC, 0.0f);

    }
}
