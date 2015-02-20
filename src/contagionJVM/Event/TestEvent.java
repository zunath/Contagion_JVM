package contagionJVM.Event;

import contagionJVM.IScriptEventHandler;
import contagionJVM.System.DiseaseSystem;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

@SuppressWarnings("UnusedDeclaration")
public class TestEvent implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        NWObject oPC = NWScript.getLastUsedBy();

        DiseaseSystem.IncreaseDiseaseLevel(oPC, 5);
    }
}
