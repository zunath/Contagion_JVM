package contagionJVM.Event;
import contagionJVM.Constants;
import contagionJVM.IScriptEventHandler;
import org.nwnx.nwnx2.jvm.*;
import org.nwnx.nwnx2.jvm.constants.*;

@SuppressWarnings("UnusedDeclaration")
public class Module_OnPlayerChat implements IScriptEventHandler {

	// This script is fired on the Module's OnChat event.
	// Note that most of the OnChat scripting takes place within the "reo_mod_chatnwnx" script,
	// as the server uses NWNX_Chat, a plugin for NWNX.

	@Override
	public void runScript(final NWObject objSelf) {

		// Housing System
		NWScript.executeScript("rhs_mod_chat", objSelf);
	}
}