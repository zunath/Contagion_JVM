package contagionJVM.Event;
import contagionJVM.Constants;
import contagionJVM.IScriptEventHandler;
import org.nwnx.nwnx2.jvm.*;

@SuppressWarnings("unused")
public class Module_OnPlayerDeath implements IScriptEventHandler {
	@Override
	public void runScript(NWObject objSelf) {

		// Drop all items on death system
		NWScript.executeScript("dth_mod_death", objSelf);
	}
}

