package contagionJVM.System;

import contagionJVM.Entities.*;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.Helper.ColorToken;
import contagionJVM.Models.ConstructionSiteMenuModel;
import contagionJVM.Repository.PlayerRepository;
import contagionJVM.Repository.StructureRepository;
import org.nwnx.nwnx2.jvm.NWLocation;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.NWVector;
import org.nwnx.nwnx2.jvm.constants.ObjectType;

import java.util.ArrayList;
import java.util.List;

public class StructureSystem {

    // Variable names
    private static final String TerritoryFlagIDVariableName = "TERRITORY_FLAG_ID";
    private static final String StructureIDVariableName = "TERRITORY_STRUCTURE_SITE_ID";
    private static final String ConstructionSiteIDVariableName = "CONSTRUCTION_SITE_ID";

    private static final String IsMovingStructureLocationVariableName = "IS_MOVING_STRUCTURE_LOCATION";
    private static final String MovingStructureVariableName = "MOVING_STRUCTURE_OBJECT";

    // Resrefs
    private static final String ConstructionSiteResref = "const_site";
    private static final String TemporaryLocationCheckerObjectResref = "temp_loc_check";


    public static void OnModuleLoad()
    {
        StructureRepository repo = new StructureRepository();
        PlayerRepository playerRepo = new PlayerRepository();

        List<ConstructionSiteEntity> constructionSites = repo.GetAllConstructionSites();
        for(ConstructionSiteEntity entity : constructionSites)
        {
            NWObject oArea = NWScript.getObjectByTag(entity.getLocationAreaTag(), 0);
            NWVector position = NWScript.vector((float) entity.getLocationX(), (float) entity.getLocationY(), (float) entity.getLocationZ());
            NWLocation location = NWScript.location(oArea, position, (float) entity.getLocationOrientation());

            NWObject constructionSite = NWScript.createObject(ObjectType.PLACEABLE, ConstructionSiteResref, location, false, "");
            NWScript.setLocalInt(constructionSite, ConstructionSiteIDVariableName, entity.getConstructionSiteID());
        }

        List<PCTerritoryFlagEntity> territoryFlags = repo.GetAllTerritoryFlags();
        for(PCTerritoryFlagEntity flag : territoryFlags)
        {
            NWObject oArea = NWScript.getObjectByTag(flag.getLocationAreaTag(), 0);
            NWVector position = NWScript.vector((float) flag.getLocationX(), (float) flag.getLocationY(), (float) flag.getLocationZ());
            NWLocation location = NWScript.location(oArea, position, (float) flag.getLocationOrientation());
            PlayerEntity playerEntity = playerRepo.getByUUID(flag.getPlayerID());

            NWObject territoryFlag = NWScript.createObject(ObjectType.PLACEABLE, flag.getBlueprint().getResref(), location, false, "");
            NWScript.setLocalInt(territoryFlag, TerritoryFlagIDVariableName, flag.getPcTerritoryFlagID());
            NWScript.setName(territoryFlag, playerEntity.getCharacterName() + "'s Territory");

            for(PCTerritoryFlagStructureEntity structure : flag.getStructures())
            {
                oArea = NWScript.getObjectByTag(structure.getLocationAreaTag(), 0);
                position = NWScript.vector((float) structure.getLocationX(), (float) structure.getLocationY(), (float) structure.getLocationZ());
                location = NWScript.location(oArea, position, (float) structure.getLocationOrientation());

                NWObject structurePlaceable = NWScript.createObject(ObjectType.PLACEABLE, structure.getBlueprint().getResref(), location, false, "");
                NWScript.setLocalInt(structurePlaceable, StructureIDVariableName, structure.getPcTerritoryFlagStructureID());
                NWScript.setPlotFlag(structurePlaceable, true);
                NWScript.setUseableFlag(structurePlaceable, structure.isUseable());
            }

        }
    }

