package contagionJVM.Placeable.DeathCorpse;

import contagionJVM.IScriptEventHandler;
import contagionJVM.System.DeathSystem;
import org.nwnx.nwnx2.jvm.NWObject;

@SuppressWarnings("UnusedDeclaration")
public class DeathCorpse_OnClosed implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        DeathSystem.OnCorpseClose(objSelf);
    }
}
