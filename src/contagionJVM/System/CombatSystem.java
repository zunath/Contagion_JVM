package contagionJVM.System;

import contagionJVM.Bioware.Position;
import contagionJVM.Enumerations.CustomAnimationType;
import contagionJVM.Enumerations.GunType;
import contagionJVM.GameObject.GunGO;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.Helper.ColorToken;
import contagionJVM.NWNX.NWNX_Events;
import org.nwnx.nwnx2.jvm.*;
import org.nwnx.nwnx2.jvm.constants.*;
import org.nwnx.nwnx2.jvm.constants.Action;

import java.util.Objects;

public class CombatSystem {

    // CONSTANTS
    final String CBT_OVR_SVAR_ATTACK_ALLOWED = "ATTACK_ALLOWED";
    final String CBT_OVR_SVAR_ATTACK_TARGET = "ATTACK_TARGET";
    final String CBT_OVR_SVAR_ATTACK_SHOTS_TO_FIRE = "ATTACK_SHOTS_TO_FIRE";
    final String CBT_OVR_SVAR_ATTACK_SILENCED = "ATTACK_SILENCED";
    final String CBT_OVR_SVAR_ATTACK_ANIMATION = "ATTACK_ANIMATION";

    final int CBT_OVR_ANIMATION_NONE    = 0;
    final int CBT_OVR_SOUND_SHOT_HEAVY_PISTOL_9MM = 8205;
    final int CBT_OVR_SOUND_SHOT_SMG_762 = 8211;
    final int CBT_OVR_SOUND_SHOT_ASSAULT_RIFLE_762 = 8213;
    final int CBT_OVR_SOUND_SHOT_SHOTGUN_12G = 8218;
    final int CBT_OVR_SOUND_SHOT_SILENCED_PISTOL = 8224;
    final int CBT_OVR_SOUND_SHOT_SILENCED_RIFLE = 8225;

    final String GUN_LOW_AMMO_MESSAGE = "Low ammo!";
    final String GUN_OUT_OF_AMMO_MESSAGE = "Out of ammo!";

    final String GUN_TEMP_PREVENT_AMMO_BOX_BUG = "GUN_TEMP_PREVENT_AMMO_BOX_BUG";
    final float GUN_LOW_AMMO_MESSAGE_DISPLAY_PERCENTAGE = 0.20f;
    final String GUN_MAGAZINE_BULLET_COUNT = "GUN_MAGAZINE_BULLET_COUNT";
    final int IP_CONST_FIREARM_RANGE_NEAR          = 1;
    final int IP_CONST_FIREARM_RANGE_MID           = 2;
    final int IP_CONST_FIREARM_RANGE_FAR           = 3;


    final float GUN_RANGE_NEAR_MINIMUM = 3.5f;
    final float GUN_RANGE_NEAR_MAXIMUM = 6.0f;
    final float GUN_RANGE_MID_MINIMUM  = 5.5f;
    final float GUN_RANGE_MID_MAXIMUM  = 8.5f;
    final float GUN_RANGE_FAR_MINIMUM  = 14.0f;
    final float GUN_RANGE_FAR_MAXIMUM  = 27.5f;

    final String GUN_TEMP_AMMO_LOADED_TYPE     = "GUN_TEMP_AMMO_LOADED_TYPE";

    final int GUN_AMMUNITION_PRIORITY_ENHANCED = 1;
    final int GUN_AMMUNITION_PRIORITY_INCENDIARY = 2;
    final String GUN_GAS_CANISTER_TAG = "gas_canister";

    // END CONSTANTS