    public static boolean IsPCMovingStructure(NWObject oPC)
    {
        return NWScript.getLocalInt(oPC, IsMovingStructureLocationVariableName) == 1 &&
                !NWScript.getLocalObject(oPC, MovingStructureVariableName).equals(NWObject.INVALID);
    }

    public static void SetIsPCMovingStructure(NWObject oPC, NWObject structure, boolean isMoving)
    {
        if(isMoving)
        {
            NWScript.setLocalInt(oPC, IsMovingStructureLocationVariableName, 1);
            NWScript.setLocalObject(oPC, MovingStructureVariableName, structure);
        }
        else
        {
            NWScript.deleteLocalInt(oPC, IsMovingStructureLocationVariableName);
            NWScript.deleteLocalObject(oPC, MovingStructureVariableName);
        }
    }

    public static void SetPlaceableStructureID(NWObject structure, int structureID)
    {
        NWScript.setLocalInt(structure, StructureIDVariableName, structureID);
    }

    public static int GetPlaceableStructureID(NWObject structure)
    {
        return NWScript.getLocalInt(structure, StructureIDVariableName) <= 0 ?
                -1 :
                NWScript.getLocalInt(structure, StructureIDVariableName);
    }

    public static NWObject GetNearestTerritoryFlag(NWLocation location)
    {
        NWObject flag;
        int currentPlaceable = 1;
        NWObject checker = NWScript.createObject(ObjectType.PLACEABLE, TemporaryLocationCheckerObjectResref, location, false, "");

        do
        {
            flag = NWScript.getNearestObject(ObjectType.PLACEABLE, checker, currentPlaceable);

            if(GetTerritoryFlagID(flag) > 0)
            {
                break;
            }

            currentPlaceable++;
        } while(!flag.equals(NWObject.INVALID));


        NWScript.destroyObject(checker, 0.0f);
        return flag;
    }

    public static int CanPCBuildInLocation(NWObject oPC, NWLocation targetLocation)
    {
        StructureRepository repo = new StructureRepository();
        NWObject flag = GetNearestTerritoryFlag(targetLocation);
        NWLocation flagLocation = NWScript.getLocation(flag);
        int pcTerritoryFlagID = GetTerritoryFlagID(flag);
        PCTerritoryFlagEntity entity = repo.GetPCTerritoryFlagByID(pcTerritoryFlagID);
        PlayerGO pcGO = new PlayerGO(oPC);
        float distance = NWScript.getDistanceBetweenLocations(flagLocation, targetLocation);

        if(flag.equals(NWObject.INVALID) ||
                distance > entity.getBlueprint().getMaxBuildDistance())
        {
            return 1;
        }

        if(repo.GetNumberOfStructuresInTerritory(pcTerritoryFlagID) >=
                entity.getBlueprint().getMaxStructuresCount())
        {
            return 2;
        }


        if(entity.getPlayerID().equals(pcGO.getUUID()))
        {
            return 1;
        }

        for(PCTerritoryFlagPermissionEntity permission : entity.getPermissions())
        {
            if(permission.getPlayer().getPCID().equals(pcGO.getUUID()) &&
                    permission.getPermission().getTerritoryFlagPermissionID() == 2) // 2 = Can Build Structures
            {
                return 1;
            }
        }

        return 0;
    }

    public static boolean IsWithinRangeOfTerritoryFlag(NWObject oCheck)
    {
        NWLocation location = NWScript.getLocation(oCheck);
        NWObject flag = GetNearestTerritoryFlag(location);

        if(flag.equals(NWObject.INVALID)) return false;

        float distance = NWScript.getDistanceBetween(oCheck, flag);
        StructureRepository repo = new StructureRepository();
        int flagID = GetTerritoryFlagID(flag);
        PCTerritoryFlagEntity entity = repo.GetPCTerritoryFlagByID(flagID);

        return distance <= entity.getBlueprint().getMaxBuildDistance();
    }

