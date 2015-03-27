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
                    // Hit Points
                    case 1:
                        LoadSkillUpgradePage(1);
                        break;
                    // Inventory
                    case 2:
                        LoadSkillUpgradePage(2);
                        break;
                }
                break;
            case "ProficienciesPage":
                switch (responseID) {
                    // Armor Proficiency
                    case 1:
                        LoadSkillUpgradePage(3);
                        break;
                    // Handgun Proficiency
                    case 2:
                        LoadSkillUpgradePage(4);
                        break;
                    // Shotgun Proficiency
                    case 3:
                        LoadSkillUpgradePage(5);
                        break;
                    // Rifle Proficiency
                    case 4:
                        LoadSkillUpgradePage(6);
                        break;
                    // SMG Proficiency
                    case 5:
                        LoadSkillUpgradePage(7);
                        break;
                    // Magnum Proficiency
                    case 6:
                        LoadSkillUpgradePage(8);
                        break;
                }
                break;
            case "UtilityPage":
                switch (responseID) {
                    // Search
                    case 1:
                        LoadSkillUpgradePage(10);
                        break;
                    // Hide
                    case 2:
                        LoadSkillUpgradePage(11);
                        break;
                    // Move Silently
                    case 3:
                        LoadSkillUpgradePage(12);
                        break;
                    // First Aid
                    case 4:
                        LoadSkillUpgradePage(13);
                        break;
                    // Lockpicking
                    case 5:
                        LoadSkillUpgradePage(14);
                        break;
                    // Mixing
                    case 6:
                        LoadSkillUpgradePage(15);
                        break;
                    // Item Repair
                    case 7:
                        LoadSkillUpgradePage(20);
                        break;
                }
                break;
            case "AbilitiesPage":
                switch (responseID) {
                    // Power Attack
                    case 1:
                        LoadSkillUpgradePage(17);
                        break;
                    // Ambidexterity
                    case 2:
                        LoadSkillUpgradePage(18);
                        break;
                    // Two-Weapon Fighting
                    case 3:
                        LoadSkillUpgradePage(19);
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
