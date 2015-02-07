package contagionJVM.Event;
import contagionJVM.Bioware.*;
import contagionJVM.IScriptEventHandler;
import org.nwnx.nwnx2.jvm.*;
import org.nwnx.nwnx2.jvm.constants.*;

@SuppressWarnings("UnusedDeclaration")
public class Module_OnActivateItem implements IScriptEventHandler {
	@Override
	public void runScript(final NWObject objSelf) {

		// Bioware default
		NWScript.executeScript("x2_mod_def_act", objSelf);
	}
}
