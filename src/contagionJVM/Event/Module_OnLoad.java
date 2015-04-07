package contagionJVM.Event;

import contagionJVM.IScriptEventHandler;
import contagionJVM.NWNX.*;
import contagionJVM.System.DeathSystem;
import contagionJVM.System.SpawnSystem;
import contagionJVM.System.StructureSystem;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

@SuppressWarnings("unused")
public class Module_OnLoad implements IScriptEventHandler {
	@Override
	public void runScript(NWObject objSelf) {
		SpawnSystem spawnSystem = new SpawnSystem();

        AddGlobalEventHandlers();
		AddAreaEventHandlers();

		// NWNX Setup
		NWScript.setLocalString(objSelf, "NWNX!INIT", "1");
		NWScript.getLocalObject(objSelf, "NWNX!INIT");
		NWScript.deleteLocalString(objSelf, "NWNX!INIT");

		// Bioware default
		NWScript.executeScript("x2_mod_def_load", objSelf);
		// SimTools and NWNX
		NWScript.executeScript("fky_chat_modload", objSelf);
		// Event Initialization
		NWScript.executeScript("initialize_event", objSelf);
		// Key Items
		NWScript.executeScript("key_item_modload", objSelf);
        DeathSystem.OnModuleLoad();
		// Portrait Selection System
		NWScript.executeScript("portrait_modload", objSelf);
		// Craft System
		NWScript.executeScript("craft_mod_load", objSelf);
		StructureSystem.OnModuleLoad();
		// Spawn System
		spawnSystem.ZSS_OnModuleLoad();


	}

	private void AddAreaEventHandlers()
	{
		NWObject area = NWNX_Funcs.GetFirstArea();
		while(NWScript.getIsObjectValid(area))
		{
			String result = NWNX_Funcs.SetEventHandler(area, AreaScript.OnEnter, "reo_area_enter");
			NWNX_Funcs.SetEventHandler(area, AreaScript.OnExit, "reo_area_exit");
			NWNX_Funcs.SetEventHandler(area, AreaScript.OnHeartbeat, "reo_area_hb");
			NWNX_Funcs.SetEventHandler(area, AreaScript.OnUserDefinedEvent, "reo_area_user");

			area = NWNX_Funcs.GetNextArea();
		}
	}

    private void AddGlobalEventHandlers()
    {
        NWNX_Events.SetGlobalEventHandler(NWNX_Events.EVENT_TYPE_ATTACK, "reo_mod_attack");
        NWNX_Events.SetGlobalEventHandler(NWNX_Events.EVENT_TYPE_CAST_SPELL, "reo_mod_castspel");
        NWNX_Events.SetGlobalEventHandler(NWNX_Events.EVENT_TYPE_EXAMINE, "reo_mod_examine");
        NWNX_Events.SetGlobalEventHandler(NWNX_Events.EVENT_TYPE_PICKPOCKET, "reo_mod_pickpock");
        NWNX_Events.SetGlobalEventHandler(NWNX_Events.EVENT_TYPE_SAVE_CHAR, "reo_mod_save");
        NWNX_Events.SetGlobalEventHandler(NWNX_Events.EVENT_TYPE_TOGGLE_MODE, "reo_mod_toggmode");
        NWNX_Events.SetGlobalEventHandler(NWNX_Events.EVENT_TYPE_TOGGLE_PAUSE, "reo_mod_toggpaus");
        NWNX_Events.SetGlobalEventHandler(NWNX_Events.EVENT_TYPE_USE_FEAT, "reo_mod_usefeat");
        NWNX_Events.SetGlobalEventHandler(NWNX_Events.EVENT_TYPE_USE_ITEM, "reo_mod_useitem");

		NWNX_DMActions.SetDMActionScript(DMActionType.CREATE_ITEM_ON_AREA, "reo_dm_areaitem");
		NWNX_DMActions.SetDMActionScript(DMActionType.CREATE_ITEM_ON_OBJECT, "reo_dm_objitem");
		NWNX_DMActions.SetDMActionScript(DMActionType.CREATE_PLACEABLE, "reo_dm_placeable");
		NWNX_DMActions.SetDMActionScript(DMActionType.GIVE_GOLD, "reo_dm_gold");
		NWNX_DMActions.SetDMActionScript(DMActionType.GIVE_LEVEL, "reo_dm_level");
		NWNX_DMActions.SetDMActionScript(DMActionType.GIVE_XP, "reo_dm_xp");
		NWNX_DMActions.SetDMActionScript(DMActionType.HEAL_CREATURE, "reo_dm_heal");
		NWNX_DMActions.SetDMActionScript(DMActionType.REST_CREATURE, "reo_dm_rest");
		NWNX_DMActions.SetDMActionScript(DMActionType.RUNSCRIPT, "reo_dm_runscript");
		NWNX_DMActions.SetDMActionScript(DMActionType.SPAWN_CREATURE, "reo_dm_spawn");
		NWNX_DMActions.SetDMActionScript(DMActionType.TOGGLE_IMMORTALITY, "reo_dm_immortal");
		NWNX_DMActions.SetDMActionScript(DMActionType.TOGGLE_INVULNERABILITY, "reo_dm_invuln");
    }
}