    public void OnModuleAttack(final NWObject oAttacker)
    {
        final NWObject oTarget = NWNX_Events.GetEventTarget();
        PlayerGO attackerGO = new PlayerGO(oAttacker);
        PlayerGO targetGO = new PlayerGO(oTarget);

        // Check for sanctuary if this attack is PC versus PC
        if(NWScript.getIsPC(oTarget) && !NWScript.getIsDMPossessed(oTarget) && NWScript.getIsPC(oAttacker))
        {
            // Either the attacker or target has sanctuary - prevent combat from happening
            if(attackerGO.hasPVPSanctuary())
            {
                NWScript.floatingTextStringOnCreature(ColorToken.Red() + "You are under the NWEffects of PVP sanctuary and cannot engage in PVP. To disable this feature permanently refer to the 'Disable PVP Sanctuary' option in your rest menu." + ColorToken.End(), oAttacker, false);
                Scheduler.assign(oAttacker, new Runnable() {
                    @Override
                    public void run() {
                        NWScript.clearAllActions(false);
                    }
                });

                Scheduler.flushQueues();
                return;
            }
            else if(targetGO.hasPVPSanctuary())
            {
                NWScript.floatingTextStringOnCreature(ColorToken.Red() + "Your target is under the NWEffects of PVP sanctuary and cannot engage in PVP combat." + ColorToken.End(), oAttacker, false);
                Scheduler.assign(oAttacker, new Runnable() {
                    @Override
                    public void run() {
                        NWScript.clearAllActions(false);
                    }
                });

                Scheduler.flushQueues();
                return;
            }
        }


        // PC is too busy to make an attack right now
        if(NWScript.getLocalInt(oAttacker, "RELOADING_GUN") == 1 || NWScript.getCurrentAction(oAttacker) == Action.SIT || NWScript.getLocalInt(oAttacker, "LOCKPICK_TEMPORARY_CURRENTLY_PICKING_LOCK") == 1)
        {
            NWScript.floatingTextStringOnCreature(ColorToken.Red() + "You are too busy to attack right now.", oAttacker, false);
            Scheduler.assign(oAttacker, new Runnable() {
                @Override
                public void run() {
                    NWScript.clearAllActions(false);
                }
            });

            Scheduler.flushQueues();
            return;
        }
        // PC is dead or dying
        if(NWScript.getCurrentHitPoints(oAttacker) <= 0)
        {
            Scheduler.assign(oAttacker, new Runnable() {
                @Override
                public void run() {
                    NWScript.clearAllActions(false);
                }
            });

            Scheduler.flushQueues();
            return;
        }

        NWObject oArea = NWScript.getArea(oAttacker);

        // Area is designated as "No PVP"
        if(NWScript.getIsPC(oTarget) && NWScript.getLocalInt(oArea, "NO_PVP") == 1 && NWScript.getIsPC(oAttacker))
        {
            Scheduler.assign(oAttacker, new Runnable() {
                @Override
                public void run() {
                    NWScript.clearAllActions(false);
                }
            });

            Scheduler.flushQueues();
            NWScript.floatingTextStringOnCreature(ColorToken.Red() + "You cannot engage in PVP in this area." + ColorToken.End(), oAttacker, false);
            return;
        }

        // Stop trying to attack a DM, dumbass
        if(NWScript.getIsDM(oTarget))
        {
            Scheduler.assign(oAttacker, new Runnable() {
                @Override
                public void run() {
                    NWScript.clearAllActions(false);
                }
            });

            Scheduler.flushQueues();
            return;
        }

        boolean bLineOfSight;

        // Determine which animation to use
        NWObject oRightHand = NWScript.getItemInSlot(InventorySlot.RIGHTHAND, oAttacker);
        NWObject oLeftHand = NWScript.getItemInSlot(InventorySlot.LEFTHAND, oAttacker);
        GunGO stGun1Info = new GunGO(oRightHand);
        GunGO stGun2Info = new GunGO(oLeftHand);
        int iNumberOfShots = 1;
        boolean bUsingFirearm = false;

        // All firearms must be marked to use the custom combat system
        if(stGun1Info.getGunType() > 0 || stGun2Info.getGunType() > 0)
        {
            // Disable sanctuary and other OnAreaEnter buffs
            NWEffect[] effects = NWScript.getEffects(oAttacker);

            for(NWEffect eEffect : effects)
            {
                int iEffectType = NWScript.getEffectType(eEffect);
                if(iEffectType == EffectType.DAMAGE_REDUCTION || iEffectType == EffectType.SANCTUARY)
                {
                    NWScript.removeEffect(oAttacker, eEffect);
                }
            }
            // Disable stealth mode
            if(NWScript.getActionMode(oAttacker, ActionMode.STEALTH))
            {
                NWScript.setActionMode(oAttacker, ActionMode.STEALTH, false);
            }
            bLineOfSight = NWScript.lineOfSightObject(oAttacker, oTarget) == 1;
            // No line of sight and we're using the custom combat system.
            // Prevent from continuing.
            if(!bLineOfSight || NWScript.getLocalInt(oAttacker, "TEMP_DISABLE_FIRING") == 1)
            {
                Scheduler.assign(oAttacker, new Runnable() {
                    @Override
                    public void run() {
                        NWScript.clearAllActions(false);
                    }
                });
                Scheduler.flushQueues();

                return;
            }
            SetAttackAllowed(oAttacker, 1);
            bUsingFirearm = true;
        }
        // Otherwise, the PC isn't using a firearm. Use the default combat system
        else
        {
            NWScript.setLocalInt(oAttacker, CBT_OVR_SVAR_ATTACK_ANIMATION, CBT_OVR_ANIMATION_NONE);
            SetAttackAllowed(oAttacker, 0);
            SetNumberShotsToFire(oAttacker, 0);
        }

        if(bUsingFirearm)
        {
            NWNX_Events.BypassEvent(); // LINUX

            // Mode is determined by the right hand weapon. Figure out how many shots to fire based on mode
            if(NWScript.getLocalInt(oRightHand, "GUN_CUR_RATE_OF_FIRE") == 1)
            {
                iNumberOfShots = 3;
            }
            SetNumberShotsToFire(oAttacker, iNumberOfShots);

            float fDelay = 0.0f;
            SetTarget(oAttacker, oTarget);

            if (NWScript.getDistanceBetween(oAttacker, oTarget) > 0.5f)
            {
                if (GetAttackAllowed(oAttacker) == 1)
                {
                    if (NWScript.getCommandable(oAttacker) && NWScript.getIsObjectValid(oTarget))
                    {
                        Scheduler.assign(oAttacker, new Runnable() {
                            @Override
                            public void run() {
                                NWScript.clearAllActions(true);
                            }
                        });
                        Scheduler.flushQueues();

                        NWVector attackerVector = NWScript.getPosition(oAttacker);
                        NWVector targetVector = NWScript.getPosition(oTarget);
                        NWVector vDiff = new NWVector(
                                attackerVector.getX() - targetVector.getX(),
                                attackerVector.getY() - targetVector.getY(),
                                attackerVector.getZ() - targetVector.getZ());

                        float vAngle = Math.abs(NWScript.vectorToAngle(vDiff) - NWScript.getFacing(oAttacker) - 180);

                        if (vAngle > 1.0 && vAngle < 359.0)
                        {
                            Position.TurnToFaceObject(oTarget, oAttacker);
                            fDelay = 0.75f;
                        }
                        // Quick players can sometimes get more than one cycle of shots off each attack. This should stop that.
                        NWScript.setLocalInt(oAttacker, "TEMP_DISABLE_FIRING", 1);

                        Scheduler.delay(oAttacker, (int) (fDelay * 1000.0f), new Runnable() {
                            @Override
                            public void run() {
                                MainAttack(oAttacker, oTarget, oAttacker);
                                NWScript.deleteLocalInt(oAttacker, "TEMP_DISABLE_FIRING");
                            }
                        });
                        Scheduler.flushQueues();
                    }
                }
            }
        }
    }


