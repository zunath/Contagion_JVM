package contagionJVM.Dialog;

import contagionJVM.GameObject.PlayerGO;
import contagionJVM.Helper.ErrorHelper;
import contagionJVM.IScriptEventHandler;
import contagionJVM.NWNX.NWNX_Events;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

@SuppressWarnings("UnusedDeclaration")
public class Dialog_ActionTaken implements IScriptEventHandler {
    @Override
    public void runScript(NWObject oNPC) {
        NWObject oPC = NWScript.getPCSpeaker();
        PlayerGO pcGO = new PlayerGO(oPC);
        String uuid = pcGO.getUUID();
        PlayerDialog dialog = DialogManager.loadPlayerDialog(uuid);
        int gender = NWScript.getGender(oPC);
        String nodeText = NWNX_Events.GetSelectedNodeText(NWNX_Events.LANGUAGE_ENGLISH, gender);
        int responseID = NWNX_Events.GetSelectedNodeID() + (DialogManager.NumberOfResponsesPerPage * dialog.getPageOffset());

        if(nodeText.equals("Next"))
        {
            dialog.setPageOffset(dialog.getPageOffset() + 1);
        }
        else if(nodeText.equals("Previous"))
        {
            dialog.setPageOffset(dialog.getPageOffset() - 1);
        }
        else if(!nodeText.equals("End")) {

            // Try to locate a matching class name based on the event passed in from NWN JVM_EVENT call.
            try {
                Class scriptClass = Class.forName("contagionJVM.Dialog.Conversation_" + dialog.getActiveDialogName());
                IDialogHandler script = (IDialogHandler) scriptClass.newInstance();
                script.DoAction(oPC, dialog.getCurrentPageName(), responseID + 1);
            } catch (Exception ex) {
                ErrorHelper.HandleException(ex, "Dialog_ActionTaken was unable to execute class method: contagionJVM.Dialog.Conversation_\" + dialog.getActiveDialogName() + \".DoAction()");
            }
        }

    }
}
