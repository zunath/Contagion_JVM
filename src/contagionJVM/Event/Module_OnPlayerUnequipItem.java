package contagionJVM.Event;
import contagionJVM.IScriptEventHandler;
import contagionJVM.System.CombatSystem;
import contagionJVM.System.InventorySystem;
import org.nwnx.nwnx2.jvm.*;

@SuppressWarnings("unused")
public class Module_OnPlayerUnequipItem implements IScriptEventHandler {
	@Override
	public void runScript(NWObject objSelf) {
		CombatSystem combatSystem = new CombatSystem();

		// Bioware Default
		NWScript.executeScript("x2_mod_def_unequ", objSelf);
		combatSystem.OnModuleUnequip();
		// Armor System
		NWScript.executeScript("armor_mod_unequi", objSelf);

        InventorySystem.OnModuleUnEquipItem();

	}
}