    public static void CreateConstructionSite(NWObject oPC, NWLocation location)
    {
        int buildStatus = CanPCBuildInLocation(oPC, location);

        if(buildStatus == 0) // 0 = Can't do it in that location
        {
            NWScript.floatingTextStringOnCreature(ColorToken.Red() + "You can't build a construction site there." + ColorToken.End(), oPC, false);
        }
        else if(buildStatus == 1) // 1 = Success
        {
            NWScript.createObject(ObjectType.PLACEABLE, ConstructionSiteResref, location, false, "");
            NWScript.floatingTextStringOnCreature("Construction site created! Use the construction site to select a blueprint.", oPC, false);
        }
        else if(buildStatus == 2) // 2 = Territory can't hold any more structures.
        {
            NWScript.floatingTextStringOnCreature(ColorToken.Red() + "The maximum number of structures this territory can manage has been reached. Raze a structure or upgrade your territory to create a new structure." + ColorToken.End(), oPC, false);
        }
    }

    public static int GetConstructionSiteID(NWObject site)
    {
        return NWScript.getLocalInt(site, ConstructionSiteIDVariableName);
    }

    public static void SetConstructionSiteID(NWObject site, int constructionSiteID)
    {
        NWScript.setLocalInt(site, ConstructionSiteIDVariableName, constructionSiteID);
    }

    public static int GetTerritoryFlagID(NWObject flag)
    {
        return NWScript.getLocalInt(flag, TerritoryFlagIDVariableName);
    }

    public static void SelectBlueprint(NWObject oPC, NWObject constructionSite, int blueprintID)
    {
        PlayerGO pcGO = new PlayerGO(oPC);
        StructureRepository repo = new StructureRepository();
        ConstructionSiteEntity entity = new ConstructionSiteEntity();
        NWObject area = NWScript.getArea(constructionSite);
        String areaTag = NWScript.getTag(area);
        NWLocation location = NWScript.getLocation(constructionSite);
        StructureBlueprintEntity blueprint = repo.GetStructureBlueprintByID(blueprintID);

        entity.setLocationAreaTag(areaTag);
        entity.setLocationOrientation(location.getFacing());
        entity.setLocationX(location.getX());
        entity.setLocationY(location.getY());
        entity.setLocationZ(location.getZ());
        entity.setPlayerID(pcGO.getUUID());
        entity.setBlueprint(blueprint);
        entity.setClothRequired(blueprint.getClothRequired());
        entity.setLeatherRequired(blueprint.getLeatherRequired());
        entity.setMetalRequired(blueprint.getMetalRequired());
        entity.setNailsRequired(blueprint.getNailsRequired());
        entity.setWoodRequired(blueprint.getWoodRequired());

        if(IsWithinRangeOfTerritoryFlag(constructionSite))
        {
            NWObject flag = GetNearestTerritoryFlag(location);
            PCTerritoryFlagEntity flagEntity = repo.GetPCTerritoryFlagByID(GetTerritoryFlagID(flag));
            entity.setPcTerritoryFlag(flagEntity);
        }

        repo.Save(entity);
        SetConstructionSiteID(constructionSite, entity.getConstructionSiteID());
    }

