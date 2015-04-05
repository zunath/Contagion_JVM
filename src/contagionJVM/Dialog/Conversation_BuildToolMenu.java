package contagionJVM.Dialog;

import contagionJVM.Entities.PCTerritoryFlagStructureEntity;
import contagionJVM.Helper.ColorToken;
import contagionJVM.Models.BuildToolMenuModel;
import contagionJVM.Repository.StructureRepository;
import contagionJVM.System.StructureSystem;
import org.nwnx.nwnx2.jvm.NWLocation;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.Scheduler;
import org.nwnx.nwnx2.jvm.constants.ObjectType;

@SuppressWarnings("unused")
public class Conversation_BuildToolMenu extends DialogBase implements IDialogHandler {

    @Override
    public PlayerDialog SetUp(NWObject oPC) {
        PlayerDialog dialog = new PlayerDialog();
        DialogPage mainPage = new DialogPage(
                "Please select an option.\n\nStructures in this list are ordered by distance from the location targeted. "
        );

        DialogPage manipulateStructurePage = new DialogPage(
                "What would you like to do with this structure?",
                "Rotate",
                "Move",
                ColorToken.Red() + "Raze" + ColorToken.End(),
                "Back"
        );

        DialogPage rotateStructurePage = new DialogPage(
                "Please select a rotation.",
                "Set Facing: East",
                "Set Facing: North",
                "Set Facing: South",
                "Set Facing: West",
                "Rotate 20\u00b0",
                "Rotate 30\u00b0",
                "Rotate 45\u00b0",
                "Rotate 60\u00b0",
                "Rotate 75\u00b0",
                "Rotate 90\u00b0",
                "Rotate 180\u00b0",
                "Back"
        );

        DialogPage razeStructurePage = new DialogPage(
                ColorToken.Red() + "WARNING: " + ColorToken.End() +
                        "You are about to destroy a structure. All items inside of this structure will be permanently destroyed.\n\n" +
                        "Are you sure you want to raze this structure?\n",
                ColorToken.Red() + "Confirm Raze" + ColorToken.End(),
                "Back"
        );

        dialog.addPage("MainPage", mainPage);
        dialog.addPage("ManipulateStructurePage", manipulateStructurePage);
        dialog.addPage("RotateStructurePage", rotateStructurePage);
        dialog.addPage("RazeStructurePage", razeStructurePage);
        return dialog;
    }

    @Override
    public void Initialize() {
        NWObject oPC = GetPC();

        BuildToolMenuModel model = new BuildToolMenuModel();
        model.setTargetLocation(NWScript.getLocalLocation(oPC, "BUILD_TOOL_LOCATION_TARGET"));
        NWScript.deleteLocalLocation(oPC, "BUILD_TOOL_LOCATION_TARGET");
        SetDialogCustomData(model);

        BuildMainMenuResponses(null);
    }

    @Override
    public void DoAction(NWObject oPC, String pageName, int responseID) {
        switch(pageName)
        {
            case "MainPage":
                HandleMainMenuResponse(responseID);
                break;
            case "ManipulateStructurePage":
                switch(responseID)
                {
                    case 1: // Rotate
                        ChangePage("RotateStructurePage");
                        break;
                    case 2: // Move
                        HandleMoveStructure();
                        break;
                    case 3: // Raze
                        ChangePage("RazeStructurePage");
                        break;
                    case 4: // Back
                        BuildMainMenuResponses(null);
                        ChangePage("MainPage");
                        break;
                }
                break;
            case "RotateStructurePage":
                switch(responseID)
                {
                    case 1: // East
                        HandleRotateStructure(0.0f, true);
                        break;
                    case 2: // North
                        HandleRotateStructure(90.0f, true);
                        break;
                    case 3: // South
                        HandleRotateStructure(180.0f, true);
                        break;
                    case 4: // West
                        HandleRotateStructure(270.0f, true);
                        break;
                    case 5: // Rotate 20
                        HandleRotateStructure(20.0f, false);
                        break;
                    case 6: // Rotate 30
                        HandleRotateStructure(30.0f, false);
                        break;
                    case 7: // Rotate 45
                        HandleRotateStructure(45.0f, false);
                        break;
                    case 8: // Rotate 60
                        HandleRotateStructure(60.0f, false);
                        break;
                    case 9: // Rotate 75
                        HandleRotateStructure(75.0f, false);
                        break;
                    case 10: // Rotate 90
                        HandleRotateStructure(90.0f, false);
                        break;
                    case 11: // Rotate 180
                        HandleRotateStructure(180.0f, false);
                        break;
                    case 12: // Back
                        ChangePage("ManipulateStructurePage");
                        break;
                }
                break;
            case "RazeStructurePage":
                switch(responseID)
                {
                    case 1: // Raze Structure
                        HandleRazeStructure();
                        break;
                    case 2: // Back
                        ChangePage("ManipulateStructurePage");
                        break;
                }
                break;
        }
    }

