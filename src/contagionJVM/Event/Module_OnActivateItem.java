package contagionJVM.Event;
import contagionJVM.IScriptEventHandler;
import org.nwnx.nwnx2.jvm.*;

@SuppressWarnings("UnusedDeclaration")
public class Module_OnActivateItem implements IScriptEventHandler {
	@Override
	public void runScript(final NWObject objSelf) {
        NWScript.executeScript("x2_mod_def_act", objSelf);
		NWScript.executeScript("jvm_item_use", objSelf);
	}
}
