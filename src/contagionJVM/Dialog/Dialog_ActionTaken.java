package contagionJVM.Dialog;

import contagionJVM.GameObject.PlayerGO;
import contagionJVM.IScriptEventHandler;
import contagionJVM.NWNX.NWNX_Events;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

import java.io.PrintWriter;
import java.io.StringWriter;

@SuppressWarnings("UnusedDeclaration")
public class Dialog_ActionTaken implements IScriptEventHandler {
    @Override
    public void runScript(NWObject oNPC) {
        String className = NWScript.getLocalString(oNPC, "REO_CONVERSATION");
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
                Class scriptClass = Class.forName("contagionJVM.Dialog." + className);
                IDialogHandler script = (IDialogHandler) scriptClass.newInstance();
                script.DoAction(oPC, dialog.getCurrentPageID() + 1, responseID + 1);
            } catch (Exception ex) {
                StringWriter sw = new StringWriter();
                ex.printStackTrace(new PrintWriter(sw));
                String exceptionAsString = sw.toString();

                String message = "Dialog_ActionTaken was unable to execute class method: contagionJVM.Dialog." + className + ".DoAction()";
                System.out.println(message);
                System.out.println("Exception: ");
                System.out.println(exceptionAsString);

                NWScript.writeTimestampedLogEntry(message);
                NWScript.writeTimestampedLogEntry("Exception:");
                NWScript.writeTimestampedLogEntry(exceptionAsString);
            }
        }

        DialogManager.storePlayerDialog(uuid, dialog);
    }
}
