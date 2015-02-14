package contagionJVM.Placeable;

import contagionJVM.IScriptEventHandler;
import contagionJVM.System.DeathSystem;
import org.nwnx.nwnx2.jvm.NWObject;

@SuppressWarnings("UnusedDeclaration")
public class DeathCorpseDisturbed implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        DeathSystem.OnCorpseDisturb(objSelf);
    }
}
