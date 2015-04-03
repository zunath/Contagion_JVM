package contagionJVM.Dialog;

import contagionJVM.Entities.ConstructionSiteEntity;
import contagionJVM.Entities.StructureCategoryEntity;
import contagionJVM.Helper.ColorToken;
import contagionJVM.Models.ConstructionSiteConversationModel;
import contagionJVM.Repository.StructureRepository;
import contagionJVM.System.StructureSystem;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

import java.util.List;

@SuppressWarnings("unused")
public class Conversation_ConstructionSite extends DialogBase implements IDialogHandler {
    @Override
    public PlayerDialog SetUp(NWObject oPC) {
        PlayerDialog dialog = new PlayerDialog();
        DialogPage mainPage = new DialogPage();

        dialog.addPage("MainPage", mainPage);
        return dialog;
    }

    @Override
    public void Initialize() {

        InitializeConstructionSite();
        BuildMainPage();
    }

    @Override
    public void DoAction(NWObject oPC, String pageName, int responseID) {
        switch(pageName)
        {
            case "MainPage":
            {
                switch (responseID)
                {
                    case 1: // Quick Build
                        break;
                    case 2: // Preview
                        break;
                    case 3: // Raze
                        break;
                    default: // Category selection
                        HandleCategorySelection(responseID);
                        break;
                }
                break;
            }
        }
    }

    @Override
    public void EndDialog() {

    }

    private void InitializeConstructionSite()
    {
        NWObject site = GetDialogTarget();
        ConstructionSiteConversationModel model = new ConstructionSiteConversationModel();
        model.setConstructionSiteID(StructureSystem.GetConstructionSiteID(site));

        SetDialogCustomData(model);
    }

    private ConstructionSiteConversationModel GetModel()
    {
        return (ConstructionSiteConversationModel)GetDialogCustomData();
    }

    private void BuildMainPage()
    {
        DialogPage page = GetPageByName("MainPage");
        String header;
        ConstructionSiteConversationModel model = GetModel();
        StructureRepository repo = new StructureRepository();

        page.getResponses().clear();
        page.addResponse("Quick Build", NWScript.getIsDM(GetPC()));
        page.addResponse("Preview", true);
        page.addResponse(ColorToken.Red() + "Raze" + ColorToken.End(), true);

        if(model.getConstructionSiteID() <= 0)
        {
            header = "Please select a blueprint.";
            GetResponseByID("MainPage", 1).setActive(false);
            GetResponseByID("MainPage", 2).setActive(false);
            GetResponseByID("MainPage", 3).setActive(false);

            List<StructureCategoryEntity> categories = repo.GetAllStructureCategories();
            for(StructureCategoryEntity category : categories)
            {
                page.addResponse(category.getName(), category.isActive(), category.getStructureCategoryID());
            }
        }
        else
        {
            ConstructionSiteEntity entity = repo.GetConstructionSiteByID(model.getConstructionSiteID());

            header = ColorToken.Green() + "Blueprint: " + ColorToken.End() + entity.getStructure().getName() + "\n\n";
            header += ColorToken.Green() + "Resources Required: " + ColorToken.End() + "\n\n";

            header += entity.getWoodRequired() > 0 ? entity.getWoodRequired() : "";
            header += entity.getClothRequired() > 0 ? entity.getClothRequired() : "";
            header += entity.getNailsRequired() > 0 ? entity.getNailsRequired() : "";
            header += entity.getLeatherRequired() > 0 ? entity.getLeatherRequired() : "";

        }

        SetPageHeader("MainPage", header);
    }

    private void HandleCategorySelection(int responseID)
    {

    }

}
