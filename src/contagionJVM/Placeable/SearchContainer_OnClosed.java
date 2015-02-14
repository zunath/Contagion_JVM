package contagionJVM.Placeable;

import contagionJVM.IScriptEventHandler;
import contagionJVM.System.SearchSystem;
import org.nwnx.nwnx2.jvm.NWObject;

@SuppressWarnings("UnusedDeclaration")
public class SearchContainer_OnClosed implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        SearchSystem.OnChestClose(objSelf);
    }
}