    void SetNumberShotsToFire(NWObject oAttacker, int iNumberOfShots)
    {
        NWScript.setLocalInt(oAttacker, CBT_OVR_SVAR_ATTACK_SHOTS_TO_FIRE, iNumberOfShots);
    }

    void SetTarget(NWObject oAttacker, NWObject oTarget)
    {
        NWScript.setLocalObject(oAttacker, CBT_OVR_SVAR_ATTACK_TARGET, oTarget);
        // Notify Target based on skill check???
    }

    int GetAttackAllowed(NWObject oAttacker)
    {
        return NWScript.getLocalInt(oAttacker, CBT_OVR_SVAR_ATTACK_ALLOWED);
    }

    void SetAttackAllowed(NWObject oAttacker, int bAttackAllowed)
    {
        NWScript.setLocalInt(oAttacker, CBT_OVR_SVAR_ATTACK_ALLOWED, bAttackAllowed);
    }

    private void MainAttack(final NWObject oAttacker, final NWObject oTarget, final NWObject objSelf)
    {

        NWObject oWeapon1 = NWScript.getItemInSlot(InventorySlot.RIGHTHAND, oAttacker);
        NWObject oWeapon2 = NWScript.getItemInSlot(InventorySlot.LEFTHAND, oAttacker);
        GunGO stGun1Info = new GunGO(oWeapon1);
        GunGO stGun2Info = new GunGO(oWeapon2);

        int iShots = NWScript.getLocalInt(oAttacker, CBT_OVR_SVAR_ATTACK_SHOTS_TO_FIRE);
        if (iShots < 1) iShots = 1;

        // Make sure there's ammo available
        NWObject oMagazine = NWScript.getItemInSlot(InventorySlot.ARROWS, oAttacker);
        int iMagazineSize = NWScript.getItemStackSize(oMagazine);

        if(iMagazineSize > stGun1Info.getMagazineSize() + stGun2Info.getMagazineSize())
        {
            iMagazineSize = stGun1Info.getMagazineSize() + stGun2Info.getMagazineSize();
            NWScript.setItemStackSize(oMagazine, iMagazineSize);
        }

        if(iMagazineSize < iShots)
        {
            // No more bullets. Inform attacker.
            if(iMagazineSize == 0)
            {
                NWScript.floatingTextStringOnCreature(GUN_OUT_OF_AMMO_MESSAGE, oAttacker, false);
                return;
            }
            // Shoot off the last round(s)
            else
            {
                iShots = iMagazineSize;
            }
        }

        float fNextAttackDelay = 0.0f;
        float fSoundDelay = 0.0f;
        float fShotDelay = 0.0f;
        float fAnimationLength= 0.0f;
        float fAnimationDelay = 0.0f;
        float fAnimationSpeed = 0.0f;
        float fDualDelay = 0.0f;
        int iCurrentShot;

        int iAnimation = 0;

        // Rifles
        if (stGun1Info.getGunType() == GunType.Rifle || stGun1Info.getGunType() == GunType.Invalid)
        {
            iAnimation = CustomAnimationType.Rifle; // Default to Rifle Animation if iAnimation is Invalid
            fNextAttackDelay = 0.8f;
            fSoundDelay = 0.1f; // 0.15 very decent
            fShotDelay = 0.7f; //3.5 original
            fAnimationLength = 0.5f; // 0.158 very decent
            fAnimationDelay = 0.5f; // 0.3 very decent
            fAnimationSpeed = 2.0f; // 7.0 very decent
        }
        // Handguns
        else if (stGun1Info.getGunType() == GunType.Hangun && stGun2Info.getGunType() == GunType.Invalid)
        {
            iAnimation = CustomAnimationType.Pistol;
            fNextAttackDelay = 0.5f;
            fSoundDelay = 0.5f; // 0.15 very decent
            fShotDelay = 0.7f; //18
            fAnimationLength = 0.5f; // 0.158 very decent
            fAnimationDelay = 0.5f; // 0.3 very decent
            fAnimationSpeed = 2.0f; // 7.0 very decent
        }
        // Magnums
        else if (stGun1Info.getGunType() == GunType.Magnum && stGun2Info.getGunType() == GunType.Invalid)
        {
            iAnimation = CustomAnimationType.Pistol;
            fNextAttackDelay = 0.8f;
            fSoundDelay = 0.5f; // 0.15 very decent
            fShotDelay = 0.7f; //18
            fAnimationLength = 0.5f; // 0.158 very decent
            fAnimationDelay = 0.5f; // 0.3 very decent
            fAnimationSpeed = 2.0f; // 7.0 very decent
        }
        // Dual Handguns or Dual Magnums (but not 1 handgun, 1 magnum)
        else if (stGun1Info.getGunType() == GunType.Hangun && stGun2Info.getGunType() == GunType.Hangun ||
                stGun1Info.getGunType() == GunType.Magnum && stGun2Info.getGunType() == GunType.Magnum)
        {
            iAnimation = CustomAnimationType.PistolDual;
            fNextAttackDelay = 0.8f;
            fSoundDelay = 0.5f; // 0.15 very decent
            fShotDelay = 0.7f; //18
            fAnimationLength = 0.5f; // 0.158 very decent
            fAnimationDelay = 0.5f; // 0.3 very decent
            fAnimationSpeed = 2.0f; // 7.0 very decent
            fDualDelay = 0.2f;
        }
        // SMGs
        else if (stGun1Info.getGunType() == GunType.SMG && stGun2Info.getGunType() == GunType.Invalid)
        {
            iAnimation = CustomAnimationType.SMG;
            fNextAttackDelay = 0.8f;
            fSoundDelay = 0.1f; // 0.15 very decent
            fShotDelay = 0.35f; //18
            fAnimationLength = 0.1f; // 0.158 very decent
            fAnimationDelay = 0.25f; // 0.3 very decent
            fAnimationSpeed = 8.0f; // 7.0 very decent
        }
        // Dual SMGs
        else if (stGun1Info.getGunType() == GunType.SMG && stGun2Info.getGunType() == GunType.SMG)
        {
            iAnimation = CustomAnimationType.SMGDual;
            fNextAttackDelay = 0.8f;
            fSoundDelay = 0.1f;
            fShotDelay = 0.35f;
            fAnimationLength = 0.1f;
            fAnimationDelay = 0.25f;
            fAnimationSpeed = 8.0f;
            fDualDelay = 0.05f;
        }
        // Assault Rifles (Rifle animation, SMG stats)
        else if (stGun1Info.getGunType() == GunType.AssaultRifle)
        {
            iAnimation = CustomAnimationType.Rifle;
            fNextAttackDelay = 0.8f;
            fSoundDelay = 0.1f;
            fShotDelay = 0.35f;
            fAnimationLength = 0.1f;
            fAnimationDelay = 0.25f;
            fAnimationSpeed = 8.0f;
            fDualDelay = 0.05f;
        }

        // Shotguns
        else if (stGun1Info.getGunType() == GunType.Shotgun)
        {
            iAnimation = CustomAnimationType.Shotgun;
            fNextAttackDelay = 0.8f;
            fSoundDelay = 0.80f;
            fShotDelay = 1.0f;

            if (iShots > 1)
            {
                fAnimationLength = 0.60f + iShots * 0.02f;
            }
            else
            {
                fAnimationLength = 0.60f;
            }

            fAnimationDelay = 1.0f;
            fAnimationSpeed = 1.20f;
        }

        final int animationID = iAnimation;
        final float animationSpeed = fAnimationSpeed;
        final float animationLength = fAnimationLength * iShots + fAnimationDelay;


        Scheduler.assign(oAttacker, new Runnable() {
            @Override
            public void run() {
                NWScript.clearAllActions(true);
                NWScript.setFacingPoint(NWScript.getPosition(oTarget));
                NWScript.playAnimation(animationID, animationSpeed, animationLength);
                NWScript.setCommandable(false, oAttacker);
            }
        });
        Scheduler.flushQueues();

        Scheduler.delay(oAttacker, (int) ((fAnimationLength + fNextAttackDelay) * 1000), new Runnable() {
            @Override
            public void run() {
                NWScript.setCommandable(true, oAttacker);
            }
        });

        Scheduler.flushQueues();

        // Loop for attack
        for (iCurrentShot = 1; iCurrentShot < iShots+1; iCurrentShot++)
        {
            // Out of ammo or target is dead - we're done here.
            if(iMagazineSize <= 0 ||
                    (NWScript.getIsPC(oTarget) && NWScript.getCurrentHitPoints(oTarget) <= -11) || // PCs
                    (!NWScript.getIsPC(oTarget) && NWScript.getIsDead(oTarget))) // NPCs
            {
                break;
            }
            // Reduce magazine bullets by 1 (or destroy the stack if it's the last bullet)
            else if(iMagazineSize == 1)
            {
                iMagazineSize = 0;
                NWScript.destroyObject(oMagazine, 0.0f);
            }
            else
            {
                iMagazineSize = iMagazineSize - 1;
                NWScript.setItemStackSize(oMagazine, iMagazineSize);
            }

            if (iAnimation == CustomAnimationType.Pistol || iAnimation == CustomAnimationType.PistolDual || iAnimation == CustomAnimationType.Shotgun)
            {
                // Each additional shot increases the sound delay
                if (iCurrentShot != 1)
                {
                    fShotDelay += fSoundDelay;
                }
            }
            else
            {
                // Default Timings
                fShotDelay += fSoundDelay;
            }

            final NWObject weapon1 = oWeapon1;
            final NWObject weapon2 = oWeapon2;
            final int animation = iAnimation;

            Scheduler.delay(oAttacker, (int) (fShotDelay * 1000), new Runnable() {
                @Override
                public void run() {
                    FireShot(oAttacker, oTarget, weapon1, animation, objSelf);
                }
            });
            Scheduler.flushQueues();


            // Second Shot for dual-pistols/SMGs
            if (iAnimation == CustomAnimationType.PistolDual || iAnimation == CustomAnimationType.SMGDual)
            {
                // Again, we need to ensure there's bullets available for use. Otherwise the second shot won't take NWEffect

                if(iMagazineSize <= 0)
                {
                    break;
                }
                // Reduce magazine bullets by 1 (or destroy the stack if it's the last bullet)
                else if(iMagazineSize == 1)
                {
                    iMagazineSize = 0;
                    NWScript.setLocalInt(oAttacker, GUN_TEMP_PREVENT_AMMO_BOX_BUG, 1);
                    NWScript.destroyObject(oMagazine, 0.0f);

                    Scheduler.delay(oAttacker, 100, new Runnable() {
                        @Override
                        public void run() {
                            NWScript.deleteLocalInt(oAttacker, GUN_TEMP_PREVENT_AMMO_BOX_BUG);
                        }
                    });
                    Scheduler.flushQueues();

                }
                else
                {
                    iMagazineSize = iMagazineSize - 1;
                    NWScript.setItemStackSize(oMagazine, iMagazineSize);
                }

                Scheduler.delay(oAttacker, (int)((fDualDelay + fShotDelay) * 1000), new Runnable() {
                    @Override
                    public void run() {
                        FireShot(oAttacker, oTarget, weapon2, animation, objSelf);
                    }
                });

                Scheduler.flushQueues();
                System.out.println("MainAttack: Finished schedling fireshot again"); // DEBUG
            }
        }

        // Display "Low Ammo" message when bullets reach specified threshold
        int iMaxMagazineSize = NWScript.floatToInt(stGun1Info.getMagazineSize() * GUN_LOW_AMMO_MESSAGE_DISPLAY_PERCENTAGE);
        if(iMaxMagazineSize < 1) iMaxMagazineSize = 1;

        // Update bullet count on the gun
        NWScript.setLocalInt(oWeapon1, GUN_MAGAZINE_BULLET_COUNT, iMagazineSize);

        if(iMagazineSize <= 0)
        {
            NWScript.floatingTextStringOnCreature(GUN_OUT_OF_AMMO_MESSAGE, oAttacker, false);
        }
        else if(iMagazineSize <= iMaxMagazineSize && iMagazineSize > 0)
        {
            NWScript.floatingTextStringOnCreature(GUN_LOW_AMMO_MESSAGE, oAttacker, false);
        }

        // Update gun name to reflect change in ammo currently chambered
        //UpdateItemName(oWeapon1); // TODO: Update item name

        // Fire durability system for gun
        //DCY_RunItemDecay(oPC, oWeapon1); // TODO: Run item decay system

        // Disabled auto-attack - There's not an easy way to stop this from firing infinitely because the NWNX OnAttack event only fires when combat initially starts
        //AssignCommand(oPC, DelayCommand(fAnimationLength+fNextAttackDelay+0.3,ActionAttack(oTarget)));
    }

