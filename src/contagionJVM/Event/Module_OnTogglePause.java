package contagionJVM.Event;

import contagionJVM.IScriptEventHandler;
import contagionJVM.NWNX.NWNX_Events;
import org.nwnx.nwnx2.jvm.NWObject;

public class Module_OnTogglePause implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        NWNX_Events.BypassEvent();
    }
}
