package contagionJVM.Placeable;

import contagionJVM.IScriptEventHandler;
import contagionJVM.System.SearchSystem;
import org.nwnx.nwnx2.jvm.NWObject;

@SuppressWarnings("UnusedDeclaration")
public class SearchContainer_OnDisturbed implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        SearchSystem.OnChestDisturbed(objSelf);
    }
}