    public static void MoveStructure(NWObject oPC, NWLocation location)
    {
        StructureRepository repo = new StructureRepository();
        NWObject target = NWScript.getLocalObject(oPC, MovingStructureVariableName);
        NWObject nearestFlag = GetNearestTerritoryFlag(location);
        NWLocation nearestFlagLocation = NWScript.getLocation(nearestFlag);
        int nearestFlagID = GetTerritoryFlagID(nearestFlag);
        boolean outsideOwnFlagRadius = false;
        int constructionSiteID = GetConstructionSiteID(target);
        int structureID = GetPlaceableStructureID(target);
        NWScript.deleteLocalInt(oPC, IsMovingStructureLocationVariableName);
        NWScript.deleteLocalObject(oPC, MovingStructureVariableName);

        if(target.equals(NWObject.INVALID) ||
                !location.getArea().equals(NWScript.getArea(target))) {
            return;
        }

        // Moving construction site, no blueprint set
        if(constructionSiteID <= 0 && NWScript.getResRef(target).equals(ConstructionSiteResref))
        {
            if(CanPCBuildInLocation(oPC, location) == 0) // 0 = Can't build in this location
            {
                outsideOwnFlagRadius = true;
            }
        }

        // Moving construction site, blueprint is set.
        if(constructionSiteID > 0)
        {
            ConstructionSiteEntity entity = repo.GetConstructionSiteByID(constructionSiteID);
            boolean isTerritoryMarkerConstructionSite = entity.getPcTerritoryFlag() == null;

            // Territory marker - Ensure not in radius of another territory
            if(isTerritoryMarkerConstructionSite )
            {
                PCTerritoryFlagEntity nearestFlagEntity = repo.GetPCTerritoryFlagByID(nearestFlagID);
                if(nearestFlagEntity != null && NWScript.getDistanceBetweenLocations(location, nearestFlagLocation) <= nearestFlagEntity.getBlueprint().getMaxBuildDistance())
                {
                    NWScript.floatingTextStringOnCreature("Cannot move territory markers within the building range of another territory marker.", oPC, false);
                    return;
                }
            }
            else if(entity.getPcTerritoryFlag().getPcTerritoryFlagID() != nearestFlagID ||
                    NWScript.getDistanceBetweenLocations(nearestFlagLocation, location) > entity.getPcTerritoryFlag().getBlueprint().getMaxBuildDistance())
            {
                outsideOwnFlagRadius = true;
            }
            else
            {
                entity.setLocationOrientation(location.getFacing());
                entity.setLocationX(location.getX());
                entity.setLocationY(location.getY());
                entity.setLocationZ(location.getZ());

                repo.Save(entity);
            }
        }
        else if(structureID > 0)
        {
            PCTerritoryFlagStructureEntity entity = repo.GetPCStructureByID(structureID);

            if(entity.getPcTerritoryFlag().getPcTerritoryFlagID() != nearestFlagID ||
                    NWScript.getDistanceBetweenLocations(nearestFlagLocation, location) > entity.getPcTerritoryFlag().getBlueprint().getMaxBuildDistance())
            {
                outsideOwnFlagRadius = true;
            }
            else
            {
                entity.setLocationOrientation(location.getFacing());
                entity.setLocationX(location.getX());
                entity.setLocationY(location.getY());
                entity.setLocationZ(location.getZ());

                repo.Save(entity);
            }
        }



        if(outsideOwnFlagRadius)
        {
            NWScript.floatingTextStringOnCreature("Unable to move structure to that location. New location must be within range of the territory marker it is attached to.", oPC, false);
            return;
        }
        NWObject copy = NWScript.createObject(ObjectType.PLACEABLE, NWScript.getResRef(target), location, false, "");

        if(constructionSiteID > 0) SetConstructionSiteID(copy, constructionSiteID);
        else if (structureID > 0) SetPlaceableStructureID(copy, structureID);

        NWScript.destroyObject(target, 0.0f);
    }

