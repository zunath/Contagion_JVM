package contagionJVM.Event;

import contagionJVM.GameObject.ItemGO;
import contagionJVM.IScriptEventHandler;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.constants.InventorySlot;

@SuppressWarnings("UnusedDeclaration")
public class TestEvent implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        NWObject oPC = NWScript.getLastUsedBy();
        NWObject oItem = NWScript.getItemInSlot(InventorySlot.RIGHTHAND, oPC);
        ItemGO itemGO = new ItemGO(oItem);

        itemGO.setDurability(45);
    }
}
