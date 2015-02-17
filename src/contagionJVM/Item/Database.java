package contagionJVM.Item;

import contagionJVM.Dialog.DialogManager;
import contagionJVM.IScriptEventHandler;
import org.nwnx.nwnx2.jvm.NWObject;

@SuppressWarnings("UnusedDeclaration")
public class Database implements IScriptEventHandler {
    @Override
    public void runScript(NWObject oPC) {
        DialogManager.startConversation(oPC, oPC, "RestMenu");
    }
}
