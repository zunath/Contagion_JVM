package contagionJVM.System;

import contagionJVM.Helper.LocalArray;
import contagionJVM.NWNX.NWNX_Funcs;
import contagionJVM.NWNX.NWNX_TMI;
import org.nwnx.nwnx2.jvm.NWLocation;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.Scheduler;
import org.nwnx.nwnx2.jvm.constants.ObjectType;

import java.util.Objects;

public class SpawnSystem {

    // The amount of time between spawns, in seconds
    // Default: 120.0 (2 minutes)
    final int ZSS_SPAWN_DELAY = 120;

    // The name of the variable which determines the name of the spawn points in an area
    // This variable is stored on the area NWObject
    final String ZSS_WAYPOINT_NAME = "ZSS_WAYPOINT_NAME";

    // The name of the variable which determines which group to pull spawn resrefs from.
    // This variable is stored on the area NWObject
    final String ZSS_GROUP_ID = "ZSS_GROUP_ID";

    // The name of the variable which determines how many spawn points there are in an area
    // This variable is stored on the area NWObject
    final String ZSS_SPAWN_WAYPOINT_COUNT = "ZSS_SPAWN";

    // The name of the variable which determines how many respawn points there are in an area
    // This variable is stored on the area NWObject
    final String ZSS_RESPAWN_WAYPOINT_COUNT = "ZSS_RESPAWN";

    // The name of the array which stores spawn waypoint NWObjects
    final String ZSS_SPAWN_WAYPOINT_OBJECT_ARRAY = "ZSS_SPAWN_WAYPOINT_OBJECT_ARRAY";

    // The name of the array which stores respawn waypoint NWObjects
    final String ZSS_RESPAWN_WAYPOINT_OBJECT_ARRAY = "ZSS_RESPAWN_WAYPOINT_OBJECT_ARRAY";

    // The name of the variable which stores the number of zombies to spawn in an area
    // This variable is stored on the area NWObject
    final String ZSS_ZOMBIE_COUNT = "ZSS_COUNT";

    // Name of the variable which tells the system whether or not a zombie was spawned by the system
    // as opposed to being spawned by a DM.
    final String ZSS_ZOMBIE_SPAWNED = "ZSS_ZOMBIE_SPAWNED";

    // Name of the variable which keeps track of the number of players in an area
    final String ZSS_PLAYER_COUNT = "ZSS_PLAYER_COUNT";

    // Resref of the spawn waypoints.
    final String ZSS_SPAWN_WAYPOINT_RESREF = "zombie_spawn";

    // Resref of the respawn waypoints.
    final String ZSS_RESPAWN_WAYPOINT_RESREF = "zombie_respawn";




    private void ZSS_DelayCreateZombie(String sZombieResref, NWLocation lWaypointLocation, NWObject oArea)
    {
        // Only respawn if there's PCs in the area
        if(NWScript.getLocalInt(oArea, ZSS_PLAYER_COUNT) > 0)
        {
            final NWObject oZombie = NWScript.createObject(ObjectType.CREATURE, sZombieResref, lWaypointLocation, false, "");
            // Just to make sure this zombie was spawned by the spawn system and not by a DM
            NWScript.setLocalInt(oZombie, ZSS_ZOMBIE_SPAWNED, 1);

            Scheduler.assign(oZombie, new Runnable() {
                @Override
                public void run() {
                    NWScript.setFacing(0.01f * NWScript.random(3600));
                }
            });
            Scheduler.flushQueues();
        }
    }

