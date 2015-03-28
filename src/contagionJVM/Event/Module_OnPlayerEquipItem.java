package contagionJVM.Event;
import contagionJVM.IScriptEventHandler;
import contagionJVM.System.*;
import org.nwnx.nwnx2.jvm.*;

@SuppressWarnings("unused")
public class Module_OnPlayerEquipItem implements IScriptEventHandler {
	@Override
	public void runScript(final NWObject objSelf) {
        CombatSystem combatSystem = new CombatSystem();

		// Bioware Default
		NWScript.executeScript("x2_mod_def_equ", objSelf);
		ProgressionSystem.OnModuleEquip();
		// Combat System
        combatSystem.OnModuleEquip();
		// Item Durability System
        DurabilitySystem.OnModuleEquip();
		ArmorSystem.OnModuleEquipItem();

        InventorySystem.OnModuleEquipItem();
	}
}
