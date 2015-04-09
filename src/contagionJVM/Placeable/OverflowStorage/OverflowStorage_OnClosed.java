package contagionJVM.Placeable.OverflowStorage;

import contagionJVM.IScriptEventHandler;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

@SuppressWarnings("unused")
public class OverflowStorage_OnClosed implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        for(NWObject item : NWScript.getItemsInInventory(objSelf))
        {
            NWScript.destroyObject(item, 0.0f);
        }

        NWScript.destroyObject(objSelf, 0.0f);
    }
}
