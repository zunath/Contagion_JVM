package contagionJVM.System;

import contagionJVM.Entities.ConstructionSiteEntity;
import contagionJVM.Entities.PCTerritoryFlagEntity;
import contagionJVM.Entities.PCTerritoryFlagPermissionEntity;
import contagionJVM.Entities.PCTerritoryFlagStructureEntity;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.Helper.ColorToken;
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
    private static final String TerritoryFlagResref = "territory_flag";
    private static final String TemporaryLocationCheckerObjectResref = "temp_loc_check";


    public static void OnModuleLoad()
    {
        StructureRepository repo = new StructureRepository();

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

            NWObject territoryFlag = NWScript.createObject(ObjectType.PLACEABLE, flag.getTerritoryFlag().getResref(), location, false, "");
            NWScript.setLocalInt(territoryFlag, TerritoryFlagIDVariableName, flag.getPcTerritoryFlagID());

            for(PCTerritoryFlagStructureEntity structure : flag.getStructures())
            {
                oArea = NWScript.getObjectByTag(structure.getLocationAreaTag(), 0);
                position = NWScript.vector((float) structure.getLocationX(), (float) structure.getLocationY(), (float) structure.getLocationZ());
                location = NWScript.location(oArea, position, (float) structure.getLocationOrientation());

                NWObject structurePlaceable = NWScript.createObject(ObjectType.PLACEABLE, flag.getTerritoryFlag().getResref(), location, false, "");
                NWScript.setLocalInt(structurePlaceable, StructureIDVariableName, structure.getPcTerritoryFlagStructureID());
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

    public static int GetPlaceableStructureID(NWObject structure)
    {
        return NWScript.getLocalInt(structure, StructureIDVariableName) <= 0 ?
                -1 :
                NWScript.getLocalInt(structure, StructureIDVariableName);
    }

    public static NWObject GetNearestTerritoryFlag(NWLocation location)
    {
        NWObject checker = NWScript.createObject(ObjectType.PLACEABLE, TemporaryLocationCheckerObjectResref, location, false, "");
        NWObject flag = NWScript.getNearestObjectByTag(TerritoryFlagResref, checker, 1);
        NWScript.destroyObject(checker, 0.0f);

        return flag;
    }

    public static boolean CanPCBuildConstructionSite(NWObject oPC, NWLocation targetLocation)
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

    public static void CreateConstructionSite(NWObject oPC, NWLocation location)
    {
        if(!CanPCBuildConstructionSite(oPC, location))
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

}
