package contagionJVM.System;

import contagionJVM.Helper.ColorToken;
import contagionJVM.Entities.PlayerEntity;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.NWNX.NWNX_Funcs;
import contagionJVM.Repository.PlayerRepository;
import org.nwnx.nwnx2.jvm.NWLocation;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.Scheduler;
import org.nwnx.nwnx2.jvm.constants.*;
import org.nwnx.nwnx2.jvm.constants.Package;

public class DiseaseSystem {

    public static int DCCheck = 10;


    public void IncreaseDiseaseLevel(final NWObject oPC, int iIncreaseBy)
    {
        PlayerGO pcGO = new PlayerGO(oPC);
        PlayerRepository repo = new PlayerRepository();
        PlayerEntity entity = repo.getByUUID(pcGO.getUUID());
        entity.setCurrentInfection(entity.getCurrentInfection() + iIncreaseBy);

        NWScript.applyEffectToObject(DurationType.INSTANT, NWScript.effectVisualEffect(Vfx.IMP_DISEASE_S, false), oPC, 0.0f);
        NWScript.sendMessageToPC(oPC, ColorToken.Red() + "Infection Level: " + entity.getCurrentInfection() + "%" + ColorToken.End());

        if(entity.getCurrentInfection() >= entity.getInfectionCap())
        {
            CreateZombieClone(oPC);
            NWScript.applyEffectToObject(Duration.TYPE_INSTANT, NWScript.effectHeal(NWScript.getMaxHitPoints(oPC)), oPC, 0.0f);
            NWScript.applyEffectToObject(DurationType.TEMPORARY, NWScript.effectCutsceneImmobilize(), oPC, 6.0f);

            Scheduler.delay(oPC, 1000, new Runnable() {
                @Override
                public void run() {
                    NWScript.actionJumpToLocation(NWScript.getLocation(NWScript.getWaypointByTag("DEATH_REALM_LOST_SOULS")));
                }
            });

            // TODO: Give death token

            NWScript.floatingTextStringOnCreature("The infection has taken over your body!", oPC, false);
        }

        repo.save(entity);
        Scheduler.flushQueues();
    }

    public static PlayerEntity RunDiseaseRemovalProcess(NWObject oPC, PlayerEntity entity)
    {
        entity.setInfectionRemovalTick(entity.getInfectionRemovalTick() - 1);

        if(entity.getInfectionRemovalTick() <= 0)
        {
            entity.setCurrentInfection(NWScript.random(10) + 5);
            NWScript.sendMessageToPC(oPC, "Your body fights off some of the infection...");

            entity.setInfectionRemovalTick(600);
        }

        return entity;
    }


    private void CloneFight(final NWObject objSelf)
    {
        if ((NWScript.getCurrentAction(objSelf) == Action.INVALID) && (NWScript.getLocalInt(objSelf, "feeding") == 0))
        {
            NWScript.clearAllActions(false);
            NWScript.actionRandomWalk();
        }

        Scheduler.delay(objSelf, 6000, new Runnable() {
            @Override
            public void run() {
                CloneFight(objSelf);
            }
        });
        Scheduler.flushQueues();
    }

