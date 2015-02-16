package contagionJVM.Dialog;

import contagionJVM.IScriptEventHandler;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

import java.io.PrintWriter;
import java.io.StringWriter;

@SuppressWarnings("UnusedDeclaration")
public class Dialog_Start implements IScriptEventHandler {
    @Override
    public void runScript(NWObject oPC) {
        String className = NWScript.getLocalString(oPC, "REO_CONVERSATION");
        NWScript.deleteLocalString(oPC, "REO_CONVERSATION");

        // Try to locate a matching class name based on the event passed in from NWN JVM_EVENT call.
        try {
            Class scriptClass = Class.forName("contagionJVM.Dialog." + className);
            IDialogHandler script = (IDialogHandler)scriptClass.newInstance();
            script.Initialize(oPC);
        }
        catch(Exception ex) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();

            String message = "Dialog_Start was unable to execute class method: contagionJVM.Dialog." + className + ".Initialize()";
            System.out.println(message);
            System.out.println("Exception: ");
            System.out.println(exceptionAsString);

            NWScript.writeTimestampedLogEntry(message);
            NWScript.writeTimestampedLogEntry("Exception:");
            NWScript.writeTimestampedLogEntry(exceptionAsString);
        }
    }
}
