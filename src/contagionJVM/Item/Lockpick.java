package contagionJVM.Item;

import contagionJVM.Bioware.Position;
import contagionJVM.Helper.ColorToken;
import contagionJVM.IScriptEventHandler;
import contagionJVM.NWNX.NWNX_Events;
import contagionJVM.NWNX.NWNX_Funcs;
import contagionJVM.System.ProgressionSystem;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.Scheduler;
import org.nwnx.nwnx2.jvm.constants.Animation;

@SuppressWarnings("UnusedDeclaration")
public class Lockpick implements IScriptEventHandler {

    final String CurrentStatusVariable = "LOCKPICK_TEMPORARY_CURRENTLY_PICKING_LOCK";
    final String UnlockingObjectVariable = "LOCKPICK_TEMP_UNLOCKING_OBJECT";
    final String SkillRequiredVariable = "LOCKPICK_SKILL_REQUIRED";
    final float MaxDistance = 2.5f;


    @Override
    public void runScript(final NWObject oPC) {

        NWObject oTarget = NWNX_Events.GetEventTarget();

        int iSkillRequired = NWScript.getLocalInt(oTarget, SkillRequiredVariable);
        int iSkill = ProgressionSystem.GetPlayerSkillLevel(oPC, ProgressionSystem.SkillType_LOCKPICKING);

        // Already picking the lock - cancel the action and inform the user
        if(NWScript.getLocalInt(oPC, CurrentStatusVariable) == 1)
        {
            NWScript.deleteLocalInt(oPC, CurrentStatusVariable);
            NWNX_Funcs.StopTimingBar(oPC, "");
            NWScript.floatingTextStringOnCreature(ColorToken.Red() + "Lockpicking canceled!" + ColorToken.End(), oPC, false);
            NWScript.setCommandable(true, oPC);
            // Remove temporary variable linking to the NWObject being unlocked
            NWScript.deleteLocalObject(oPC, UnlockingObjectVariable);
        }
        // Object is not locked.
        else if(!NWScript.getLocked(oTarget))
        {
            NWScript.floatingTextStringOnCreature(ColorToken.Red() + "That NWObject is not locked." + ColorToken.End(), oPC, false);
        }
        // Object cannot be unlocked using a lockpick
        else if(iSkillRequired <= 0)
        {
            NWScript.floatingTextStringOnCreature(ColorToken.Red() + "You cannot pick that lock." + ColorToken.End(), oPC, false);
        }
        // Skill level is too low
        else if(iSkillRequired > iSkill)
        {
            NWScript.floatingTextStringOnCreature(ColorToken.Red() + "Your skill level is too low. (Required: " + iSkillRequired + ")" + ColorToken.End(), oPC, false);
        }
        // Too far away
        else if(NWScript.getDistanceBetween(oPC, oTarget) > MaxDistance)
        {
            NWScript.floatingTextStringOnCreature(ColorToken.Red() + "You must get closer to do that." + ColorToken.End(), oPC, false);
        }
        // All requirements met. Begin process to unlock NWObject
        else
        {
            int iSeconds = 10 + NWScript.random(5);
            float fSeconds = iSeconds;
            int iSkillBonus = Math.abs(iSkillRequired - iSkill);

            // Number of seconds is reduced depending on how many skill ranks a player
            // has over the minimum skill required.
            // Cap is 75% of normal
            if(iSkillBonus > 0)
            {
                float fMultiplier = iSkillBonus * 0.05f;
                if(fMultiplier > 0.75)
                    fMultiplier = 0.75f;

                fSeconds = fSeconds * fMultiplier;
                iSeconds = (int)fSeconds;
            }


            // Animation stuff
            Position.TurnToFaceObject(oTarget, oPC);

            final float fSecondsCopy = fSeconds;
            Scheduler.assign(oPC, new Runnable() {
                @Override
                public void run() {
                    NWScript.playAnimation(Animation.LOOPING_GET_MID, 1.0f, fSecondsCopy);
                    NWScript.setCommandable(false, oPC);
                }
            });

            Scheduler.delay(oPC, (int) (fSeconds * 1000), new Runnable() {
                @Override
                public void run() {
                    NWScript.setCommandable(true, oPC);
                }
            });

            // Show timing bar, set PC's current action status, and inform PC they're picking a lock.
            NWNX_Funcs.StartTimingBar(oPC, iSeconds, "perform_lockpick");
            NWScript.setLocalObject(oPC, UnlockingObjectVariable, oTarget);
            NWScript.setLocalInt(oPC, CurrentStatusVariable, 1);

            Scheduler.delay(oPC, (int)(1000 * (fSeconds + 0.2)), new Runnable() {
                @Override
                public void run() {
                    NWScript.deleteLocalInt(oPC, CurrentStatusVariable);
                }
            });

            NWScript.floatingTextStringOnCreature(ColorToken.Purple() + "You begin picking the lock..." + ColorToken.End(), oPC, false);
        }

        Scheduler.flushQueues();

    }
}