    @Override
    public void EndDialog() {

    }

    private BuildToolMenuModel GetModel()
    {
        return (BuildToolMenuModel)GetDialogCustomData();
    }

    private void BuildMainMenuResponses(NWObject excludeObject)
    {
        DialogPage page = GetPageByName("MainPage");
        page.getResponses().clear();
        BuildToolMenuModel model = GetModel();
        model.getNearbyStructures().clear();
        model.setActiveStructure(null);

        DialogResponse constructionSiteResponse = new DialogResponse(ColorToken.Green() + "Create Construction Site" + ColorToken.End());
        if(StructureSystem.CanPCBuildInLocation(GetPC(), model.getTargetLocation()) == 0) // 0 = Can't build in this location
        {
            constructionSiteResponse.setActive(false);
        }

        page.getResponses().add(constructionSiteResponse);

        for(int current = 1; current <= 30; current++)
        {
            NWObject structure = NWScript.getNearestObjectToLocation(ObjectType.PLACEABLE, model.getTargetLocation(), current);
            NWLocation structureLocation = NWScript.getLocation(structure);
            float distance = NWScript.getDistanceBetweenLocations(model.getTargetLocation(), structureLocation);

            if(distance > 15.0f) break;

            if(StructureSystem.GetPlaceableStructureID(structure) > 0)
            {
                model.getNearbyStructures().add(structure);
            }
        }

        for(NWObject structure : model.getNearbyStructures())
        {
            if(excludeObject == null || !excludeObject.equals(structure))
            {
                DialogResponse response = new DialogResponse(NWScript.getName(structure, false), structure);
                page.getResponses().add(response);
            }
        }
    }

    private void HandleMainMenuResponse(int responseID)
    {
        BuildToolMenuModel model = GetModel();
        DialogResponse response = GetResponseByID("MainPage", responseID);
        NWObject structure = (NWObject)response.getCustomData();

        if(responseID == 1)
        {
            StructureSystem.CreateConstructionSite(GetPC(), model.getTargetLocation());
            EndConversation();
        }
        else if(structure != null)
        {
            model.setActiveStructure(structure);
            ChangePage("ManipulateStructurePage");
        }
    }

    private void HandleMoveStructure()
    {
        BuildToolMenuModel model = GetModel();
        NWScript.floatingTextStringOnCreature("Please use your build tool to select a new location for this structure.", GetPC(), false);
        StructureSystem.SetIsPCMovingStructure(GetPC(), model.getActiveStructure(), true);
        EndConversation();
    }

    private void HandleRotateStructure(float rotation, boolean isSet)
    {
        BuildToolMenuModel model = GetModel();
        StructureRepository repo = new StructureRepository();
        int structureID = StructureSystem.GetPlaceableStructureID(model.getActiveStructure());
        final PCTerritoryFlagStructureEntity entity = repo.GetPCStructureByID(structureID);

        if(isSet)
        {
            entity.setLocationOrientation(rotation);
        }
        else
        {
            entity.setLocationOrientation(entity.getLocationOrientation() + rotation);
        }

        repo.Save(entity);

        Scheduler.assign(model.getActiveStructure(), new Runnable() {
            @Override
            public void run() {
                NWScript.setFacing((float)entity.getLocationOrientation());
            }
        });

        Scheduler.flushQueues();
    }

    private void HandleRazeStructure()
    {
        BuildToolMenuModel model = GetModel();
        int structureID = StructureSystem.GetPlaceableStructureID(model.getActiveStructure());

        if(model.isConfirmingRaze())
        {
            model.setIsConfirmingRaze(false);
            SetResponseText("RazeStructurePage", 1, ColorToken.Red() + "Raze Structure" + ColorToken.End());
            StructureRepository repo = new StructureRepository();
            PCTerritoryFlagStructureEntity entity = repo.GetPCStructureByID(structureID);
            repo.Delete(entity);
            NWScript.destroyObject(model.getActiveStructure(), 0.0f);

            BuildMainMenuResponses(model.getActiveStructure());
            ChangePage("MainPage");
        }
        else
        {
            model.setIsConfirmingRaze(true);
            SetResponseText("RazeStructurePage", 1, ColorToken.Red() + "CONFIRM RAZE STRUCTURE" + ColorToken.End());
        }
    }

}
