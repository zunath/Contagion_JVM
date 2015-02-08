package contagionJVM.System;

import contagionJVM.Constants;
import contagionJVM.Entities.PlayerEntity;
import contagionJVM.Entities.PlayerProgressionSkillEntity;
import contagionJVM.Entities.ProgressionLevelEntity;
import contagionJVM.Entities.ProgressionSkillEntity;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.Helper.ColorToken;
import contagionJVM.NWNX.NWNX_Funcs;
import contagionJVM.Repository.PlayerProgressionSkillsRepository;
import contagionJVM.Repository.PlayerRepository;
import contagionJVM.Repository.ProgressionLevelRepository;
import contagionJVM.Repository.ProgressionSkillRepository;
import org.nwnx.nwnx2.jvm.NWItemProperty;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.Scheduler;
import org.nwnx.nwnx2.jvm.constants.Ability;
import org.nwnx.nwnx2.jvm.constants.Feat;
import org.nwnx.nwnx2.jvm.constants.SavingThrow;

import java.util.Objects;
import java.util.logging.Level;

public class ProgressionSystem {

    // Configuration
    private final int LevelCap = 50;
    private final int SPEarnedOnLevelUp = 10;
    private final int ResetInitialCost = 2000;
    private final int ResetAdditionalCost = 500;
    private final int ResetCooldownDays = 3;
    private final boolean IsResetFirstTimeFree = true;


    public static final int SkillType_INVALID                    = 0;
    public static final int SkillType_HP                         = 1;
    public static final int SkillType_INVENTORY_SPACE            = 2;
    public static final int SkillType_ARMOR                      = 3;
    public static final int SkillType_HANDGUN_PROFICIENCY        = 4;
    public static final int SkillType_SHOTGUN_PROFICIENCY        = 5;
    public static final int SkillType_RIFLE_PROFICIENCY          = 6;
    public static final int SkillType_SMG_PROFICIENCY            = 7;
    public static final int SkillType_MAGNUM_PROFICIENCY         = 8;
    public static final int SkillType_MELEE                      = 9;
    public static final int SkillType_SEARCH                     = 10;
    public static final int SkillType_HIDE                       = 11;
    public static final int SkillType_MOVE_SILENTLY              = 12;
    public static final int SkillType_FIRST_AID                  = 13;
    public static final int SkillType_LOCKPICKING                = 14;
    public static final int SkillType_MIXING                     = 15;
    public static final int SkillType_SPRING_ATTACK              = 16;
    public static final int SkillType_POWER_ATTACK               = 17;
    public static final int SkillType_AMBIDEXTERITY              = 18;
    public static final int SkillType_TWO_WEAPON_FIGHTING        = 19;
    public static final int SkillType_ITEM_REPAIR                = 20;
    public static final int SkillType_DISEASE_RESISTANCE         = 21;
    public static final int SkillType_HANDGUN_ACCURACY           = 22;
    public static final int SkillType_SHOTGUN_ACCURACY           = 23;
    public static final int SkillType_RIFLE_ACCURACY             = 24;
    public static final int SkillType_SMG_ACCURACY               = 25;
    public static final int SkillType_MAGNUM_ACCURACY            = 26;
    public static final int SkillType_NATURAL_REGENERATION       = 27;
    public static final int SkillType_COMPUTER_LITERACY          = 28;
    public static final int SkillType_SPEECH                     = 29;



