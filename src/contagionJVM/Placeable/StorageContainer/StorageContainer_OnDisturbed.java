package contagionJVM.Placeable.StorageContainer;

import contagionJVM.IScriptEventHandler;
import contagionJVM.System.StorageSystem;
import org.nwnx.nwnx2.jvm.NWObject;

@SuppressWarnings("unused")
public class StorageContainer_OnDisturbed implements IScriptEventHandler {
    @Override
    public void runScript(NWObject oChest) {
        StorageSystem.OnChestDisturbed(oChest);
    }
}
