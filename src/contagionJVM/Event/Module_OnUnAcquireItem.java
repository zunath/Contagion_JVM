package contagionJVM.Event;
import contagionJVM.IScriptEventHandler;
import contagionJVM.System.RadioSystem;
import org.nwnx.nwnx2.jvm.*;
import org.nwnx.nwnx2.jvm.constants.ObjectType;

@SuppressWarnings("UnusedDeclaration")
public class Module_OnUnAcquireItem implements IScriptEventHandler {
	@Override
	public void runScript(final NWObject objSelf) {
		RadioSystem radioSystem = new RadioSystem();

		// Bioware Default
		NWScript.executeScript("x2_mod_def_unaqu", objSelf);

		// Inventory Limit
		NWScript.executeScript("inv_mod_unacq", objSelf);

		radioSystem.OnModuleUnacquire();

	}
}
