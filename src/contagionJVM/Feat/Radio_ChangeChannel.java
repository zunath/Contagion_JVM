package contagionJVM.Feat;

import contagionJVM.IScriptEventHandler;
import contagionJVM.System.RadioSystem;
import org.nwnx.nwnx2.jvm.NWObject;

@SuppressWarnings("UnusedDeclaration")
public class Radio_ChangeChannel implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        RadioSystem radioSystem = new RadioSystem();
        radioSystem.ChangeChannel(objSelf);
    }
}