    void FireShot(NWObject oAttacker, NWObject oTarget, NWObject oWeapon, int iAnimation, NWObject objSelf)
    {
        NWLocation lLocation = NWScript.getLocation(oAttacker);
        NWEffect eSound;
        int iSilenced = NWScript.getLocalInt(oAttacker, CBT_OVR_SVAR_ATTACK_SILENCED);

        GunGO stGunInfo = new GunGO(oWeapon);

        // Pistols
        if (iAnimation == CustomAnimationType.Pistol || iAnimation == CustomAnimationType.PistolDual)
        {
            if (iSilenced == 1)
            {
                eSound = NWScript.effectVisualEffect(CBT_OVR_SOUND_SHOT_SILENCED_PISTOL, false);
            }
            else
            {
                eSound = NWScript.effectVisualEffect(CBT_OVR_SOUND_SHOT_HEAVY_PISTOL_9MM, false);
            }
        }
        // SMGs
        else if (iAnimation == CustomAnimationType.SMG || iAnimation == CustomAnimationType.SMGDual)
        {
            if (iSilenced == 1)
            {
                eSound = NWScript.effectVisualEffect(CBT_OVR_SOUND_SHOT_SILENCED_PISTOL, false);
            }
            else
            {
                eSound = NWScript.effectVisualEffect(CBT_OVR_SOUND_SHOT_SMG_762, false);
            }
        }
        // Shotguns
        else if (iAnimation == CustomAnimationType.Shotgun)
        {
            eSound = NWScript.effectVisualEffect(CBT_OVR_SOUND_SHOT_SHOTGUN_12G, false);
        }
        else
        {
            if (iSilenced == 1)
            {
                eSound = NWScript.effectVisualEffect(CBT_OVR_SOUND_SHOT_SILENCED_RIFLE, false);
            }
            else
            {
                eSound = NWScript.effectVisualEffect(CBT_OVR_SOUND_SHOT_ASSAULT_RIFLE_762, false);
            }
        }

        NWScript.applyEffectAtLocation(DurationType.INSTANT, eSound, lLocation, 0.0f);

        // Fire shotgun blast
        if (iAnimation == CustomAnimationType.Shotgun)
        {
            FireShotgun(oAttacker, stGunInfo, objSelf);
        }
        // Otherwise, fire normal bullet
        else
        {
            FireBullet(oAttacker, oTarget, oWeapon, stGunInfo, objSelf);
        }
    }

