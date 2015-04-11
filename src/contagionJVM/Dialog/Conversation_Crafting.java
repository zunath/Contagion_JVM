package contagionJVM.Dialog;

import contagionJVM.Entities.*;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.Repository.CraftRepository;
import contagionJVM.System.CraftSystem;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.Scheduler;

import java.util.List;

@SuppressWarnings("unused")
public class Conversation_Crafting extends DialogBase implements IDialogHandler {
    @Override
    public PlayerDialog SetUp(NWObject oPC) {
        PlayerDialog dialog = new PlayerDialog();
        DialogPage mainPage = new DialogPage(
                "Please select a blueprint. Only blueprints you've added to your collection will be displayed here.",
                "Back"
        );
        DialogPage blueprintPage = new DialogPage(
                "<SET LATER>",
                "Select Blueprint",
                "Back"
        );
        DialogPage blueprintListPage = new DialogPage(
                "Please select a blueprint. Only blueprints you've added to your collection will be displayed here.",
                "Back"
        );

        dialog.addPage("MainPage", mainPage);
        dialog.addPage("BlueprintListPage", blueprintListPage);
        dialog.addPage("BlueprintPage", blueprintPage);
        return dialog;
    }

    @Override
    public void Initialize() {
        LoadCategoryResponses();
    }

    @Override
    public void DoAction(NWObject oPC, String pageName, int responseID) {
        switch (pageName)
        {
            case "MainPage":
                HandleCategoryResponse(responseID);
                break;
            case "BlueprintListPage":
                HandleBlueprintListResponse(responseID);
                break;
            case "BlueprintPage":
                HandleBlueprintResponse(responseID);
                break;
        }
    }

    @Override
    public void EndDialog() {

    }

    private void LoadCategoryResponses()
    {
        int craftID = NWScript.getLocalInt(GetDialogTarget(), "CRAFT_ID");
        PlayerGO pcGO = new PlayerGO(GetPC());
        CraftRepository repo = new CraftRepository();
        List<CraftBlueprintCategoryEntity> categories = repo.GetCategoriesAvailableToPCByCraftID(pcGO.getUUID(), craftID);
        DialogPage page = GetPageByName("MainPage");
        page.getResponses().clear();

        for(CraftBlueprintCategoryEntity category : categories)
        {
            page.addResponse(category.getName(), category.isActive(), category.getCraftBlueprintCategoryID());
        }

        page.addResponse("Back", true);
    }

    private void LoadBlueprintListPage(int categoryID)
    {
        PlayerGO pcGO = new PlayerGO(GetPC());
        CraftRepository repo = new CraftRepository();
        List<PCBlueprintEntity> blueprints = repo.GetPCBlueprintsByCategoryID(pcGO.getUUID(), categoryID);
        DialogPage page = GetPageByName("BlueprintListPage");
        page.getResponses().clear();

        for(PCBlueprintEntity bp : blueprints)
        {
            page.addResponse(bp.getBlueprint().getItemName(), true, bp.getBlueprint().getCraftBlueprintID());
        }

        page.addResponse("Back", true);
    }

    private void LoadBlueprintPage(int blueprintID)
    {
        DialogPage page = GetPageByName("BlueprintPage");
        SetPageHeader("BlueprintPage", CraftSystem.BuildBlueprintHeader(GetPC(), blueprintID));
        DialogResponse response = GetResponseByID("BlueprintPage", 1);
        response.setCustomData(blueprintID);
    }

    private void HandleCategoryResponse(int responseID)
    {
        DialogResponse response = GetResponseByID("MainPage", responseID);
        if(response.getCustomData() == null)
        {
            final NWObject device = GetDialogTarget();
            Scheduler.assign(GetPC(), new Runnable() {
                @Override
                public void run() {
                    NWScript.actionInteractObject(device);
                }
            });
            return;
        }

        int categoryID = (int)response.getCustomData();
        LoadBlueprintListPage(categoryID);
        ChangePage("BlueprintListPage");
    }

    private void HandleBlueprintListResponse(int responseID)
    {
        DialogResponse response = GetResponseByID("BlueprintListPage", responseID);
        if(response.getCustomData() == null)
        {
            ChangePage("MainPage");
            return;
        }

        int blueprintID = (int)response.getCustomData();
        LoadBlueprintPage(blueprintID);
        ChangePage("BlueprintPage");
    }

    private void HandleBlueprintResponse(int responseID)
    {
        DialogResponse response = GetResponseByID("BlueprintPage", responseID);

        switch (responseID)
        {
            case 1: // Select Blueprint
                int blueprintID = (int)response.getCustomData();
                NWScript.sendMessageToPC(GetPC(), "Blueprint selected. Add necessary resources to the device and click the 'Craft Item' option.");

                final NWObject device = GetDialogTarget();
                NWScript.setLocalInt(device, "CRAFT_BLUEPRINT_ID", blueprintID);

                Scheduler.assign(GetPC(), new Runnable() {
                    @Override
                    public void run() {
                        NWScript.actionInteractObject(device);
                    }
                });

                EndConversation();
                break;
            case 2: // Back
                ChangePage("BlueprintListPage");
                break;
        }
    }

}
