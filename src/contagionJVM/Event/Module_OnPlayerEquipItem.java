package contagionJVM.Event;
import contagionJVM.IScriptEventHandler;
import contagionJVM.System.CombatSystem;
import contagionJVM.System.DurabilitySystem;
import contagionJVM.System.ProgressionSystem;
import org.nwnx.nwnx2.jvm.*;

@SuppressWarnings("unused")
public class Module_OnPlayerEquipItem implements IScriptEventHandler {
	@Override
	public void runScript(final NWObject objSelf) {
		ProgressionSystem progressionSystem = new ProgressionSystem();
        CombatSystem combatSystem = new CombatSystem();

		// Bioware Default
		NWScript.executeScript("x2_mod_def_equ", objSelf);

		progressionSystem.OnModuleEquip();
		// Combat System
        combatSystem.OnModuleEquip();
		// Item Durability System
        DurabilitySystem.OnModuleEquip();
		// Armor
		NWScript.executeScript("armor_mod_equip", objSelf);
		// Inventory Limits
		NWScript.executeScript("inv_mod_equip", objSelf);
	}
}