    int bIsCriticalHit = 0;
    int CalculateDamage(final NWObject oAttacker, final NWObject oTarget, GunGO stGunInfo, int iFirearmSkill)
    {
        bIsCriticalHit = 0;

        float fDistance = NWScript.getDistanceBetween(oAttacker, oTarget);
        if(fDistance == 0.0) return 0;
        iFirearmSkill = iFirearmSkill + 1;
        float fMultiplier = ((NWScript.random(30) + 40)) * 0.001f + (iFirearmSkill * 0.005f);
        float fMaxRange = 0.0f;
        int iCriticalChance = 1 + NWScript.random(5) + NWScript.floatToInt(((iFirearmSkill) * 0.5f)) + stGunInfo.getCriticalRating();
        int iChanceToHit = 100;

        // Each weapon has a "sweet spot". When a player is within this range they receive a 0.02 multiplier bonus.

        // "Near" range setting
        if(stGunInfo.getRange() == IP_CONST_FIREARM_RANGE_NEAR)
        {
            fMaxRange = GUN_RANGE_NEAR_MAXIMUM;
            if(fDistance >= GUN_RANGE_NEAR_MINIMUM && fDistance <= GUN_RANGE_NEAR_MAXIMUM)
            {
                fMultiplier = fMultiplier + 0.02f;
            }
        }
        // "Mid" range setting
        else if(stGunInfo.getRange() == IP_CONST_FIREARM_RANGE_MID)
        {
            fMaxRange = GUN_RANGE_MID_MAXIMUM;
            if(fDistance >= GUN_RANGE_MID_MINIMUM && fDistance <= GUN_RANGE_MID_MAXIMUM)
            {
                fMultiplier = fMultiplier + 0.02f;
            }
        }
        // "Far" range setting
        else if(stGunInfo.getRange() == IP_CONST_FIREARM_RANGE_FAR)
        {
            fMaxRange = GUN_RANGE_FAR_MAXIMUM;
            if(fDistance >= GUN_RANGE_FAR_MINIMUM && fDistance <= GUN_RANGE_FAR_MAXIMUM)
            {
                fMultiplier = fMultiplier + 0.02f;
            }
            // Rifles (weapons with the "Far" range setting) receive a penalty to accuracy if a target is too close.
            // 3% accuracy loss per half meter below minimum range
            else if(fDistance < GUN_RANGE_FAR_MINIMUM)
            {
                iChanceToHit = iChanceToHit - NWScript.floatToInt((3 * (Math.abs(fDistance - GUN_RANGE_FAR_MINIMUM) / 0.5f)));
            }
        }

        // Multiplier falls off starting at max range + 2.5 meters at a rate of 0.005 per half meter outside of max range
        // Chance to head shot also decreases at a rate of 2% loss per half meter outside max range
        // Accuracy lost at a rate of 3% per half meter past max range
        fMaxRange = fMaxRange + 2.5f;
        if(fDistance > fMaxRange)
        {
            fMaxRange = Math.abs(fMaxRange - fDistance);
            fMultiplier = fMultiplier - (0.005f * (fMaxRange / 0.5f));
            iCriticalChance = iCriticalChance - NWScript.floatToInt((2 * (fMaxRange / 0.5f)));
            if(iCriticalChance < 0) iCriticalChance = 0;
            iChanceToHit = iChanceToHit - NWScript.floatToInt((3 * (fMaxRange / 0.5f)));
        }

        // Critical hit (Head shot) rolls. Note that shotguns do not have a chance to head shot since their spread is powerful already.
        if(NWScript.random(100) <= iCriticalChance && stGunInfo.getGunType() != GunType.Shotgun)
        {
            fMultiplier = fMultiplier + 0.04f;
            bIsCriticalHit = 1;
        }

        // Enhanced ammunition grants an additional 0.015 to the multiplier.
        int iAmmunitionType = NWScript.getLocalInt(stGunInfo.getGun(), GUN_TEMP_AMMO_LOADED_TYPE);
        if(iAmmunitionType == GUN_AMMUNITION_PRIORITY_ENHANCED)
        {
            fMultiplier = fMultiplier + 0.015f;
        }

        // Convert durability to a decimal, then multiply that value with the Firepower
        // Finally, multiply that value with the multiplier.
        int iFirepower = NWScript.floatToInt(stGunInfo.getFirepower() * (0.01f * stGunInfo.getDurability()));
        //NWScript.sendMessageToPC(oAttacker, "DEBUG: iFirepower = " + IntToString(iFirepower)); // DEBUG
        int iDamage = NWScript.floatToInt(iFirepower * fMultiplier);
        // Attack missed - return zero damaged
        if(NWScript.random(100) > iChanceToHit) return 0;

        // Always deal at least 1 damage when an attack hits
        if(iDamage <= 0) iDamage = 1;

        // Incendiary ammunition deals fire damage to the target over time. It also applies a nifty visual to the target.
        if(iAmmunitionType == GUN_AMMUNITION_PRIORITY_INCENDIARY)
        {
            NWScript.applyEffectToObject(DurationType.TEMPORARY, NWScript.effectVisualEffect(VfxDur.INFERNO_CHEST, false), oTarget, 3.0f);
            int bCurrentlyOnFire = NWScript.getLocalInt(oTarget, "INCENDIARY_DAMAGE_COUNTER");
            NWScript.setLocalInt(oTarget, "INCENDIARY_DAMAGE_COUNTER", 3);

            // We don't want multiple copies of this function firing at the same time, so ensure there's not another
            // one already going
            if(bCurrentlyOnFire == 0)
            {
                Scheduler.delay(oAttacker, 1000, new Runnable() {
                    @Override
                    public void run() {
                        IncendiaryDamage(oAttacker, oTarget, NWScript.random(3) + 3);
                    }
                });
                Scheduler.flushQueues();
            }
        }

        return iDamage;
    }

