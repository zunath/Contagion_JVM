package contagionJVM.Event;

import contagionJVM.IScriptEventHandler;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

@SuppressWarnings("UnusedDeclaration")
public class Area_OnHeartbeat implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {

        // NESS
        NWScript.executeScript("spawn_sample_hb", objSelf);
    }
}
