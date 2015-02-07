package contagionJVM.Event;

import contagionJVM.IScriptEventHandler;
import contagionJVM.NWNX.AreaScript;
import contagionJVM.NWNX.NWNX_Funcs;
import contagionJVM.System.SpawnSystem;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

@SuppressWarnings("unused")
public class Module_OnLoad implements IScriptEventHandler {
	@Override
	public void runScript(NWObject objSelf) {
		SpawnSystem spawnSystem = new SpawnSystem();

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
		// Death System
		NWScript.executeScript("dth_mod_load", objSelf);
		// Portrait Selection System
		NWScript.executeScript("portrait_modload", objSelf);
		// Craft System
		NWScript.executeScript("craft_mod_load", objSelf);

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

}
