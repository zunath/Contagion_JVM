package contagionJVM.Event;
import contagionJVM.IScriptEventHandler;
import contagionJVM.System.RadioSystem;
import org.nwnx.nwnx2.jvm.*;

@SuppressWarnings("unused")
public class Module_OnAcquireItem implements IScriptEventHandler {
	@Override
	public void runScript(NWObject objSelf) {
		RadioSystem radioSystem = new RadioSystem();

		// Bioware Default
		NWScript.executeScript("x2_mod_def_aqu", objSelf);

		radioSystem.OnModuleAcquire();

		// Key Item System
		NWScript.executeScript("key_item_modacq", objSelf);
	}
}