    public void InitializePlayer(NWObject oPC, boolean resetFeats)
    {
        if(!NWScript.getIsPC(oPC)) return;
        PlayerGO pcGO = new PlayerGO(oPC);
        PlayerRepository playerRepo = new PlayerRepository();
        PlayerProgressionSkillsRepository playerSkillRepo = new PlayerProgressionSkillsRepository();
        PlayerEntity entity = playerRepo.getByUUID(pcGO.getUUID());

        if(resetFeats)
        {
            int numberOfFeats = NWNX_Funcs.GetTotalKnownFeats(oPC);
            for(int currentFeat = 1; currentFeat <= numberOfFeats; currentFeat++)
            {
                NWNX_Funcs.SetKnownFeat(oPC, currentFeat, -1);
            }

            NWNX_Funcs.AddKnownFeat(oPC, Feat.ARMOR_PROFICIENCY_LIGHT, -1);
            NWNX_Funcs.AddKnownFeat(oPC, Feat.ARMOR_PROFICIENCY_MEDIUM, -1);
            NWNX_Funcs.AddKnownFeat(oPC, Feat.ARMOR_PROFICIENCY_HEAVY, -1);
            NWNX_Funcs.AddKnownFeat(oPC, Feat.SHIELD_PROFICIENCY, -1);
            NWNX_Funcs.AddKnownFeat(oPC, Feat.WEAPON_PROFICIENCY_EXOTIC, -1);
            NWNX_Funcs.AddKnownFeat(oPC, Feat.WEAPON_PROFICIENCY_MARTIAL, -1);
            NWNX_Funcs.AddKnownFeat(oPC, Feat.WEAPON_PROFICIENCY_SIMPLE, -1);
            NWNX_Funcs.AddKnownFeat(oPC, 1116, -1); // Reload

            NWNX_Funcs.SetAbilityScore(oPC, Ability.STRENGTH, Constants.BaseStrength);
            NWNX_Funcs.SetAbilityScore(oPC, Ability.DEXTERITY, Constants.BaseDexterity);
            NWNX_Funcs.SetAbilityScore(oPC, Ability.CONSTITUTION, Constants.BaseConstitution);
            NWNX_Funcs.SetAbilityScore(oPC, Ability.WISDOM, Constants.BaseWisdom);
            NWNX_Funcs.SetAbilityScore(oPC, Ability.CHARISMA, Constants.BaseCharisma);
            NWNX_Funcs.SetAbilityScore(oPC, Ability.INTELLIGENCE, Constants.BaseIntelligence);

            NWNX_Funcs.SetMaxHitPointsByLevel(oPC, 1, Constants.BaseHitPoints);
            NWNX_Funcs.SetCurrentHitPoints(oPC, NWScript.getMaxHitPoints(oPC));

            for(int iCurSkill = 1; iCurSkill <= 27; iCurSkill++)
            {
                NWNX_Funcs.SetSkillRank(oPC, iCurSkill, 0);
            }
            NWNX_Funcs.SetSavingThrowBonus(oPC, SavingThrow.FORT, 0);
            NWNX_Funcs.SetSavingThrowBonus(oPC, SavingThrow.REFLEX, 0);
            NWNX_Funcs.SetSavingThrowBonus(oPC, SavingThrow.WILL, 0);
        }

        playerSkillRepo.deleteAllByPlayerUUID(pcGO.getUUID());
        entity.setUnallocatedSP(SPEarnedOnLevelUp * entity.getLevel());
        entity.setRegenerationTick(Constants.BaseHPRegenRate);
        entity.setHpRegenerationAmount(Constants.BaseHPRegenAmount);
        entity.setInventorySpaceBonus(0);

        playerRepo.save(entity);
    }


    public void GiveExperienceToPC(NWObject oPC, int amount)
    {
        if(amount <= 0 || NWScript.getIsDM(oPC)) return;

        PlayerGO pcGO = new PlayerGO(oPC);
        PlayerRepository repo = new PlayerRepository();
        PlayerEntity entity = repo.getByUUID(pcGO.getUUID());
        entity.setExperience(entity.getExperience() + amount);

        ProgressionLevelRepository levelRepo = new ProgressionLevelRepository();
        ProgressionLevelEntity levelEntity = levelRepo.getByLevel(entity.getLevel());

        NWScript.floatingTextStringOnCreature("You earned " + amount + " experience points.", oPC, false);

        if(entity.getLevel() >= LevelCap && entity.getExperience() >= levelEntity.getExperience())
        {
            entity.setExperience(levelEntity.getExperience() - 1);
        }

        while(entity.getExperience() >= levelEntity.getExperience())
        {
            entity.setExperience(entity.getExperience() - levelEntity.getExperience());
            entity.setUnallocatedSP(entity.getUnallocatedSP() + SPEarnedOnLevelUp);
            entity.setLevel(entity.getLevel() + 1);
            NWScript.sendMessageToPC(oPC, "You have attained level " + entity.getLevel() + "!");

            levelEntity = levelRepo.getByLevel(entity.getLevel());
        }

        repo.save(entity);
    }


