package contagionJVM.Item;

import contagionJVM.IScriptEventHandler;
import contagionJVM.NWNX.NWNX_Events;
import contagionJVM.System.FoodSystem;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

@SuppressWarnings("unused")
public class Food implements IScriptEventHandler {
    @Override
    public void runScript(NWObject oPC) {
        NWObject oItem = NWNX_Events.GetEventItem();
        int amount = NWScript.getLocalInt(oItem, "HUNGER_RESTORE");

        FoodSystem.IncreaseHungerLevel(oPC, amount);
        NWScript.setItemCharges(oItem, NWScript.getItemCharges(oItem) - 1);
    }
}
