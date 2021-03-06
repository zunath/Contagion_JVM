package contagionJVM.Event;

import contagionJVM.Entities.PlayerEntity;
import contagionJVM.Helper.ColorToken;
import contagionJVM.Constants;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.IScriptEventHandler;
import contagionJVM.NWNX.NWNX_Funcs;
import contagionJVM.Repository.PlayerRepository;
import contagionJVM.System.PlayerAuthorizationSystem;
import contagionJVM.System.ProgressionSystem;
import contagionJVM.System.RadioSystem;
import org.nwnx.nwnx2.jvm.*;
import org.nwnx.nwnx2.jvm.constants.*;

@SuppressWarnings("unused")
public class Module_OnClientEnter implements IScriptEventHandler {
    @Override
    public void runScript(final NWObject objSelf) {
        RadioSystem radioSystem = new RadioSystem();

        // Bioware Default
        NWScript.executeScript("x3_mod_def_enter", objSelf);
        InitializeNewCharacter();
        LoadCharacter();
        // SimTools
        NWScript.executeScript("fky_chat_clenter", objSelf);
        // Radio System - Also used for NWNX chat (different from SimTools)
        radioSystem.OnModuleEnter();
        // DM Validation
        NWScript.executeScript("dm_authorization", objSelf);
        // PC Validation
        NWScript.executeScript("auth_mod_enter", objSelf);
        ShowMOTD();
        ApplyGhostwalk();
        // Validate CD Key
        PlayerAuthorizationSystem.OnModuleEnter();
    }

    private void ApplyGhostwalk()
    {
        NWObject oPC = NWScript.getEnteringObject();

        if(!NWScript.getIsPC(oPC) || NWScript.getIsDM(oPC)) return;

        NWEffect eGhostWalk = NWScript.effectCutsceneGhost();
        NWScript.applyEffectToObject(Duration.TYPE_PERMANENT, eGhostWalk, oPC, 0.0f);

    }

    private void InitializeNewCharacter()
    {
        final NWObject oPC = NWScript.getEnteringObject();
        PlayerGO pcGO = new PlayerGO(oPC);
        NWObject oDatabase = pcGO.GetDatabaseItem();

        if(NWScript.getIsDM(oPC) || NWScript.getLocalInt(oDatabase, "MIGRATED_TO_OAL") == 1) return;
        boolean missingStringID = NWScript.getLocalString(oDatabase, Constants.PCIDNumberVariable).equals("");
        boolean missingIntegerID = NWScript.getLocalInt(oDatabase, Constants.PCIDNumberVariable) <= 0;

        if(oDatabase == NWObject.INVALID || (missingStringID && missingIntegerID))
        {
            pcGO.destroyAllEquippedItems();
            pcGO.destroyAllInventoryItems(true);

            oDatabase = NWScript.createItemOnObject(Constants.PCDatabaseTag, oPC, 1, "");

            Scheduler.assign(oPC, new Runnable() {
                @Override
                public void run() {
                    NWScript.takeGoldFromCreature(NWScript.getGold(oPC), oPC, true);
                    NWScript.giveGoldToCreature(oPC, 10);
                }
            });

            NWScript.createItemOnObject("fky_chat_target", oPC, 1, "");
            NWScript.createItemOnObject("food_bread", oPC, 1, "");
            NWScript.createItemOnObject("combat_knife", oPC, 1, "");

            Scheduler.assign(oPC, new Runnable() {
                @Override
                public void run() {
                    NWObject oClothes = NWScript.createItemOnObject("starting_shirt", oPC, 1, "");
                    NWScript.actionEquipItem(oClothes, InventorySlot.CHEST);
                }
            });

            for(int slot = 0; slot <= 10; slot++)
            {
                NWNX_Funcs.SetRawQuickBarSlot(oPC, slot + " 0 0 0 0");
            }

            // Save to database
            PlayerRepository repo = new PlayerRepository();
            PlayerEntity entity = pcGO.createEntity();
            repo.save(entity);

            pcGO.setCreateDate();

            ProgressionSystem.InitializePlayer(oPC);
            NWNX_Funcs.SetRawQuickBarSlot(oPC, "1 4 0 1116 0");
            NWScript.setLocalInt(oDatabase, "MIGRATED_TO_OAL", 1);
            Scheduler.delay(oPC, 1000, new Runnable() {
                @Override
                public void run() {
                    NWScript.applyEffectToObject(DurationType.INSTANT, NWScript.effectHeal(999), oPC, 0.0f);
                }
            });
        }
    }

    private void ShowMOTD()
    {
        final NWObject oPC = NWScript.getEnteringObject();
        final String sMOTD = NWScript.getLocalString(NWObject.MODULE, "MOTD");
        final String message = ColorToken.Green() + "Welcome to Outbreak: After Life!\n\nMOTD:" + ColorToken.White() +  sMOTD + ColorToken.End();

        Scheduler.delay(oPC, 6500, new Runnable() {
            @Override
            public void run() {
                NWScript.sendMessageToPC(oPC, message);
            }
        });
    }

    private void LoadCharacter()
    {
        final NWObject oPC = NWScript.getEnteringObject();
        PlayerGO pcGO = new PlayerGO(oPC);
        PlayerRepository repo = new PlayerRepository();
        PlayerEntity entity = repo.getByUUID(pcGO.getUUID());

        if(entity == null) return;

        int hp = NWScript.getCurrentHitPoints(oPC);
        int damage;
        if(entity.getHitPoints() < 0)
        {
            damage = hp + Math.abs(entity.getHitPoints());
        }
        else
        {
            damage = hp - entity.getHitPoints();
        }

        if(damage != 0)
        {
            NWScript.applyEffectToObject(DurationType.INSTANT, NWScript.effectDamage(damage, DamageType.MAGICAL, DamagePower.NORMAL), oPC, 0.0f);
        }
    }
}
