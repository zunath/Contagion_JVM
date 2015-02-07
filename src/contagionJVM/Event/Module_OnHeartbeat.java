package contagionJVM.Event;
import contagionJVM.IScriptEventHandler;
import org.nwnx.nwnx2.jvm.*;
import org.nwnx.nwnx2.jvm.constants.Weather;

@SuppressWarnings("unused")
public class Module_OnHeartbeat implements IScriptEventHandler {
	@Override
	public void runScript(NWObject objSelf) {

		NWScript.executeScript("regen_mod_hb", objSelf);
		SaveCharacters();

	}

	// Export all characters every minute.
	private void SaveCharacters()
	{
		int currentTick = NWScript.getLocalInt(NWObject.MODULE, "SAVE_CHARACTERS_TICK") + 1;

		if(currentTick >= 10)
		{
			NWScript.exportAllCharacters();
			currentTick = 0;
		}

		NWScript.setLocalInt(NWObject.MODULE, "SAVE_CHARACTERS_TICK", currentTick);
	}

}

