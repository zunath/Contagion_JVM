package contagionJVM.Event;

import contagionJVM.IScriptEventHandler;
import contagionJVM.NWNX.NWNX_DMActions;
import contagionJVM.System.ProgressionSystem;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

@SuppressWarnings("unused")
public class DM_GiveLevel implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        NWObject oTarget = NWNX_DMActions.oGetDMAction_Target(false);
        int levels = NWNX_DMActions.nGetDMAction_Param(false);
        NWNX_DMActions.PreventDMAction();

        if(levels <= 0)
        {
            NWScript.sendMessageToPC(objSelf, "You can't take levels.");
            return;
        }

        if(!NWScript.getIsPC(oTarget) || NWScript.getIsDM(oTarget)) return;

        ProgressionSystem.GiveLevelToPC(oTarget, levels);
        NWScript.sendMessageToPC(objSelf, "You gave " + levels + " level(s) to " + NWScript.getName(oTarget, false) + "!");
    }
}
