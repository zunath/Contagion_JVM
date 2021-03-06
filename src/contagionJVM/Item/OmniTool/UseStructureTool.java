package contagionJVM.Item.OmniTool;

import contagionJVM.Dialog.DialogManager;
import contagionJVM.IScriptEventHandler;
import contagionJVM.NWNX.NWNX_Events;
import contagionJVM.System.StructureSystem;
import org.nwnx.nwnx2.jvm.NWLocation;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

@SuppressWarnings("unused")
public class UseStructureTool implements IScriptEventHandler {
    @Override
    public void runScript(NWObject oPC) {
        NWObject oItem = NWNX_Events.GetEventItem();
        NWObject oTarget = NWNX_Events.GetEventTarget();
        NWLocation lTargetLocation = NWScript.location(NWScript.getArea(oPC), NWNX_Events.GetEventPosition(), 0.0f);
        boolean isMovingStructure = StructureSystem.IsPCMovingStructure(oPC);

        if(!oTarget.equals(NWObject.INVALID))
        {
            lTargetLocation = NWScript.getLocation(oTarget);
        }

        if(isMovingStructure)
        {
            StructureSystem.MoveStructure(oPC, lTargetLocation);
        }
        else
        {
            NWScript.setLocalLocation(oPC, "BUILD_TOOL_LOCATION_TARGET", lTargetLocation);
            DialogManager.startConversation(oPC, oPC, "BuildToolMenu");
        }
    }
}
