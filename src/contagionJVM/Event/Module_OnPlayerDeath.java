package contagionJVM.Event;
import contagionJVM.IScriptEventHandler;
import contagionJVM.System.DeathSystem;
import org.nwnx.nwnx2.jvm.*;

@SuppressWarnings("unused")
public class Module_OnPlayerDeath implements IScriptEventHandler {
	@Override
	public void runScript(NWObject objSelf) {

        DeathSystem.OnPlayerDeath();
	}
}

