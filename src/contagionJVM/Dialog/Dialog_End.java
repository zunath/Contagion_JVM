package contagionJVM.Dialog;

import contagionJVM.IScriptEventHandler;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

import java.io.PrintWriter;
import java.io.StringWriter;

@SuppressWarnings("UnusedDeclaration")
public class Dialog_End implements IScriptEventHandler {
    @Override
    public void runScript(NWObject oNPC) {
        String className = NWScript.getLocalString(oNPC, "REO_CONVERSATION");

        // Try to locate a matching class name based on the event passed in from NWN JVM_EVENT call.
        try {
            Class scriptClass = Class.forName("contagionJVM.Dialog." + className);
            IDialogHandler script = (IDialogHandler)scriptClass.newInstance();
            script.EndDialog();
        }
        catch(Exception ex) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();

            String message = "Dialog_End was unable to execute class method: contagionJVM.Dialog." + className + ".EndDialog()";
            System.out.println(message);
            System.out.println("Exception: ");
            System.out.println(exceptionAsString);

            NWScript.writeTimestampedLogEntry(message);
            NWScript.writeTimestampedLogEntry("Exception:");
            NWScript.writeTimestampedLogEntry(exceptionAsString);
        }
    }
}
