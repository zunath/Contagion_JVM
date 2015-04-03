package contagionJVM.Item;

import contagionJVM.Dialog.DialogManager;
import contagionJVM.IScriptEventHandler;
import org.nwnx.nwnx2.jvm.NWLocation;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

@SuppressWarnings("unused")
public class StructureBuildTool implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        NWObject oPC = NWScript.getItemActivator();
        NWObject oItem = NWScript.getItemActivated();
        NWLocation lTargetLocation = NWScript.getItemActivatedTargetLocation();
        NWScript.setLocalLocation(oPC, "BUILD_TOOL_LOCATION_TARGET", lTargetLocation);

        DialogManager.startConversation(oPC, oPC, "BuildToolMenu");

    }
}
