package contagionJVM.Event;
import contagionJVM.Dialog.DialogManager;
import contagionJVM.IScriptEventHandler;
import org.nwnx.nwnx2.jvm.*;

@SuppressWarnings("unused")
public class Module_OnPlayerRest implements IScriptEventHandler {
	@Override
	public void runScript(final NWObject objSelf) {
        NWObject oPC = NWScript.getLastPCRested();

        Scheduler.assign(oPC, new Runnable() {
            @Override
            public void run() {
                NWScript.clearAllActions(false);
            }
        });

        Scheduler.flushQueues();

        DialogManager.startConversation(oPC, oPC, "RestMenu");
	}
}
