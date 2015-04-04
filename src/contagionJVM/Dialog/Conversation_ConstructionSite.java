package contagionJVM.Dialog;

import contagionJVM.Entities.ConstructionSiteEntity;
import contagionJVM.Entities.PCTerritoryFlagEntity;
import contagionJVM.Entities.StructureCategoryEntity;
import contagionJVM.Entities.StructureBlueprintEntity;
import contagionJVM.Helper.ColorToken;
import contagionJVM.Models.ConstructionSiteConversationModel;
import contagionJVM.Repository.StructureRepository;
import contagionJVM.System.StructureSystem;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.Scheduler;
import org.nwnx.nwnx2.jvm.constants.ObjectType;

import java.util.List;

@SuppressWarnings("unused")
public class Conversation_ConstructionSite extends DialogBase implements IDialogHandler {
    @Override
    public PlayerDialog SetUp(NWObject oPC) {
        PlayerDialog dialog = new PlayerDialog();
        DialogPage mainPage = new DialogPage();
        DialogPage blueprintCategoryPage = new DialogPage(
                "Please select a blueprint category."
        );

        DialogPage blueprintDetailsPage = new DialogPage(
                "<SET LATER>",
                "Select Blueprint",
                "Preview",
                "Back"
        );

        DialogPage razePage = new DialogPage(
                ColorToken.Red() + "WARNING: " + ColorToken.End() + "Razing this construction site will destroy it permanently. Materials used will NOT be returned to you.\n\n" +
                        "Are you sure you want to raze this construction site?",
                ColorToken.Red() + "Confirm Raze" + ColorToken.End(),
                "Back"
        );

        DialogPage quickBuildPage = new DialogPage(
                "Quick building this structure will complete it instantly. Please use this sparingly.",
                "Confirm Quick Build",
                "Back"
        );

        DialogPage blueprintListPage = new DialogPage(
                "Please select a blueprint."
        );

        dialog.addPage("MainPage", mainPage);
        dialog.addPage("BlueprintCategoryPage", blueprintCategoryPage);
        dialog.addPage("BlueprintListPage", blueprintListPage);
        dialog.addPage("BlueprintDetailsPage", blueprintDetailsPage);
        dialog.addPage("QuickBuildPage", quickBuildPage);
        dialog.addPage("RazePage", razePage);
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
                HandleMainPageResponse(responseID);
                break;
            }
            case "BlueprintCategoryPage":
            {
                HandleCategoryResponse(responseID);
                break;
            }
            case "BlueprintListPage":
            {
                HandleBlueprintListResponse(responseID);
                break;
            }
            case "BlueprintDetailsPage":
            {
                HandleBlueprintDetailsResponse(responseID);
                break;
            }
            case "RazePage":
            {
                HandleRazeResponse(responseID);
                break;
            }
            case "QuickBuildPage":
            {
                HandleQuickBuildResponse(responseID);
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
        NWObject existingFlag = StructureSystem.GetNearestTerritoryFlag(NWScript.getLocation(GetDialogTarget()));
        StructureRepository repo = new StructureRepository();
        ConstructionSiteConversationModel model = new ConstructionSiteConversationModel();

        float distance = NWScript.getDistanceBetween(existingFlag, GetDialogTarget());
        if(existingFlag.equals(NWObject.INVALID))
        {
            model.setIsTerritoryFlag(true);
        }
        else
        {
            int flagID = StructureSystem.GetTerritoryFlagID(existingFlag);
            PCTerritoryFlagEntity entity = repo.GetPCTerritoryFlagByID(flagID);
            if(distance <= entity.getBlueprint().getMaxBuildDistance())
            {
                model.setIsTerritoryFlag(false);
            }
            else
            {
                model.setIsTerritoryFlag(true);
            }
        }

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

        if(model.getConstructionSiteID() <= 0)
        {
            header = "Please select an option.";
            page.addResponse("Select Blueprint", true);
            page.addResponse(ColorToken.Red() + "Raze" + ColorToken.End(), true);

        }
        else
        {
            ConstructionSiteEntity entity = repo.GetConstructionSiteByID(model.getConstructionSiteID());

            header = ColorToken.Green() + "Blueprint: " + ColorToken.End() + entity.getBlueprint().getName() + "\n\n";
            header += ColorToken.Green() + "Resources Required: " + ColorToken.End() + "\n\n";

            header += entity.getWoodRequired() > 0 ? entity.getWoodRequired() + "x Wood" : "";
            header += entity.getNailsRequired() > 0 ? entity.getNailsRequired() + "x Nails" : "";
            header += entity.getMetalRequired() > 0 ? entity.getMetalRequired() + "x Metal" : "";
            header += entity.getClothRequired() > 0 ? entity.getClothRequired() + "x Cloth" : "";
            header += entity.getLeatherRequired() > 0 ? entity.getLeatherRequired() + "x Leather" : "";

            page.addResponse("Quick Build", NWScript.getIsDM(GetPC()));
            page.addResponse("Preview", true);
            page.addResponse(ColorToken.Red() + "Raze" + ColorToken.End(), true);
        }

        SetPageHeader("MainPage", header);
    }

