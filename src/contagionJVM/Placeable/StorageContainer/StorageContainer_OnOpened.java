package contagionJVM.Placeable.StorageContainer;

import contagionJVM.IScriptEventHandler;
import contagionJVM.System.StorageSystem;
import org.nwnx.nwnx2.jvm.NWObject;

@SuppressWarnings("unused")
public class StorageContainer_OnOpened implements IScriptEventHandler {
    @Override
    public void runScript(NWObject oChest) {
        StorageSystem.OnChestOpened(oChest);
    }
}
