package contagionJVM.Dialog;

import contagionJVM.Entities.KeyItemEntity;
import contagionJVM.Entities.PCKeyItemEntity;
import contagionJVM.Entities.PlayerEntity;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.Helper.ColorToken;
import contagionJVM.Repository.KeyItemRepository;
import contagionJVM.Repository.PlayerRepository;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.Scheduler;

import java.util.List;


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

        DialogPage keyItemCategoriesPage = new DialogPage(
                "Select a key item category.",
                "Maps",
                "Quest Items",
                "Documents",
                "Back"
        );

        DialogPage keyItemListPage = new DialogPage(
                "Select a key item."
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
        dialog.addPage("KeyItemCategoriesPage", keyItemCategoriesPage);
        dialog.addPage("KeyItemsListPage", keyItemListPage);
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
                    // Key Item Categories Page
                    case 2:
                        ChangePage("KeyItemCategoriesPage");
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
            case "KeyItemCategoriesPage":
                switch (responseID) {
                    case 1: // "Maps"
                    case 2: // "Quest Items"
                    case 3: // "Documents"
                        NWScript.setLocalInt(GetPC(), "TEMP_MENU_KEY_ITEM_CATEGORY_ID", responseID);
                        LoadKeyItemsOptions(responseID);
                        break;
                    case 4: // "Back"
                        ChangePage("MainPage");
                        break;
                }
                break;
            case "KeyItemsListPage":
                HandleKeyItemSelectionr(responseID);
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
        ClearTempVariables();
    }

    private void ClearTempVariables()
    {
        NWScript.deleteLocalString(GetPC(), "TEMP_MENU_KEY_ITEM_CATEGORY_ID");
        SetPageHeader("KeyItemsListPage", "Select a key item.");
    }

    private void LoadKeyItemsOptions(int categoryID)
    {
        PlayerGO pcGO = new PlayerGO(GetPC());
        PlayerDialog dialog = DialogManager.loadPlayerDialog(pcGO.getUUID());
        DialogPage page = dialog.getPageByName("KeyItemsListPage");
        page.getResponses().clear();
        KeyItemRepository repo = new KeyItemRepository();
        List<PCKeyItemEntity> items = repo.GetPlayerKeyItemsByCategory(pcGO.getUUID(), categoryID);

        for(PCKeyItemEntity item : items)
        {
            DialogResponse response = new DialogResponse(item.getKeyItem().getName());
            response.setCustomData(item.getKeyItemID());
            page.getResponses().add(response);
        }

        page.getResponses().add(new DialogResponse("Back"));
        ChangePage("KeyItemsListPage");
    }

    private void HandleKeyItemSelectionr(int responseID)
    {
        DialogResponse response = GetResponseByID(GetCurrentPage(), responseID);
        if(response.getCustomData() == null)
        {
            ClearTempVariables();
            ChangePage("KeyItemCategoriesPage");
        }
        else
        {
            SetPageHeader("KeyItemsListPage", BuildKeyItemHeader(responseID));
        }
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

    private String BuildKeyItemHeader(int responseID)
    {
        KeyItemRepository repo = new KeyItemRepository();
        DialogResponse response = GetResponseByID(GetCurrentPage(), responseID);
        int keyItemID = (int)response.getCustomData();
        KeyItemEntity entity = repo.GetKeyItemByID(keyItemID);

        String header = ColorToken.Green() + "Key Item: " + ColorToken.End() + entity.getName() + "\n\n";
        header += ColorToken.Green() + "Description: " + ColorToken.End() + entity.getDescription() + "\n";

        return header;
    }

}
