package contagionJVM.Dialog;

import contagionJVM.Entities.CraftBlueprintCategoryEntity;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.Repository.CraftRepository;
import org.nwnx.nwnx2.jvm.NWObject;

import java.util.List;

@SuppressWarnings("unused")
public class Conversation_Crafting extends DialogBase implements IDialogHandler {
    @Override
    public PlayerDialog SetUp(NWObject oPC) {
        PlayerDialog dialog = new PlayerDialog();
        DialogPage mainPage = new DialogPage(
                "Please select a blueprint. Only blueprints you've added to your collection will be displayed here."
        );
        DialogPage blueprintPage = new DialogPage();

        dialog.addPage("MainPage", mainPage);
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
        PlayerGO pcGO = new PlayerGO(GetPC());
        CraftRepository repo = new CraftRepository();
        List<CraftBlueprintCategoryEntity> categories = repo.GetCategoriesAvailableToPC(pcGO.getUUID());
        DialogPage page = GetPageByName("MainPage");
        page.getResponses().clear();

        for(CraftBlueprintCategoryEntity category : categories)
        {
            page.addResponse(category.getName(), category.isActive(), category.getCraftBlueprintCategoryID());
        }
    }

    private void LoadBlueprintPage(int blueprintID)
    {

    }

    private void HandleCategoryResponse(int responseID)
    {

    }

    private void HandleBlueprintResponse(int responseID)
    {

    }

}
