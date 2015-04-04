package contagionJVM.Dialog;

import contagionJVM.GameObject.PlayerGO;
import contagionJVM.Helper.ErrorHelper;
import contagionJVM.IScriptEventHandler;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

@SuppressWarnings("UnusedDeclaration")
public class Dialog_End implements IScriptEventHandler {
    @Override
    public void runScript(NWObject oNPC) {
        NWObject oPC = NWScript.getPCSpeaker();
        PlayerGO pcGO = new PlayerGO(oPC);
        PlayerDialog dialog = DialogManager.loadPlayerDialog(pcGO.getUUID());

        try {
            Class scriptClass = Class.forName("contagionJVM.Dialog.Conversation_" + dialog.getActiveDialogName());
            IDialogHandler script = (IDialogHandler)scriptClass.newInstance();
            script.EndDialog();
            DialogManager.removePlayerDialog(pcGO.getUUID());
        }
        catch(Exception ex) {
            ErrorHelper.HandleException(ex, "Dialog_End was unable to execute class method.");
        }

        NWScript.deleteLocalInt(oPC, "DIALOG_SYSTEM_INITIALIZE_RAN");
        NWScript.setLocalInt(oNPC, "REO_CONVERSATION_SHOW_NODE", 0);
    }
}