    private void CreateZombieClone(final NWObject oPC)
    {
        PlayerGO pcGO = new PlayerGO(oPC);

        NWLocation lLocation = NWScript.getLocation(oPC);
        String sClawResref = "reo_zombie_claw";
        final NWObject oClone = NWScript.copyObject(oPC, lLocation, NWObject.INVALID, "reo_zombie_000");
        NWScript.applyEffectToObject(DurationType.INSTANT, NWScript.effectHeal(NWScript.getMaxHitPoints(oClone)), oClone, 0.0f);

        NWNX_Funcs.RemoveKnownFeat(oClone, Feat.WEAPON_PROFICIENCY_EXOTIC);
        NWNX_Funcs.RemoveKnownFeat(oClone, Feat.WEAPON_PROFICIENCY_MARTIAL);
        NWNX_Funcs.RemoveKnownFeat(oClone, Feat.WEAPON_PROFICIENCY_SIMPLE);
        NWNX_Funcs.RemoveKnownFeat(oClone, Feat.SHIELD_PROFICIENCY);

        // All inventory items need to be set to droppable, except undroppable ones
        // Undroppable items are destroyed

        NWObject[] items = NWScript.getItemsInInventory(oClone);
        for(NWObject item : items)
        {
            if(!NWScript.getItemCursedFlag(item))
            {
                NWScript.setDroppableFlag(item, true);
            }
            else
            {
                NWScript.destroyObject(item, 0.0f);
            }
        }

        // Loop through PC's equipped slots and do the same
        int iSlot;
        for(iSlot = 0; iSlot < 18; iSlot++)
        {
            final NWObject oInventory = NWScript.getItemInSlot(iSlot, oClone);

            if(!NWScript.getItemCursedFlag(oInventory))
            {
                NWScript.setDroppableFlag(oInventory, true);
                Scheduler.assign(oClone, new Runnable() {
                    @Override
                    public void run() {
                        NWScript.actionUnequipItem(oInventory);
                    }
                });

            }
            else
            {
                NWScript.destroyObject(oInventory, 0.0f);
            }
        }

        // Zombie Claws and Hide needs to be equipped
        final NWObject belt = NWScript.createItemOnObject("ZombieCloneBelt", oClone, 1, "");
        NWScript.setDroppableFlag(belt, false);
        Scheduler.assign(oClone, new Runnable() {
            @Override
            public void run() {
                NWScript.actionEquipItem(belt, InventorySlot.BELT);
            }
        });

        final NWObject claw1 = NWScript.createItemOnObject(sClawResref, oClone, 1, "");
        NWScript.setDroppableFlag(claw1, false);
        Scheduler.assign(oClone, new Runnable() {
            @Override
            public void run() {
                NWScript.actionEquipItem(claw1, InventorySlot.CWEAPON_B);
            }
        });


        final NWObject claw2 = NWScript.createItemOnObject(sClawResref, oClone, 1, "");
        NWScript.setDroppableFlag(claw2, false);
        Scheduler.assign(oClone, new Runnable() {
            @Override
            public void run() {
                NWScript.actionEquipItem(claw2, InventorySlot.CWEAPON_L);
            }
        });

        final NWObject claw3 = NWScript.createItemOnObject(sClawResref, oClone, 1, "");
        NWScript.setDroppableFlag(claw3, false);
        Scheduler.assign(oClone, new Runnable() {
            @Override
            public void run() {
                NWScript.actionEquipItem(claw3, InventorySlot.CWEAPON_R);
            }
        });

        final NWObject creatureArmor = NWScript.createItemOnObject("rotd_zombieprop", oClone, 1, "");
        NWScript.setDroppableFlag(creatureArmor, false);
        Scheduler.assign(oClone, new Runnable() {
            @Override
            public void run() {
                NWScript.actionEquipItem(claw1, InventorySlot.CARMOUR);
            }
        });

        NWScript.applyEffectToObject(DurationType.PERMANENT, NWScript.supernaturalEffect(NWScript.effectMovementSpeedDecrease(15)), oClone, 0.0f);
        NWScript.levelUpHenchman(oClone, ClassType.UNDEAD, false, Package.UNDEAD);

        // Change faction to hostile and fire a recursive call
        NWScript.changeToStandardFaction(oClone, StandardFaction.HOSTILE);

        Scheduler.assign(oClone, new Runnable() {
            @Override
            public void run() {
                NWScript.clearAllActions(false);
            }
        });
        Scheduler.delay(oClone, 2000, new Runnable() {
            @Override
            public void run() {
                Scheduler.assign(oClone, new Runnable() {
                    @Override
                    public void run() {
                        CloneFight(oClone);
                    }
                });
            }
        });

        Scheduler.flushQueues();
    }

}
