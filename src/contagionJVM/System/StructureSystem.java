package contagionJVM.System;

import contagionJVM.Entities.*;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.Helper.ColorToken;
import contagionJVM.Repository.PlayerRepository;
import contagionJVM.Repository.StructureRepository;
import org.nwnx.nwnx2.jvm.NWLocation;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.NWVector;
import org.nwnx.nwnx2.jvm.constants.ObjectType;

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

                NWObject structurePlaceable = NWScript.createObject(ObjectType.PLACEABLE, flag.getBlueprint().getResref(), location, false, "");
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

    public static boolean CanPCBuildInLocation(NWObject oPC, NWLocation targetLocation)
    {
        NWObject flag = GetNearestTerritoryFlag(targetLocation);

        if(flag.equals(NWObject.INVALID))
        {
            return true;
        }

        PlayerGO pcGO = new PlayerGO(oPC);
        int pcTerritoryFlagID = NWScript.getLocalInt(flag, TerritoryFlagIDVariableName);
        StructureRepository repo = new StructureRepository();
        PCTerritoryFlagEntity entity = repo.GetPCTerritoryFlagByID(pcTerritoryFlagID);

        if(entity.getPlayerID().equals(pcGO.getUUID()))
        {
            return true;
        }

        for(PCTerritoryFlagPermissionEntity permission : entity.getPermissions())
        {
            if(permission.getPlayerID().equals(pcGO.getUUID()) &&
                    permission.getTerritoryFlagPermissionID() == 2) // 2 = Can Build
            {
                return true;
            }
        }

        return false;
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
        if(!CanPCBuildInLocation(oPC, location))
        {
            NWScript.floatingTextStringOnCreature(ColorToken.Red() + "You can't build a construction site there." + ColorToken.End(), oPC, false);
            return;
        }

        NWScript.createObject(ObjectType.PLACEABLE, ConstructionSiteResref, location, false, "");

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
            if(!CanPCBuildInLocation(oPC, location))
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
            pcStructure.setStructureBlueprintID(blueprint.getStructureBlueprintID());
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

}