    public void ZSS_OnAreaEnter(NWObject oArea)
    {
        NWObject oPC = NWScript.getEnteringObject();

        if(!NWScript.getIsPC(oPC) || NWScript.getIsDM(oPC)) return;



        int iZombieCount = NWScript.getLocalInt(oArea, ZSS_ZOMBIE_COUNT);
        int iWaypointCount = NWScript.getLocalInt(oArea, ZSS_SPAWN_WAYPOINT_COUNT);
        int iGroupID = NWScript.getLocalInt(oArea, ZSS_GROUP_ID);
        if(iGroupID < 1) iGroupID = 1;


        // Determine if this is the first PC in the area
        int iPCCount = 0;
        NWObject[] players = NWScript.getPCs();

        for(NWObject pc : players)
        {
            System.out.println("PC area: " + NWScript.getName(NWScript.getArea(pc), false));

            if(oArea.equals(NWScript.getArea(pc)))
            {
                iPCCount++;
            }
        }


        System.out.println("iPCCount = " + iPCCount);

        // Only spawn zombies if this is the first PC in the area
        if(iPCCount == 1)
        {
            boolean bUnique = true;

            for(int iCurrentZombie = 1; iCurrentZombie <= iZombieCount; iCurrentZombie++)
            {
                int iWaypoint;

                // Ensure that at least one zombie will be placed at each spawn point
                if(iCurrentZombie > iWaypointCount)
                {
                    iWaypoint = NWScript.random(iWaypointCount) + 1;
                }
                else
                {
                    iWaypoint = iCurrentZombie;
                }

                String sResref = ZSS_GetZombieToSpawn(iGroupID, bUnique);
                NWObject oWaypoint = LocalArray.GetLocalArrayObject(oArea, ZSS_SPAWN_WAYPOINT_OBJECT_ARRAY, iWaypoint);
                NWLocation lLocation = NWScript.getLocation(oWaypoint);

                System.out.println("About to create zombie resref = " + sResref);
                final NWObject oZombie = NWScript.createObject(ObjectType.CREATURE, sResref, lLocation, false, "");

                System.out.println("created zombie resref = " + sResref);

                // Mark zombie as spawned via the system (as opposed from a DM)
                NWScript.setLocalInt(oZombie, ZSS_ZOMBIE_SPAWNED, 1);

                // NWScript.randomize facing
                Scheduler.assign(oZombie, new Runnable() {
                    @Override
                    public void run() {
                        NWScript.setFacing(0.01f * NWScript.random(3600));
                    }
                });

                // No more chances to spawn a unique creature
                bUnique = false;
            }
        }

        // Update PC counter
        NWScript.setLocalInt(oArea, ZSS_PLAYER_COUNT, iPCCount);
        Scheduler.flushQueues();
    }

    public void ZSS_OnAreaExit(NWObject oArea)
    {
        NWObject oPC = NWScript.getExitingObject();

        if(!NWScript.getIsPC(oPC) || NWScript.getIsDM(oPC)) return;

        int iPCCount = 0;
        NWObject[] players = NWScript.getPCs();

        for(NWObject pc : players)
        {
            if(oArea == NWScript.getArea(pc))
            {
                iPCCount++;
            }
        }

        // Last PC exited the area. Despawn all zombies.
        if(iPCCount <= 0)
        {
            NWObject[] objects = NWScript.getObjectsInArea(oArea);

            for(NWObject currentObject : objects)
            {
                if(NWScript.getLocalInt(currentObject, ZSS_ZOMBIE_SPAWNED) == 1)
                {
                    NWScript.destroyObject(currentObject, 0.0f);
                }
            }
        }

        // Update PC counter
        NWScript.setLocalInt(oArea, ZSS_PLAYER_COUNT, iPCCount);
    }

    public void ZSS_OnModuleLoad()
    {
        NWNX_TMI.SetTMILimit(7000000);

        NWObject oArea = NWNX_Funcs.GetFirstArea();

        while(NWScript.getIsObjectValid(oArea))
        {
            String sSpawnID = NWScript.getResRef(oArea);
            int iSpawnCount = 0;
            int iRespawnCount = 0;

            NWObject[] areaObjects = NWScript.getObjectsInArea(oArea);
            for(NWObject obj : areaObjects)
            {
                String sResref = NWScript.getResRef(obj);
                // Spawn waypoint
                if(Objects.equals(sResref, ZSS_SPAWN_WAYPOINT_RESREF))
                {
                    iSpawnCount++;
                    //SetTag(oWaypoint, sSpawnPrefix + IntToString(iSpawnCount));
                    LocalArray.SetLocalArrayObject(oArea, ZSS_SPAWN_WAYPOINT_OBJECT_ARRAY, iSpawnCount, obj);
                }
                // Respawn waypoint
                else if(Objects.equals(sResref, ZSS_RESPAWN_WAYPOINT_RESREF))
                {
                    iRespawnCount++;
                    //SetTag(oWaypoint, sRespawnPrefix + IntToString(iRespawnCount));
                    LocalArray.SetLocalArrayObject(oArea, ZSS_RESPAWN_WAYPOINT_OBJECT_ARRAY, iRespawnCount, obj);
                }
            }

            // Mark the number of spawn and respawn waypoints for this area
            NWScript.setLocalInt(oArea, ZSS_SPAWN_WAYPOINT_COUNT, iSpawnCount);
            NWScript.setLocalInt(oArea, ZSS_RESPAWN_WAYPOINT_COUNT, iRespawnCount);
            // Mark the unique identifier (the resref)
            NWScript.setLocalString(oArea, ZSS_WAYPOINT_NAME, sSpawnID);

            oArea = NWNX_Funcs.GetNextArea();
        }

        NWScript.printString("Spawn System: Module load procedure complete.");

        // Set the TMI limit back to normal
        NWNX_TMI.SetTMILimit(131071);
    }

