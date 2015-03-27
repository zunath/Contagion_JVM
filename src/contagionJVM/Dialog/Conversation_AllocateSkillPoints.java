package contagionJVM.Dialog;

import contagionJVM.Entities.PlayerEntity;
import contagionJVM.Entities.PlayerProgressionSkillEntity;
import contagionJVM.Entities.ProgressionSkillEntity;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.Helper.ColorToken;
import contagionJVM.Repository.PlayerProgressionSkillsRepository;
import contagionJVM.Repository.PlayerRepository;
import contagionJVM.Repository.ProgressionSkillRepository;
import contagionJVM.System.ProgressionSystem;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

@SuppressWarnings("UnusedDeclaration")
public class Conversation_AllocateSkillPoints extends DialogBase implements IDialogHandler {
    @Override
    public PlayerDialog Initialize(NWObject oPC) {
        PlayerDialog dialog = new PlayerDialog();
        DialogPage mainPage = new DialogPage(
                "Please select a category.",
                "Statistics",
                "Proficiencies",
                "Utility",
                "Abilities"
        );

        DialogPage statsPage = new DialogPage(
                "Please select an upgrade.",
                "Hit Points",
                "Strength",
                "Dexterity",
                "Constitution",
                "Wisdom",
                "Intelligence",
                "Charisma",
                "Inventory"
        );

        DialogPage proficienciesPage = new DialogPage(
                "Please select an upgrade.",
                "Armor Proficiency",
                "Handgun Proficiency",
                "Shotgun Proficiency",
                "Rifle Proficiency",
                "SMG Proficiency",
                "Magnum Proficiency"
        );

        DialogPage utilityPage = new DialogPage(
                "Please select an upgrade.",
                "Search",
                "Hide",
                "Move Silently",
                "First Aid",
                "Lockpicking",
                "Mixing",
                "Item Repair"
        );

        DialogPage abilitiesPage = new DialogPage(
                "Please select an upgrade.",
                "Power Attack",
                "Ambidexterity",
                "Two-Weapon Fighting"
        );

        DialogPage upgradePage = new DialogPage(
                "<REPLACED LATER>",
                "Upgrade",
                "Back",
                "Return to Category List"
        );

        dialog.addPage("MainPage", mainPage);
        dialog.addPage("StatsPage", statsPage);
        dialog.addPage("ProficienciesPage", proficienciesPage);
        dialog.addPage("UtilityPage", utilityPage);
        dialog.addPage("AbilitiesPage", abilitiesPage);
        dialog.addPage("UpgradePage", upgradePage);

        return dialog;
    }

    @Override
    public void DoAction(NWObject oPC, String pageName, int responseID) {
        switch (pageName) {
            case "MainPage":
                switch (responseID) {
                    // Statistics
                    case 1:
                        ChangePage("StatsPage");
                        break;
                    // Proficiencies
                    case 2:
                        ChangePage("ProficienciesPage");
                        break;
                    // Utility
                    case 3:
                        ChangePage("UtilityPage");
                        break;
                    // Abilities
                    case 4:
                        ChangePage("AbilitiesPage");
                        break;
                }
                break;
            case "StatsPage":
                switch (responseID) {
                    case 1:
                        LoadSkillUpgradePage(ProgressionSystem.SkillType_HP);
                        break;
                    case 2:
                        LoadSkillUpgradePage(ProgressionSystem.SkillType_STRENGTH);
                        break;
                    case 3:
                        LoadSkillUpgradePage(ProgressionSystem.SkillType_DEXTERITY);
                        break;
                    case 4:
                        LoadSkillUpgradePage(ProgressionSystem.SkillType_CONSTITUTION);
                        break;
                    case 5:
                        LoadSkillUpgradePage(ProgressionSystem.SkillType_WISDOM);
                        break;
                    case 6:
                        LoadSkillUpgradePage(ProgressionSystem.SkillType_INTELLIGENCE);
                        break;
                    case 7:
                        LoadSkillUpgradePage(ProgressionSystem.SkillType_CHARISMA);
                        break;
                    case 8:
                        LoadSkillUpgradePage(ProgressionSystem.SkillType_INVENTORY_SPACE);
                        break;
                }
                break;
            case "ProficienciesPage":
                switch (responseID) {
                    case 1:
                        LoadSkillUpgradePage(ProgressionSystem.SkillType_ARMOR);
                        break;
                    case 2:
                        LoadSkillUpgradePage(ProgressionSystem.SkillType_HANDGUN_PROFICIENCY);
                        break;
                    case 3:
                        LoadSkillUpgradePage(ProgressionSystem.SkillType_SHOTGUN_PROFICIENCY);
                        break;
                    case 4:
                        LoadSkillUpgradePage(ProgressionSystem.SkillType_RIFLE_PROFICIENCY);
                        break;
                    case 5:
                        LoadSkillUpgradePage(ProgressionSystem.SkillType_SMG_PROFICIENCY);
                        break;
                    case 6:
                        LoadSkillUpgradePage(ProgressionSystem.SkillType_MAGNUM_PROFICIENCY);
                        break;
                }
                break;
            case "UtilityPage":
                switch (responseID) {
                    case 1:
                        LoadSkillUpgradePage(ProgressionSystem.SkillType_SEARCH);
                        break;
                    case 2:
                        LoadSkillUpgradePage(ProgressionSystem.SkillType_HIDE);
                        break;
                    case 3:
                        LoadSkillUpgradePage(ProgressionSystem.SkillType_MOVE_SILENTLY);
                        break;
                    case 4:
                        LoadSkillUpgradePage(ProgressionSystem.SkillType_FIRST_AID);
                        break;
                    case 5:
                        LoadSkillUpgradePage(ProgressionSystem.SkillType_LOCKPICKING);
                        break;
                    case 6:
                        LoadSkillUpgradePage(ProgressionSystem.SkillType_MIXING);
                        break;
                    case 7:
                        LoadSkillUpgradePage(ProgressionSystem.SkillType_ITEM_REPAIR);
                        break;
                }
                break;
            case "AbilitiesPage":
                switch (responseID) {
                    case 1:
                        LoadSkillUpgradePage(ProgressionSystem.SkillType_POWER_ATTACK);
                        break;
                    case 2:
                        LoadSkillUpgradePage(ProgressionSystem.SkillType_AMBIDEXTERITY);
                        break;
                    case 3:
                        LoadSkillUpgradePage(ProgressionSystem.SkillType_TWO_WEAPON_FIGHTING);
                        break;
                }
                break;
            case "UpgradePage":
                switch (responseID) {
                    // Upgrade
                    case 1:
                        HandleUpgrade(responseID);
                        break;
                    // Back
                    case 2:
                        break;
                    // Return to Category List
                    case 3:
                        ChangePage("MainPage");
                        break;
                }
                break;
        }
    }