    public void PurchaseSkillUpgrade(NWObject oPC, int skillID)
    {
        PlayerGO pcGO = new PlayerGO(oPC);
        PlayerRepository playerRepo = new PlayerRepository();
        PlayerEntity playerEntity = playerRepo.getByUUID(pcGO.getUUID());

        ProgressionSkillRepository skillRepo = new ProgressionSkillRepository();
        ProgressionSkillEntity skillEntity = skillRepo.getByID(skillID);

        PlayerProgressionSkillsRepository playerSkillRepo = new PlayerProgressionSkillsRepository();
        PlayerProgressionSkillEntity playerSkillEntity = playerSkillRepo.GetByUUIDAndSkillID(pcGO.getUUID(), skillID);

        if(playerSkillEntity.equals(null))
        {
            playerSkillEntity = new PlayerProgressionSkillEntity();
            playerSkillEntity.setPcID(pcGO.getUUID());
            playerSkillEntity.setProgressionSkillID(skillID);
        }

        playerSkillEntity.setUpgradeLevel(playerSkillEntity.getUpgradeLevel() + 1);

        int requiredSP = skillEntity.getInitialPrice() + playerSkillEntity.getUpgradeLevel() + 1;

        if(playerSkillEntity.getUpgradeLevel() > skillEntity.getMaxUpgrades())
        {
            NWScript.sendMessageToPC(oPC, ColorToken.Red() + "You cannot increase that skill any further." + ColorToken.End());
            return;
        }

        if(playerEntity.getUnallocatedSP() < requiredSP)
        {
            NWScript.sendMessageToPC(oPC, ColorToken.Red() + "You do not have enough SP to make that purchase." + ColorToken.End());
            return;
        }

        playerEntity.setUnallocatedSP(playerEntity.getUnallocatedSP() - requiredSP);
        playerSkillEntity.setUpgradeLevel(playerSkillEntity.getUpgradeLevel() + 1);
        ApplyCustomUpgradeEffects(skillID);

        playerRepo.save(playerEntity);
        playerSkillRepo.save(playerSkillEntity);
    }


    private void ApplyCustomUpgradeEffects(int skillID)
    {
        // TODO: Case statement for custom stat upgrades
    }

    public boolean DoesPlayerMeetItemSkillRequirements(NWObject oPC, NWObject oItem)
    {
        boolean canWear = true;
        PlayerGO pcGO = new PlayerGO(oPC);
        ProgressionSkillRepository skillRepo = new ProgressionSkillRepository();
        PlayerProgressionSkillsRepository repo = new PlayerProgressionSkillsRepository();

        NWItemProperty[] itemProperties = NWScript.getItemProperties(oItem);
        for(NWItemProperty ip : itemProperties)
        {
            if(NWScript.getItemPropertyType(ip) == 138) // 138 == Skill Requirement
            {
                int skillID = NWScript.getItemPropertySubType(ip);
                int skillRequired = NWScript.getItemPropertyCostTableValue(ip);
                PlayerProgressionSkillEntity playerSkillEntity = repo.GetByUUIDAndSkillID(pcGO.getUUID(), skillID);
                ProgressionSkillEntity skillEntity = skillRepo.getByID(skillID);

                if(playerSkillEntity.equals(null) || playerSkillEntity.getUpgradeLevel() < skillRequired)
                {
                    canWear = false;
                    NWScript.sendMessageToPC(oPC, ColorToken.Red() + "You lack the required " + skillEntity.getName() + " skill to equip that item. (Required: " + skillRequired + ")" + ColorToken.End());
                }
            }
        }

        return canWear;
    }

    public static int GetPlayerSkillLevel(NWObject oPC, int skillID)
    {
        PlayerGO pcGO = new PlayerGO(oPC);
        PlayerProgressionSkillsRepository repo = new PlayerProgressionSkillsRepository();
        PlayerProgressionSkillEntity entity = repo.GetByUUIDAndSkillID(pcGO.getUUID(), skillID);

        return entity == null ? 0 : entity.getUpgradeLevel();
    }

    public void OnModuleEquip()
    {
        NWObject oPC = NWScript.getPCItemLastEquippedBy();

        if(!NWScript.getIsPC(oPC) || NWScript.getIsDM(oPC)) return;

        final NWObject oItem = NWScript.getPCItemLastEquipped();
        String resref = NWScript.getResRef(oItem);
        String name = NWScript.getName(oItem, false);

        if(Objects.equals(name, "PC Properties") || !NWScript.getIsObjectValid(oItem)) return;

        if(!DoesPlayerMeetItemSkillRequirements(oPC, oItem))
        {
            Scheduler.assign(oPC, new Runnable() {
                @Override
                public void run() {
                    NWScript.clearAllActions(false);
                    NWScript.actionUnequipItem(oItem);
                }
            });
        }

    }


}
