package contagionJVM.Event;

import contagionJVM.IScriptEventHandler;
import contagionJVM.NWNX.NWNX_Events;
import org.nwnx.nwnx2.jvm.NWObject;

public class Module_OnUseFeat implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        NWObject oPC = objSelf;
        int iFeatID = NWNX_Events.GetEventSubType();
        NWObject oTarget = NWNX_Events.GetEventTarget();
    }
}