    @Override
    public void EndDialog() {
        NWScript.deleteLocalInt(GetPC(), "TEMP_MENU_SKILL_ID");
        NWScript.deleteLocalInt(GetPC(), "TEMP_MENU_CONFIRM_PURCHASE");
    }

    private void HandleUpgrade(int responseID)
    {
        NWObject oPC = GetPC();
        int skillID = NWScript.getLocalInt(oPC, "TEMP_MENU_SKILL_ID");
        boolean isConfirmation = NWScript.getLocalInt(oPC, "TEMP_MENU_CONFIRM_PURCHASE") == 1;

        if(isConfirmation)
        {
            ProgressionSystem.PurchaseSkillUpgrade(oPC, skillID);
            SetPageHeader("UpgradePage", BuildUpgradeHeader());
            SetResponseText("UpgradePage", responseID, "Upgrade");
            NWScript.deleteLocalInt(oPC, "TEMP_MENU_CONFIRM_PURCHASE");
        }
        else
        {
            NWScript.setLocalInt(oPC, "TEMP_MENU_CONFIRM_PURCHASE", 1);
            SetResponseText("UpgradePage", responseID, "CONFIRM PURCHASE");
        }

    }

    private void LoadSkillUpgradePage(int skillID)
    {
        NWScript.setLocalInt(GetPC(), "TEMP_MENU_SKILL_ID", skillID);
        SetPageHeader("UpgradePage", BuildUpgradeHeader());
        ChangePage("UpgradePage");
    }

    private String BuildUpgradeHeader()
    {
        int skillID = NWScript.getLocalInt(GetPC(), "TEMP_MENU_SKILL_ID");
        PlayerGO pcGO = new PlayerGO(GetPC());
        PlayerRepository pcRepo = new PlayerRepository();
        PlayerProgressionSkillsRepository pcSkillRepo = new PlayerProgressionSkillsRepository();
        ProgressionSkillRepository skillRepo = new ProgressionSkillRepository();

        PlayerEntity pcEntity = pcRepo.getByUUID(pcGO.getUUID());
        PlayerProgressionSkillEntity pcSkill = pcSkillRepo.GetByUUIDAndSkillID(pcGO.getUUID(), skillID);
        ProgressionSkillEntity skill = skillRepo.getByID(skillID);

        String upgradeName = skill.getName();
        String description = skill.getDescription();
        int upgradeLevel = pcSkill == null ? 0 : pcSkill.getUpgradeLevel();
        int upgradeCap = pcSkill == null || !pcSkill.isSoftCapUnlocked() ? skill.getSoftCap() : skill.getMaxUpgrades();
        int availableSP = pcEntity.getUnallocatedSP();
        int nextUpgradeCost = skill.getInitialPrice() + (pcSkill == null ? 0 : pcSkill.getUpgradeLevel());

        String upgradeCapText = ColorToken.Yellow() + upgradeCap + ColorToken.End();
        if(pcSkill != null && pcSkill.isSoftCapUnlocked())
        {
            upgradeCapText = ColorToken.White() + upgradeCap + ColorToken.End();
        }

        String header = ColorToken.Green() + "Upgrade Name: " + ColorToken.End() + upgradeName + "\n";
        header += ColorToken.Green() + "Upgrade Level: " + ColorToken.End() + upgradeLevel + " / " + upgradeCapText + "\n\n";
        header += ColorToken.Green() + "Available SP: " + ColorToken.End() + availableSP + "\n";
        header += ColorToken.Green() + "Next Upgrade: " + ColorToken.End() + nextUpgradeCost + "\n\n";
        header += ColorToken.Green() + "Description: " + ColorToken.End() + description;


        return header;
    }
}