    private void HandleMainPageResponse(int responseID)
    {
        ConstructionSiteConversationModel model = GetModel();

        if(model.getConstructionSiteID() <= 0)
        {
            switch (responseID)
            {
                case 1: // Select Blueprint
                    LoadCategoryPageResponses();
                    ChangePage("BlueprintCategoryPage");
                    break;
                case 2: // Raze
                    ChangePage("RazePage");
                    break;
            }
        }
        else
        {
            switch (responseID)
            {
                case 1: // Quick Build
                    ChangePage("QuickBuildPage");
                    break;
                case 2: // Preview
                    DoConstructionSitePreview();
                    break;
                case 3: // Raze
                    ChangePage("RazePage");
                    break;
            }
        }

    }

    private void BuildBlueprintDetailsHeader()
    {
        ConstructionSiteConversationModel model = GetModel();
        StructureRepository repo = new StructureRepository();
        StructureBlueprintEntity entity = repo.GetStructureBlueprintByID(model.getBlueprintID());
        String header = ColorToken.Green() + "Blueprint Name: " + ColorToken.End() + entity.getName() + "\n\n";
        if(!entity.getDescription().equals(""))
        {
            header += ColorToken.Green() + "Description: " + ColorToken.End() + entity.getDescription() + "\n\n";
        }
        header += ColorToken.Green() + "Resources Required: " + ColorToken.End() + "\n\n";
        header += entity.getWoodRequired() > 0 ? entity.getWoodRequired() + "x Wood" : "";
        header += entity.getNailsRequired() > 0 ? entity.getNailsRequired() + "x Nails" : "";
        header += entity.getMetalRequired() > 0 ? entity.getMetalRequired() + "x Metal" : "";
        header += entity.getClothRequired() > 0 ? entity.getClothRequired() + "x Cloth" : "";
        header += entity.getLeatherRequired() > 0 ? entity.getLeatherRequired() + "x Leather" : "";

        SetPageHeader("BlueprintDetailsPage", header);

    }

    private void LoadCategoryPageResponses()
    {
        ConstructionSiteConversationModel model = GetModel();
        StructureRepository repo = new StructureRepository();
        DialogPage page = GetPageByName("BlueprintCategoryPage");
        page.getResponses().clear();

        List<StructureCategoryEntity> categories = repo.GetStructureCategoriesByType(model.isTerritoryFlag());

        for(StructureCategoryEntity category : categories)
        {
            page.addResponse(category.getName(), category.isActive(), category.getStructureCategoryID());
        }

        page.addResponse("Back", true);
    }

    private void LoadBlueprintListPageResponses()
    {
        ConstructionSiteConversationModel model = GetModel();
        StructureRepository repo = new StructureRepository();
        DialogPage page = GetPageByName("BlueprintListPage");
        page.getResponses().clear();

        List<StructureBlueprintEntity> entities = repo.GetStructuresByCategoryID(model.getCategoryID());

        for(StructureBlueprintEntity entity : entities)
        {
            page.addResponse(entity.getName(), entity.isActive(), entity.getStructureBlueprintID());
        }

        page.addResponse("Back", true);
    }