    public static void IncendiaryDamage(final NWObject oAttacker, final NWObject oTarget, final int iDamage)
    {
        // Exit when target is dead or invalid
        if(NWScript.getIsDead(oTarget) || !NWScript.getIsObjectValid(oTarget)) return;

        int iCounter = NWScript.getLocalInt(oTarget, "INCENDIARY_DAMAGE_COUNTER") - 1;
        Scheduler.assign(oAttacker, new Runnable() {
            @Override
            public void run() {
                NWScript.applyEffectToObject(DurationType.INSTANT, NWScript.effectDamage(iDamage, DamageType.FIRE, DamagePower.NORMAL), oTarget, 0.0f);
            }
        });
        Scheduler.flushQueues();


        // Exit when the incendiary NWEffect wears off
        if(iCounter <= 0) return;

        // Otherwise update the counter and call this function again in one second
        NWScript.setLocalInt(oTarget, "INCENDIARY_DAMAGE_COUNTER", iCounter);

        Scheduler.delay(oAttacker, 1000, new Runnable() {
            @Override
            public void run() {
                IncendiaryDamage(oAttacker, oTarget, NWScript.random(3) + 3);
            }
        });
        Scheduler.flushQueues();
        
    }

    // Formula found at: http://paulbourke.net/geometry/insidepoly/
    float CalcPosition(NWVector vP1, NWVector vP2, NWVector vPoint)
    {
        float x = vPoint.getX();
        float y = vPoint.getY();
        float x0 = vP1.getX();
        float y0 = vP1.getY();
        float x1 = vP2.getX();
        float y1 = vP2.getY();

        return (y - y0) * (x1 - x0) - (x - x0) * (y1 - y0);
    }

