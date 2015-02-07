package contagionJVM.Event;
import contagionJVM.Constants;
import contagionJVM.IScriptEventHandler;
import org.nwnx.nwnx2.jvm.*;
import org.nwnx.nwnx2.jvm.constants.AttackBonus;
import org.nwnx.nwnx2.jvm.constants.InventorySlot;

@SuppressWarnings("unused")
public class Module_OnPlayerUnequipItem implements IScriptEventHandler {
	@Override
	public void runScript(NWObject objSelf) {
		// Bioware Default
		NWScript.executeScript("x2_mod_def_unequ", objSelf);
		// Firearm System
		NWScript.executeScript("gun_mod_unequip", objSelf);
		// Armor System
		NWScript.executeScript("armor_mod_unequi", objSelf);
		// Inventory Limits
		NWScript.executeScript("inv_mod_unequip", objSelf);

	}
}
