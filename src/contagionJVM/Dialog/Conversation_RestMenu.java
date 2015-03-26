package contagionJVM.Dialog;

import contagionJVM.Entities.PlayerEntity;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.Helper.ColorToken;
import contagionJVM.Repository.PlayerRepository;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.Scheduler;

@SuppressWarnings("UnusedDeclaration")
public class Conversation_RestMenu extends DialogBase implements IDialogHandler {
    @Override
    public PlayerDialog Initialize(NWObject oPC) {

        PlayerDialog dialog = new PlayerDialog();
        DialogPage mainPage = new DialogPage(
                BuildMainPageHeader(oPC),
                "Allocate Skill Points",
                "View Key Items",
                "Modify Clothes",
                "Character Management");

        DialogPage modifyClothesPage = new DialogPage(
                "You may save, load, or modify outfits using this menu.",
                "Save/Load Outfit",
                "Change armor/clothing appearance",
                "Change robe appearance"
        );

        DialogPage viewKeyItemsPage = new DialogPage(
                "Select a key item category.",
                "Maps",
                "Quest Items",
                "Documents"
        );

        DialogPage characterManagementPage = new DialogPage(
                "Character Management & Information Page",
                "Disable PVP Protection",
                "Manage CD Keys",
                "Change Portrait",
                "Change Head"
        );

        dialog.addPage("MainPage", mainPage);
        dialog.addPage("ModifyClothesPage", modifyClothesPage);
        dialog.addPage("ViewKeyItemsPage", viewKeyItemsPage);
        dialog.addPage("CharacterManagementPage", characterManagementPage);

        return dialog;
    }

    @Override
    public void DoAction(final NWObject oPC, String pageName, int responseID) {
        switch (pageName) {
            case "MainPage":
                switch (responseID) {
                    // Allocate Skill Points
                    case 1:
                        SwitchConversation("AllocateSkillPoints");
                        break;
                    // View Key Items
                    case 2:
                        ChangePage("ViewKeyItemsPage");
                        break;
                    // Modify Clothes
                    case 3:
                        Scheduler.assign(oPC, new Runnable() {
                            @Override
                            public void run() {
                                NWScript.actionStartConversation(oPC, "x0_skill_ctrap", false, false);
                            }
                        });
                        break;
                    // Character Management
                    case 4:
                        ChangePage("CharacterManagementPage");
                        break;
                }
                break;
            case "ViewKeyItemsPage":
                switch (responseID) {
                    case 1:
                        break;
                    case 2:
                        break;
                }
                break;
            case "CharacterManagementPage":
                switch (responseID) {
                    // Disable PVP Protection
                    case 1:
                        break;
                    // Manage CD Keys
                    case 2:
                        break;
                    // Change Portrait
                    case 3:
                        break;
                    // Change Head
                    case 4:
                        break;
                }
                break;
        }

        Scheduler.flushQueues();
    }

    @Override
    public void EndDialog()
    {
    }


    private String BuildMainPageHeader(NWObject oPC)
    {
        PlayerGO pcGO = new PlayerGO(oPC);
        PlayerRepository repo = new PlayerRepository();
        PlayerEntity entity = repo.getByUUID(pcGO.getUUID());

        String header = ColorToken.Green() + "Name: " + ColorToken.End() + NWScript.getName(oPC, false) + "\n\n";
        header += ColorToken.Green() + "Level: " + ColorToken.End() + entity.getLevel() + "\n";
        header += ColorToken.Green() + "EXP: " + ColorToken.End() + entity.getExperience() + "\n";
        header += ColorToken.Green() + "Skill Points: " + ColorToken.End() + entity.getUnallocatedSP() + "\n";
        header += ColorToken.Green() + "Hunger: " + ColorToken.End() + entity.getCurrentHunger() + "%\n";
        header += ColorToken.Green() + "Thirst: " + ColorToken.End() + entity.getCurrentThirst() + "%\n";
        header += ColorToken.Green() + "Infection: " + ColorToken.End() + entity.getCurrentInfection() + "%\n";

        return header;
    }

}
