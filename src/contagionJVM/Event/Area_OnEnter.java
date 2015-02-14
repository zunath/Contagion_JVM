package contagionJVM.Event;

import contagionJVM.Entities.PlayerEntity;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.IScriptEventHandler;
import contagionJVM.Repository.PlayerRepository;
import contagionJVM.System.SpawnSystem;
import org.nwnx.nwnx2.jvm.*;

@SuppressWarnings("UnusedDeclaration")
public class Area_OnEnter implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        SpawnSystem spawnSystem = new SpawnSystem();

        NWObject oPC = NWScript.getEnteringObject();

        // Temporary Sanctuary Effects
        NWScript.executeScript("sanctuary", objSelf);
        LoadLocation(oPC, objSelf);
        spawnSystem.ZSS_OnAreaEnter(objSelf);
        // Show map in designated areas
        NWScript.executeScript("show_map", objSelf);
        // Initialize camera in designated areas.
        NWScript.executeScript("initialize_camer", objSelf);

        // Save characters
        if(NWScript.getIsObjectValid(oPC) && NWScript.getIsPC(oPC) && !NWScript.getIsDM(oPC)) NWScript.exportSingleCharacter(oPC);
    }


    private void LoadLocation(NWObject oPC, NWObject oArea)
    {
        if(NWScript.getTag(oArea).equals("ooc_area"))
        {
            PlayerGO pcGO = new PlayerGO(oPC);
            PlayerEntity entity = new PlayerRepository().getByUUID(pcGO.getUUID());
            NWObject area = NWScript.getObjectByTag(entity.getLocationAreaTag(), 0);
            final NWLocation location = new NWLocation(area,
                    entity.getLocationX(),
                    entity.getLocationY(),
                    entity.getLocationZ(),
                    entity.getLocationOrientation());

            Scheduler.assign(oPC, new Runnable() {
                @Override
                public void run() {
                    NWScript.actionJumpToLocation(location);
                }
            });
        }

        Scheduler.flushQueues();
    }

}
