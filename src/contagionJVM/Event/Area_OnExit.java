package contagionJVM.Event;

import contagionJVM.IScriptEventHandler;
import contagionJVM.System.SpawnSystem;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

@SuppressWarnings("UnusedDeclaration")
public class Area_OnExit implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        SpawnSystem spawnSystem = new SpawnSystem();

        // Spawn System
        spawnSystem.ZSS_OnAreaExit(objSelf);
    }
}