    private void HandleCategoryResponse(int responseID)
    {
        DialogResponse response = GetResponseByID("BlueprintCategoryPage", responseID);
        if(response.getCustomData() == null)
        {
            ChangePage("MainPage");
            return;
        }

        ConstructionSiteConversationModel model = GetModel();
        model.setCategoryID((int)response.getCustomData());
        LoadBlueprintListPageResponses();
        ChangePage("BlueprintListPage");
    }

    private void HandleBlueprintListResponse(int responseID)
    {
        DialogResponse response = GetResponseByID("BlueprintListPage", responseID);
        if(response.getCustomData() == null)
        {
            ChangePage("BlueprintCategoryPage");
            return;
        }

        ConstructionSiteConversationModel model = GetModel();
        model.setBlueprintID((int) response.getCustomData());
        BuildBlueprintDetailsHeader();
        ChangePage("BlueprintDetailsPage");
    }

    private void HandleBlueprintDetailsResponse(int responseID)
    {
        switch (responseID)
        {
            case 1: // Select Blueprint
                break;
            case 2: // Preview
                DoBlueprintPreview(GetModel().getBlueprintID());
                break;
            case 3: // Back
                ChangePage("BlueprintListPage");
                break;
        }
    }

    private void HandleRazeResponse(int responseID)
    {
        switch(responseID)
        {
            case 1: // Confirm Raze
                DoRaze();
                break;
            case 2: // Back
                ChangePage("MainPage");
                break;
        }
    }

    private void HandleQuickBuildResponse(int responseID)
    {
        switch(responseID)
        {
            case 1:
                DoQuickBuild();
                break;
            case 2: // Back
                ChangePage("MainPage");
                break;
        }
    }

    private void DoBlueprintPreview(int blueprintID)
    {
        final ConstructionSiteConversationModel model = GetModel();
        if(model.isPreviewing()) return;
        model.setIsPreviewing(true);
        StructureRepository repo = new StructureRepository();
        StructureBlueprintEntity entity = repo.GetStructureBlueprintByID(blueprintID);
        final NWObject preview = NWScript.createObject(ObjectType.PLACEABLE, entity.getResref(), NWScript.getLocation(GetDialogTarget()), false, "");
        NWScript.setUseableFlag(preview, false);
        NWScript.setPlotFlag(preview, true);
        NWScript.destroyObject(preview, 6.0f);

        Scheduler.delay(preview, 6000, new Runnable() {
            @Override
            public void run() {
                model.setIsPreviewing(false);
            }
        });
        Scheduler.flushQueues();

    }

    private void DoConstructionSitePreview()
    {
        final ConstructionSiteConversationModel model = GetModel();
        if(model.isPreviewing()) return;
        StructureRepository repo = new StructureRepository();
        ConstructionSiteEntity entity = repo.GetConstructionSiteByID(model.getConstructionSiteID());
        StructureBlueprintEntity blueprint = entity.getBlueprint();
        final NWObject preview = NWScript.createObject(ObjectType.PLACEABLE, blueprint.getResref(), NWScript.getLocation(GetDialogTarget()), false, "");
        NWScript.setUseableFlag(preview, false);
        NWScript.setPlotFlag(preview, true);
        NWScript.destroyObject(preview, 6.0f);

        Scheduler.delay(preview, 6000, new Runnable() {
            @Override
            public void run() {
                model.setIsPreviewing(false);
            }
        });
        Scheduler.flushQueues();
    }

    private void DoRaze()
    {
        ConstructionSiteConversationModel model = GetModel();

        if(model.getConstructionSiteID() > 0)
        {
            StructureRepository repo = new StructureRepository();
            ConstructionSiteEntity entity = repo.GetConstructionSiteByID(model.getConstructionSiteID());
            repo.Delete(entity);
        }
        NWScript.destroyObject(GetDialogTarget(), 0.0f);

        EndConversation();
    }

    private void DoQuickBuild()
    {

    }

}
