package contagionJVM.Event;
import contagionJVM.IScriptEventHandler;
import org.nwnx.nwnx2.jvm.*;

@SuppressWarnings("UnusedDeclaration")
public class Module_OnPlayerRespawn implements IScriptEventHandler {
	@Override
	public void runScript(NWObject objSelf) {
		// Death System
		NWScript.executeScript("dth_mod_respawn", objSelf);
	}
}
