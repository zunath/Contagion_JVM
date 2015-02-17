package contagionJVM.Dialog;

import contagionJVM.GameObject.PlayerGO;
import contagionJVM.IScriptEventHandler;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

import java.io.PrintWriter;
import java.io.StringWriter;

@SuppressWarnings("UnusedDeclaration")
public class Dialog_End implements IScriptEventHandler {
    @Override
    public void runScript(NWObject oNPC) {
        NWObject oPC = NWScript.getPCSpeaker();
        PlayerGO pcGO = new PlayerGO(oPC);
        PlayerDialog playerDialog = DialogManager.loadPlayerDialog(pcGO.getUUID());

        try {
            Class scriptClass = Class.forName("contagionJVM.Dialog.Conversation_" + playerDialog.getActiveDialogName());
            IDialogHandler script = (IDialogHandler)scriptClass.newInstance();
            script.EndDialog();
            DialogManager.removePlayerDialog(pcGO.getUUID());
        }
        catch(Exception ex) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();

            String message = "Dialog_End was unable to execute class method: contagionJVM.Dialog.Conversation_" + playerDialog.getActiveDialogName() + ".EndDialog()";
            System.out.println(message);
            System.out.println("Exception: ");
            System.out.println(exceptionAsString);

            NWScript.writeTimestampedLogEntry(message);
            NWScript.writeTimestampedLogEntry("Exception:");
            NWScript.writeTimestampedLogEntry(exceptionAsString);
        }
    }
}
