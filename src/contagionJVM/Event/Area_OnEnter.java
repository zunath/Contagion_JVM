package contagionJVM.Event;

import contagionJVM.IScriptEventHandler;
import contagionJVM.System.SpawnSystem;
import org.nwnx.nwnx2.jvm.NWEffect;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.constants.*;

@SuppressWarnings("UnusedDeclaration")
public class Area_OnEnter implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        SpawnSystem spawnSystem = new SpawnSystem();

        NWObject oPC = NWScript.getEnteringObject();

        // Temporary Sanctuary Effects
        NWScript.executeScript("sanctuary", objSelf);
        // Persistent Locations
        NWScript.executeScript("loc_area_enter", objSelf);
        spawnSystem.ZSS_OnAreaEnter(objSelf);
        // Show map in designated areas
        NWScript.executeScript("show_map", objSelf);
        // Initialize camera in designated areas.
        NWScript.executeScript("initialize_camer", objSelf);

        // Save characters
        if(NWScript.getIsObjectValid(oPC) && NWScript.getIsPC(oPC) && !NWScript.getIsDM(oPC)) NWScript.exportSingleCharacter(oPC);
    }



}
