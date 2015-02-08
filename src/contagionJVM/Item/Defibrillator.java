package contagionJVM.Item;

import contagionJVM.Bioware.Position;
import contagionJVM.Helper.ColorToken;
import contagionJVM.IScriptEventHandler;
import contagionJVM.NWNX.NWNX_Funcs;
import contagionJVM.System.ProgressionSystem;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.Scheduler;
import org.nwnx.nwnx2.jvm.constants.Animation;

@SuppressWarnings("UnusedDeclaration")
public class Defibrillator implements IScriptEventHandler {
    final String DefibInUseStatusVariable = "DEFIB_TEMP_IN_USE";
    final String TemporaryTargetObjectVariable = "DEFIB_TEMP_TARGET_OBJECT";

    @Override
    public void runScript(NWObject objSelf) {
        ProgressionSystem progressionSystem = new ProgressionSystem();

        final NWObject oPC = NWScript.getItemActivator();
        NWObject oTarget = NWScript.getItemActivatedTarget();
        NWObject oItem = NWScript.getItemActivated();
        final int iDelayTime = 15 - progressionSystem.GetPlayerSkillLevel(oPC, ProgressionSystem.SkillType_FIRST_AID);

        if(!progressionSystem.DoesPlayerMeetItemSkillRequirements(oItem, oPC))
        {
            return;
        }

        // Already reviving - cancel the action and inform user
        if(NWScript.getLocalInt(oPC, DefibInUseStatusVariable) == 1)
        {
            NWScript.deleteLocalInt(oPC, DefibInUseStatusVariable);
            NWNX_Funcs.StopTimingBar(oPC, "");
            NWScript.floatingTextStringOnCreature(ColorToken.Red() + "Action canceled!" + ColorToken.End(), oPC, false);
            NWScript.setCommandable(true, oPC);
            // Remove temporary variable linking to the NWObject being unlocked
            NWScript.deleteLocalObject(oPC, TemporaryTargetObjectVariable);
            return;
        }

        if(NWScript.getIsPC(oTarget) && oPC != oTarget)
        {
            if(NWScript.getCurrentHitPoints(oTarget) <= 0)
            {
                NWNX_Funcs.StartTimingBar(oPC, iDelayTime, "perform_defib");
                NWScript.setLocalObject(oPC, TemporaryTargetObjectVariable, oTarget);
                NWScript.setLocalInt(oPC, DefibInUseStatusVariable, 1);

                Scheduler.delay(oPC, iDelayTime * 1000, new Runnable() {
                    @Override
                    public void run() {
                        NWScript.deleteLocalInt(oPC, DefibInUseStatusVariable);
                    }
                });

                NWScript.floatingTextStringOnCreature(ColorToken.Purple() + "You begin using the defibrillator..." + ColorToken.End(), oPC, false);

                Position.TurnToFaceObject(oTarget, oPC);

                Scheduler.assign(oPC, new Runnable() {
                    @Override
                    public void run() {
                        NWScript.playAnimation(Animation.LOOPING_GET_LOW, 1.0f, (float) iDelayTime);
                        NWScript.setCommandable(false, oPC);
                    }
                });

            }
            else
            {
                NWScript.sendMessageToPC(oPC, "You can only use that item on players who are dead or dying.");
            }
        }
        else
        {
            NWScript.sendMessageToPC(oPC, "Invalid target.");
        }

        Scheduler.flushQueues();
    }
}
