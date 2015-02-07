package contagionJVM.Event;
import contagionJVM.IScriptEventHandler;
import org.nwnx.nwnx2.jvm.*;
import org.nwnx.nwnx2.jvm.constants.ClassType;

@SuppressWarnings("UnusedDeclaration")
public class Module_OnPlayerLevelUp implements IScriptEventHandler {
	@Override
	public void runScript(NWObject objSelf) {
		// SimTools
		NWScript.executeScript("fky_chat_levelup", objSelf);
	}
}
