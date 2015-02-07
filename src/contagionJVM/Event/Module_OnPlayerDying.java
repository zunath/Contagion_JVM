package contagionJVM.Event;
import contagionJVM.Constants;
import contagionJVM.IScriptEventHandler;
import org.nwnx.nwnx2.jvm.*;
import org.nwnx.nwnx2.jvm.constants.*;

@SuppressWarnings("unused")
public class Module_OnPlayerDying implements IScriptEventHandler {
	@Override
	public void runScript(NWObject objSelf) {

		// Bleeding
		NWScript.executeScript("bleed_on_dying", objSelf);
	}
}
