package contagionJVM.Item.OmniTool;

import contagionJVM.IScriptEventHandler;
import contagionJVM.NWNX.NWNX_Events;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.Scheduler;

@SuppressWarnings("unused")
public class AutoFollow implements IScriptEventHandler {
    @Override
    public void runScript(NWObject oPC) {

        final NWObject oTarget = NWNX_Events.GetEventTarget();

        if(!NWScript.getIsPC(oTarget) || NWScript.getIsDM(oTarget))
        {
            NWScript.sendMessageToPC(oPC, "You can only follow other players.");
            return;
        }

        NWScript.floatingTextStringOnCreature("Now following " + NWScript.getName(oTarget, false) + ".", oPC, false);
        Scheduler.delay(oPC, 2000, new Runnable() {
            @Override
            public void run() {
                NWScript.actionForceFollowObject(oTarget, 2.0f);
            }
        });

    }
}
