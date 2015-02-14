package contagionJVM.Event;
import contagionJVM.IScriptEventHandler;
import contagionJVM.System.DeathSystem;
import org.nwnx.nwnx2.jvm.*;

@SuppressWarnings("UnusedDeclaration")
public class Module_OnPlayerRespawn implements IScriptEventHandler {
	@Override
	public void runScript(NWObject objSelf) {
        DeathSystem.OnPlayerRespawn();
	}
}
