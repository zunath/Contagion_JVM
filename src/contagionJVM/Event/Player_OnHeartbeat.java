package contagionJVM.Event;

import contagionJVM.IScriptEventHandler;
import contagionJVM.System.EffectSystem;
import org.nwnx.nwnx2.jvm.NWObject;

@SuppressWarnings("unused")
public class Player_OnHeartbeat implements IScriptEventHandler {
    @Override
    public void runScript(NWObject oPC) {
        EffectSystem.OnPlayerHeartbeat(oPC);
    }
}