    public static void CompleteStructure(NWObject constructionSite)
    {
        StructureRepository repo = new StructureRepository();
        int constructionSiteID = GetConstructionSiteID(constructionSite);
        ConstructionSiteEntity entity = repo.GetConstructionSiteByID(constructionSiteID);
        StructureBlueprintEntity blueprint = entity.getBlueprint();
        NWLocation location = NWScript.getLocation(constructionSite);

        NWObject structurePlaceable = NWScript.createObject(ObjectType.PLACEABLE, blueprint.getResref(), location, false, "");
        NWScript.destroyObject(constructionSite, 0.0f);

        if(blueprint.isTerritoryFlag())
        {
            PlayerRepository playerRepo = new PlayerRepository();
            PlayerEntity playerEntity = playerRepo.getByUUID(entity.getPlayerID());
            NWScript.setName(structurePlaceable, playerEntity.getCharacterName() + "'s Territory");

            PCTerritoryFlagEntity pcFlag = new PCTerritoryFlagEntity();
            pcFlag.setBlueprint(blueprint);
            pcFlag.setLocationAreaTag(entity.getLocationAreaTag());
            pcFlag.setLocationOrientation(entity.getLocationOrientation());
            pcFlag.setLocationX(entity.getLocationX());
            pcFlag.setLocationY(entity.getLocationY());
            pcFlag.setLocationZ(entity.getLocationZ());
            pcFlag.setPlayerID(entity.getPlayerID());

            repo.Save(pcFlag);
            NWScript.setLocalInt(structurePlaceable, TerritoryFlagIDVariableName, pcFlag.getPcTerritoryFlagID());
        }
        else
        {
            PCTerritoryFlagStructureEntity pcStructure = new PCTerritoryFlagStructureEntity();
            pcStructure.setBlueprint(blueprint);
            pcStructure.setLocationAreaTag(entity.getLocationAreaTag());
            pcStructure.setLocationOrientation(entity.getLocationOrientation());
            pcStructure.setLocationX(entity.getLocationX());
            pcStructure.setLocationY(entity.getLocationY());
            pcStructure.setLocationZ(entity.getLocationZ());
            pcStructure.setPcTerritoryFlag(entity.getPcTerritoryFlag());

            repo.Save(pcStructure);
            NWScript.setLocalInt(structurePlaceable, StructureIDVariableName, pcStructure.getPcTerritoryFlagStructureID());
        }

        repo.Delete(entity);
    }


    public static void RazeTerritory(NWObject flag)
    {
        int flagID = GetTerritoryFlagID(flag);
        StructureRepository repo = new StructureRepository();
        PCTerritoryFlagEntity entity = repo.GetPCTerritoryFlagByID(flagID);
        ArrayList<Integer> constructionSiteIDs = new ArrayList<>();
        ArrayList<Integer> structureSiteIDs = new ArrayList<>();

        for(PCTerritoryFlagStructureEntity structure : entity.getStructures())
        {
            if(!structureSiteIDs.contains(structure.getPcTerritoryFlagStructureID()))
            {
                structureSiteIDs.add(structure.getPcTerritoryFlagStructureID());
            }
        }

        for(ConstructionSiteEntity constructionSite : entity.getConstructionSites())
        {
            if(!constructionSiteIDs.contains(constructionSite.getConstructionSiteID()))
            {
                constructionSiteIDs.add(constructionSite.getConstructionSiteID());
            }
        }


        int currentPlaceable = 1;
        NWObject placeable = NWScript.getNearestObject(ObjectType.PLACEABLE, flag, currentPlaceable);
        while(!placeable.equals(NWObject.INVALID))
        {
            if(NWScript.getDistanceBetween(placeable, flag) > entity.getBlueprint().getMaxBuildDistance()) break;

            if(constructionSiteIDs.contains(GetConstructionSiteID(placeable)) ||
                    structureSiteIDs.contains(GetPlaceableStructureID(placeable)))
            {
                NWScript.destroyObject(placeable, 0.0f);
            }

            currentPlaceable++;
            placeable = NWScript.getNearestObject(ObjectType.PLACEABLE, flag, currentPlaceable);
        }

        NWScript.destroyObject(flag, 0.0f);
        repo.Delete(entity);
    }

