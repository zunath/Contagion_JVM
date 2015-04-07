package contagionJVM.Event;
import contagionJVM.IScriptEventHandler;
import contagionJVM.System.InventorySystem;
import contagionJVM.System.RadioSystem;
import org.nwnx.nwnx2.jvm.*;

@SuppressWarnings("UnusedDeclaration")
public class Module_OnUnAcquireItem implements IScriptEventHandler {
	@Override
	public void runScript(final NWObject objSelf) {
		RadioSystem radioSystem = new RadioSystem();

		// Bioware Default
		NWScript.executeScript("x2_mod_def_unaqu", objSelf);

		radioSystem.OnModuleUnacquire();

	}
}