    public void ZSS_OnZombieDeath(NWObject oZombie)
    {
        final NWObject oArea = NWScript.getArea(oZombie);

        int iPCCount = NWScript.getLocalInt(oArea, ZSS_PLAYER_COUNT);
        int iWaypointCount = NWScript.getLocalInt(oArea, ZSS_RESPAWN_WAYPOINT_COUNT);
        int iGroupID = NWScript.getLocalInt(oArea, ZSS_GROUP_ID);

        // Safety check
        if(iGroupID < 1) iGroupID = 1;

        // Only respawn zombies that were created through this system (as opposed from DMs)
        if(NWScript.getLocalInt(oZombie, ZSS_ZOMBIE_SPAWNED) == 1)
        {
            // Only spawn when PCs are in the area
            if(iPCCount > 0)
            {
                int iWaypoint = NWScript.random(iWaypointCount) + 1;
                final String sResref = ZSS_GetZombieToSpawn(iGroupID, false);
                NWObject oWaypoint = LocalArray.GetLocalArrayObject(oArea, ZSS_RESPAWN_WAYPOINT_OBJECT_ARRAY, iWaypoint);
                final NWLocation lLocation = NWScript.getLocation(oWaypoint);

                Scheduler.delay(oZombie, ZSS_SPAWN_DELAY * 1000, new Runnable() {
                    @Override
                    public void run() {
                        ZSS_DelayCreateZombie(sResref, lLocation, oArea);
                    }
                });

            }
        }

        Scheduler.flushQueues();
    }


    private String ZSS_GetZombieToSpawn(int iGroupID, boolean bUnique)
    {
        String sResref = "";

        switch(iGroupID)
        {
            // Group #1 - Basic Zombies (Tier 1)
            case 1:
            {
                int iNumberOfChoices = 15;

                switch(NWScript.random(iNumberOfChoices) + 1)
                {
                    case 1:  sResref = "reo_zombie_1";  break;
                    case 2:  sResref = "reo_zombie_2";  break;
                    case 3:  sResref = "reo_zombie_3";  break;
                    case 4:  sResref = "reo_zombie_4";  break;
                    case 5:  sResref = "reo_zombie_5";  break;
                    case 6:  sResref = "reo_zombie_6";  break;
                    case 7:  sResref = "reo_zombie_7";  break;
                    case 8:  sResref = "reo_zombie_8";  break;
                    case 9:  sResref = "reo_zombie_9";  break;
                    case 10: sResref = "reo_zombie_10"; break;
                    case 11: sResref = "reo_zombie_11"; break;
                    case 12: sResref = "reo_zombie_12"; break;
                    case 13: sResref = "reo_zombie_13"; break;
                    case 14: sResref = "reo_zombie_14"; break;
                    case 15: sResref = "reo_zombie_15"; break;
                }
                break;
            }

            // Group #2 - Walkers and MA-121 Hunter Alphas (Tier 2)
            case 2:
            {
                if(bUnique)
                {
                    int iChance = NWScript.random(100) + 1;

                    // 15% to spawn a Hunter
                    if(iChance <= 15)
                    {
                        sResref = "reo_hunter_1";
                    }
                }

                if(Objects.equals(sResref, ""))
                {
                    int iNumberOfChoices = 15;

                    // In all other cases, spawn normal Walkers
                    switch(NWScript.random(iNumberOfChoices) + 1)
                    {
                        case 1:  sResref = "reo_walker_1";  break;
                        case 2:  sResref = "reo_walker_2";  break;
                        case 3:  sResref = "reo_walker_3";  break;
                        case 4:  sResref = "reo_walker_4";  break;
                        case 5:  sResref = "reo_walker_5";  break;
                        case 6:  sResref = "reo_walker_6";  break;
                        case 7:  sResref = "reo_walker_7";  break;
                        case 8:  sResref = "reo_walker_8";  break;
                        case 9:  sResref = "reo_walker_9";  break;
                        case 10: sResref = "reo_walker_10"; break;
                        case 11: sResref = "reo_walker_11"; break;
                        case 12: sResref = "reo_walker_12"; break;
                        case 13: sResref = "reo_walker_13"; break;
                        case 14: sResref = "reo_walker_14"; break;
                        case 15: sResref = "reo_walker_15"; break;
                    }
                }
                break;
            }

        }

        return sResref;
    }
    

}
