package contagionJVM.Dialog;

import contagionJVM.Entities.PlayerEntity;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.Helper.ColorToken;
import contagionJVM.Repository.PlayerRepository;
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
                BuildUpgradeHeader(),
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
                        break;
                    // Proficiencies
                    case 2:
                        break;
                    // Utility
                    case 3:
                        break;
                    // Abilities
                    case 4:
                        break;
                }
                break;
            case "StatsPage":
                switch (responseID) {
                    // Hit Points
                    case 1:
                        break;
                    // Inventory
                    case 2:
                        break;
                }
                break;
            case "ProficienciesPage":
                switch (responseID) {
                    // Armor Proficiency
                    case 1:
                        break;
                    // Handgun Proficiency
                    case 2:
                        break;
                    // Shotgun Proficiency
                    case 3:
                        break;
                    // Rifle Proficiency
                    case 4:
                        break;
                    // SMG Proficiency
                    case 5:
                        break;
                    // Magnum Proficiency
                    case 6:
                        break;
                }
                break;
            case "UtilityPage":
                switch (responseID) {
                    // Search
                    case 1:
                        break;
                    // Hide
                    case 2:
                        break;
                    // Move Silently
                    case 3:
                        break;
                    // First Aid
                    case 4:
                        break;
                    // Lockpicking
                    case 5:
                        break;
                    // Mixing
                    case 6:
                        break;
                    // Item Repair
                    case 7:
                        break;
                }
                break;
            case "AbilitiesPage":
                switch (responseID) {
                    // Power Attack
                    case 1:
                        break;
                    // Ambidexterity
                    case 2:
                        break;
                    // Two-Weapon Fighting
                    case 3:
                        break;
                }
                break;
            case "UpgradePage":
                switch (responseID) {
                    // Upgrade
                    case 1:
                        break;
                    // Back
                    case 2:
                        break;
                    // Return to Category List
                    case 3:
                        break;
                }
                break;
        }
    }

    @Override
    public void EndDialog() {

    }

    private String BuildUpgradeHeader()
    {
        PlayerGO pcGO = new PlayerGO(GetPC());
        PlayerRepository repo = new PlayerRepository();
        PlayerEntity entity = repo.getByUUID(pcGO.getUUID());
        String upgradeName = "";
        int upgradeLevel = 0;
        int upgradeCap = 0;
        int availableSP = 0;
        String nextUpgradeCost = "";
        String description = "";

        String header = ColorToken.Green() + "Upgrade Name: " + ColorToken.End() + upgradeName + "\n";
        header += ColorToken.Green() + "Upgrade Level: " + ColorToken.End() + upgradeLevel + " / " + upgradeCap + "\n\n";
        header += ColorToken.Green() + "Available SP: " + ColorToken.End() + availableSP + "\n";
        header += ColorToken.Green() + "Next Upgrade: " + ColorToken.End() + nextUpgradeCost + "\n\n";
        header += ColorToken.Green() + "Description: " + ColorToken.End() + description;


        return header;
    }
}