    int IsPointInShape(NWVector vOrigin, NWVector vStraightPosition, NWVector vRightPosition, NWVector vLeftPosition, NWVector vPoint)
    {
        // P1 = Origin
        // P2 = Left
        // P3 = Straight
        // P4 = Right

        // If < 0.0, right of line segment
        // If > 0.0, left of line segment
        // If = 0.0, on line segment
        float fResult = CalcPosition(vOrigin, vLeftPosition, vPoint);
        if(fResult < 0.0) return 0;
        fResult = CalcPosition(vLeftPosition, vStraightPosition, vPoint);
        if(fResult < 0.0) return 0;
        fResult = CalcPosition(vStraightPosition, vRightPosition, vPoint);
        if(fResult < 0.0) return 0;
        fResult = CalcPosition(vRightPosition, vOrigin, vPoint);
        if(fResult < 0.0) return 0;

        return 1;
    }

    void FireShotgun(NWObject oAttacker, GunGO stGunInfo, NWObject objSelf)
    {
        NWVector vOrigin = NWScript.getPosition(oAttacker);
        NWLocation lOrigin = NWScript.getLocation(oAttacker);
        float fFacing = NWScript.getFacing(oAttacker);
        
        int iShotgunSkill = ProgressionSystem.GetPlayerSkillLevel(oAttacker, ProgressionSystem.SkillType_SHOTGUN_PROFICIENCY);
        int iShotgunAccuracy = ProgressionSystem.GetPlayerSkillLevel(oAttacker, ProgressionSystem.SkillType_SHOTGUN_ACCURACY);
        float fRangeDistance = 7.5f;
        //NWLocation lTargetLocation = NWScript.getLocation(oTarget);
        int bMiss = 1;
        NWEffect eSparkEffect = NWScript.effectVisualEffect(VfxComBloodSpark.LARGE , false);
        NWEffect eBloodEffect = NWScript.effectVisualEffect(VfxComChunkRed.SMALL, false);

        NWVector vStraightPosition = Position.GetChangedPosition(vOrigin, fRangeDistance, fFacing);
        NWVector vRightPosition = Position.GetChangedPosition(vOrigin, fRangeDistance * 0.9f, fFacing + 45);
        NWVector vLeftPosition = Position.GetChangedPosition(vOrigin, fRangeDistance * 0.9f, fFacing - 45);


        NWObject[] sphereTargets = NWScript.getObjectsInShape(Shape.SPHERE, fRangeDistance, lOrigin, true, ObjectType.CREATURE, vOrigin);
        for(NWObject oSphereTarget : sphereTargets)
        {
            // PCs hit whatever is in range.
            // NPCs hit only enemies in range
            if(NWScript.getIsPC(oAttacker) || (!NWScript.getIsPC(oAttacker) && NWScript.getIsEnemy(oSphereTarget, objSelf)))
            {
                if(oSphereTarget != oAttacker && !NWScript.getIsDead(oSphereTarget) && !NWScript.getIsDead(oAttacker))
                {
                    if(IsPointInShape(vOrigin, vStraightPosition, vRightPosition, vLeftPosition, NWScript.getPosition(oSphereTarget)) == 1)
                    {
                        int iSphereTargetAC = NWScript.getAC(oSphereTarget) - iShotgunAccuracy;

                        if(NWScript.random(30) + 3 > iSphereTargetAC)
                        {
                            int iDamage = CalculateDamage(oAttacker, oSphereTarget, stGunInfo, iShotgunSkill);

                            if(iDamage > 0)
                            {
                                NWScript.applyEffectToObject(DurationType.INSTANT, NWScript.effectDamage(iDamage, DamageType.PIERCING, DamagePower.NORMAL), oSphereTarget, 0.0f);
                                NWScript.applyEffectToObject(DurationType.INSTANT, eSparkEffect, oSphereTarget, 0.0f);
                                NWScript.applyEffectToObject(DurationType.INSTANT, eBloodEffect, oSphereTarget, 0.0f);
                                bMiss = 0;
                            }
                        }
                        else
                        {
                            bMiss = 1;
                        }
                    }
                }
            }
        }

        // Enemies were out of range - display sparks randomly across the cone
        if(bMiss == 1)
        {
            NWObject oArea = NWScript.getArea(oAttacker);
            int iNumberOfSparks = NWScript.random(4) + 1;
            int iLoop;

            for(iLoop = 1; iLoop <= iNumberOfSparks; iLoop++)
            {
                fRangeDistance = NWScript.random(75) * 0.1f;
                NWVector vSparkPosition = Position.GetChangedPosition(vOrigin, fRangeDistance, NWScript.getFacing(oAttacker));
                NWScript.applyEffectAtLocation(DurationType.INSTANT, eSparkEffect, NWScript.location(oArea, vSparkPosition, 0.0f), 0.0f);
            }
        }
    }

