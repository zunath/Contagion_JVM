package contagionJVM.Item.OmniTool;

import contagionJVM.IScriptEventHandler;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

@SuppressWarnings("unused")
public class CheckInfectionLevel implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        NWScript.sendMessageToPC(objSelf, "CheckInfectionLevel firing");
    }
}
