package contagionJVM.Item.OmniTool;

import contagionJVM.Dialog.DialogManager;
import contagionJVM.IScriptEventHandler;
import org.nwnx.nwnx2.jvm.NWObject;

@SuppressWarnings("unused")
public class OpenRestMenu implements IScriptEventHandler {
    @Override
    public void runScript(NWObject oPC) {
        DialogManager.startConversation(oPC, oPC, "RestMenu");
    }
}