    void FireBullet(NWObject oAttacker, NWObject oTarget, NWObject oWeapon, GunGO stGunInfo, NWObject objSelf)
    {
        NWVector vPosition = NWScript.getPosition(oAttacker);
        NWLocation lTargetLocation = NWScript.getLocation(oTarget);
        int bMiss;
        float fClosestDistance = 0.0f;
        float fStunDuration = 0.0f;
        int iTargetAC = NWScript.getAC(oTarget);

        // Get the correct skill, based on the type of firearm being used
        // Also determine how long the stun NWEffect lasts, based on weapon
        int iProficiencySkill = 0;
        int iAccuracySkill = 0;
        switch(stGunInfo.getGunType())
        {
            case GunType.Hangun:
                iProficiencySkill = ProgressionSystem.GetPlayerSkillLevel(oAttacker, ProgressionSystem.SkillType_HANDGUN_PROFICIENCY);
                iAccuracySkill = ProgressionSystem.GetPlayerSkillLevel(oAttacker, ProgressionSystem.SkillType_HANDGUN_ACCURACY);
                fStunDuration = 1.0f;
                break;
            case GunType.Rifle:
                iProficiencySkill = ProgressionSystem.GetPlayerSkillLevel(oAttacker, ProgressionSystem.SkillType_RIFLE_PROFICIENCY);
                iAccuracySkill = ProgressionSystem.GetPlayerSkillLevel(oAttacker, ProgressionSystem.SkillType_RIFLE_ACCURACY);
                fStunDuration = 1.0f;
                break;
            case GunType.SMG:
                iProficiencySkill = ProgressionSystem.GetPlayerSkillLevel(oAttacker, ProgressionSystem.SkillType_SMG_PROFICIENCY);
                iAccuracySkill = ProgressionSystem.GetPlayerSkillLevel(oAttacker, ProgressionSystem.SkillType_SMG_ACCURACY);
                fStunDuration = 1.0f;
                break;
            case GunType.AssaultRifle:
                iProficiencySkill = ProgressionSystem.GetPlayerSkillLevel(oAttacker, ProgressionSystem.SkillType_SMG_PROFICIENCY);
                iAccuracySkill = ProgressionSystem.GetPlayerSkillLevel(oAttacker, ProgressionSystem.SkillType_SMG_ACCURACY);
                fStunDuration = 1.0f;
                break;
            case GunType.Magnum:
                iProficiencySkill = ProgressionSystem.GetPlayerSkillLevel(oAttacker, ProgressionSystem.SkillType_MAGNUM_PROFICIENCY);
                iAccuracySkill = ProgressionSystem.GetPlayerSkillLevel(oAttacker, ProgressionSystem.SkillType_MAGNUM_ACCURACY);
                fStunDuration = 1.0f;
                break;
        }

        // Gas canisters always take priority when targeting. Otherwise players will cry about not hitting them when they want to.
        String sTag = NWScript.getTag(oTarget);
        if(!Objects.equals(sTag, GUN_GAS_CANISTER_TAG))
        {
            NWObject[] shapeTargets = NWScript.getObjectsInShape(Shape.SPELLCYLINDER, 16.0f, lTargetLocation, true, ObjectType.CREATURE, vPosition);

            for(NWObject oCylinderTarget : shapeTargets)
            {
                // PCs hit whatever is in range.
                // NPCs hit only enemies in range
                if(NWScript.getIsPC(oAttacker) || (!NWScript.getIsPC(oAttacker) && NWScript.getIsEnemy(oCylinderTarget, objSelf)))
                {
                    // Found a valid target. See if it's closest to attacker
                    if(!NWScript.getIsDead(oCylinderTarget) && oAttacker != oCylinderTarget && !NWScript.getIsDead(oAttacker))
                    {
                        float fDistanceCheck = NWScript.getDistanceBetween(oCylinderTarget, oAttacker);

                        if(fDistanceCheck < fClosestDistance || fClosestDistance == 0.0)
                        {
                            PlayerGO playerGO = new PlayerGO(oCylinderTarget);
                            // Check for sanctuary status
                            if(!playerGO.hasPVPSanctuary())
                            {
                                oTarget = oCylinderTarget;
                                fClosestDistance = fDistanceCheck;
                            }
                        }
                    }
                }
            }
        }

        // Do accuracy check
        iTargetAC = iTargetAC - iAccuracySkill;

        if(NWScript.random(30) + 1 > iTargetAC)
        {
            bMiss = 0;
        }
        else
        {
            bMiss = 1;
        }

        // Calculate damage
        int iDamage = CalculateDamage(oAttacker, oTarget, stGunInfo, iProficiencySkill);
        if(iDamage <= 0) bMiss = 1;

        if(bMiss == 0)
        {
            if(bIsCriticalHit == 1)
            {
                NWScript.floatingTextStringOnCreature("Critical hit!", oAttacker, false);
            }

            NWEffect eSparkEffect = NWScript.effectVisualEffect(VfxComBloodSpark.LARGE, false);

            if(iDamage > 0)
            {
                int iStoppingPower = 0;
                NWItemProperty[] itemProperties = NWScript.getItemProperties(oWeapon);
                
                for(NWItemProperty ipStoppingPower : itemProperties)
                {
                    if(NWScript.getItemPropertyType(ipStoppingPower) == 139) // 139 = Stopping Power item property
                    {
                        iStoppingPower = NWScript.getItemPropertyCostTableValue(ipStoppingPower);
                        break;
                    }
                }

                if(iStoppingPower >= 100)
                {
                    NWScript.applyEffectToObject(DurationType.TEMPORARY, NWScript.effectCutsceneImmobilize(), oTarget, fStunDuration);
                }
                else if(iStoppingPower > 0)
                {
                    NWScript.applyEffectToObject(DurationType.TEMPORARY, NWScript.effectMovementSpeedDecrease(iStoppingPower), oTarget, fStunDuration);
                }

                NWScript.applyEffectToObject(DurationType.INSTANT, NWScript.effectDamage(iDamage, DamageType.PIERCING, DamagePower.NORMAL), oTarget, 0.0f);
            }
            NWScript.applyEffectToObject(DurationType.INSTANT, eSparkEffect, oTarget, 0.0f);
        }
        else
        {
            NWScript.sendMessageToPC(oAttacker, ColorToken.Gray() + "You missed your target." + ColorToken.End());
        }
    }




}
