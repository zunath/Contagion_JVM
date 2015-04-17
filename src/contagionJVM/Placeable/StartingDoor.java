package contagionJVM.Placeable;

import contagionJVM.Dialog.DialogManager;
import contagionJVM.IScriptEventHandler;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

@SuppressWarnings("unused")
public class StartingDoor implements IScriptEventHandler {
    @Override
    public void runScript(NWObject door) {
        NWObject oPC = NWScript.getLastUsedBy();
        DialogManager.startConversation(oPC, oPC, "StartingDoor");
    }
}
