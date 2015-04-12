package contagionJVM.Event;

import contagionJVM.IScriptEventHandler;
import contagionJVM.System.CustomEffectSystem;
import org.nwnx.nwnx2.jvm.NWObject;

@SuppressWarnings("unused")
public class Player_OnHeartbeat implements IScriptEventHandler {
    @Override
    public void runScript(NWObject oPC) {
        CustomEffectSystem.OnPlayerHeartbeat(oPC);
    }
}