    public static void TransferTerritoryOwnership(NWObject oFlag, String newOwnerUUID)
    {
        PlayerRepository playerRepo = new PlayerRepository();
        StructureRepository repo = new StructureRepository();
        int pcFlagID = GetTerritoryFlagID(oFlag);
        PlayerEntity playerEntity = playerRepo.getByUUID(newOwnerUUID);
        PCTerritoryFlagEntity entity = repo.GetPCTerritoryFlagByID(pcFlagID);
        entity.getPermissions().clear();
        entity.setPlayerID(newOwnerUUID);
        repo.Save(entity);

        NWScript.setName(oFlag, playerEntity.getCharacterName() + "'s Territory");

        for(NWObject oPC : NWScript.getPCs())
        {
            PlayerGO pcGO = new PlayerGO(oPC);
            if(pcGO.getUUID().equals(newOwnerUUID))
            {
                NWScript.floatingTextStringOnCreature("Ownership of a territory in " + NWScript.getName(NWScript.getArea(oFlag), false)  + " has been transferred to you.", oPC, false);
                break;
            }
        }
    }

    public static void RazeConstructionSite(NWObject oPC, NWObject site, boolean recoverMaterials)
    {
        int constructionSiteID = GetConstructionSiteID(site);
        if(constructionSiteID > 0)
        {
            StructureRepository repo = new StructureRepository();
            ConstructionSiteEntity entity = repo.GetConstructionSiteByID(constructionSiteID);

            if(recoverMaterials)
            {
                int wood = entity.getBlueprint().getWoodRequired() - entity.getWoodRequired();
                int metal = entity.getBlueprint().getMetalRequired() - entity.getMetalRequired();
                int nails = entity.getBlueprint().getNailsRequired() - entity.getNailsRequired();
                int cloth = entity.getBlueprint().getClothRequired() - entity.getClothRequired();
                int leather = entity.getBlueprint().getLeatherRequired() - entity.getLeatherRequired();

                for(int w = 1; w <= wood; w++) NWScript.createItemOnObject("reo_wood", oPC, 1, "");
                for(int m = 1; m <= metal; m++) NWScript.createItemOnObject("reo_metal", oPC, 1, "");
                for(int n = 1; n <= nails; n++) NWScript.createItemOnObject("reo_nails", oPC, 1, "");
                for(int c = 1; c <= cloth; c++) NWScript.createItemOnObject("reo_cloth", oPC, 1, "");
                for(int l = 1; l <= leather; l++) NWScript.createItemOnObject("reo_leather", oPC, 1, "");

            }

            repo.Delete(entity);
        }
        NWScript.destroyObject(site, 0.0f);
    }

    public static boolean IsConstructionSiteValid(NWObject site)
    {
        StructureRepository repo = new StructureRepository();
        NWLocation siteLocation = NWScript.getLocation(site);
        NWObject flag = StructureSystem.GetNearestTerritoryFlag(siteLocation);
        NWLocation flaglocation = NWScript.getLocation(flag);
        int flagID = StructureSystem.GetTerritoryFlagID(flag);
        int constructionSiteID = StructureSystem.GetConstructionSiteID(site);
        if(flagID <= 0) return true;

        ConstructionSiteEntity constructionSiteEntity = repo.GetConstructionSiteByID(constructionSiteID);
        PCTerritoryFlagEntity flagEntity = repo.GetPCTerritoryFlagByID(flagID);
        float distance = NWScript.getDistanceBetweenLocations(flaglocation, siteLocation);

        // Scenario #1: Territory's structure cap has been reached. Blueprint not set on this construction site.
        //              Site must be razed otherwise player would go over the cap.
        if(constructionSiteID <= 0)
        {
            long structureCount = repo.GetNumberOfStructuresInTerritory(flagID);
            if(structureCount >= flagEntity.getBlueprint().getMaxStructuresCount())
            {
                return false;
            }
        }

        // Scenario #2: Construction site is a territory flag blueprint.
        // Construction site is within the flag's area of influence OR
        // the blueprint selected would bring the flag inside of its area of influence.
        if(constructionSiteEntity != null &&
                constructionSiteEntity.getBlueprint().isTerritoryFlag() &&
                (distance <= flagEntity.getBlueprint().getMaxBuildDistance() ||
                        distance <= constructionSiteEntity.getBlueprint().getMaxBuildDistance()))
        {
            return false;
        }

        return true;
    }

}
