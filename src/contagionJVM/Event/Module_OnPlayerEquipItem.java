package contagionJVM.Event;
import contagionJVM.IScriptEventHandler;
import contagionJVM.System.ProgressionSystem;
import org.nwnx.nwnx2.jvm.*;

@SuppressWarnings("unused")
public class Module_OnPlayerEquipItem implements IScriptEventHandler {
	@Override
	public void runScript(final NWObject objSelf) {
		ProgressionSystem progressionSystem = new ProgressionSystem();

		// Bioware Default
		NWScript.executeScript("x2_mod_def_equ", objSelf);

		progressionSystem.OnModuleEquip();
		// Combat System
		NWScript.executeScript("gun_mod_equip", objSelf);
		// Item Durability System
		NWScript.executeScript("dcy_mod_equip", objSelf);
		// Armor
		NWScript.executeScript("armor_mod_equip", objSelf);
		// Inventory Limits
		NWScript.executeScript("inv_mod_equip", objSelf);
	}
}
