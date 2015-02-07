package contagionJVM;

import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.SchedulerListener;

import java.io.PrintWriter;
import java.io.StringWriter;

public class EventReceiver implements SchedulerListener {
    public void postFlushQueues(int remainingTokens) {}
    public void missedToken(NWObject objSelf, String token) {}
    public void context(NWObject objSelf) {}

    public void event(NWObject objSelf, String event) {

        // Try to locate a matching class name based on the event passed in from NWN JVM_EVENT call.
        try {
            Class scriptClass = Class.forName("contagionJVM." + event);
            IScriptEventHandler script = (IScriptEventHandler)scriptClass.newInstance();
            script.runScript(objSelf);
        }
        catch(Exception ex) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();

            String message = "EventReceiver was unable to execute class method: contagionJVM." + event + ".runScript()";
            System.out.println(message);
            System.out.println("Exception: ");
            System.out.println(exceptionAsString);

            NWScript.writeTimestampedLogEntry(message);
            NWScript.writeTimestampedLogEntry("Exception:");
            NWScript.writeTimestampedLogEntry(exceptionAsString);
        }
    }
}
