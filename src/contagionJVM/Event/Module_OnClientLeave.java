package contagionJVM.Event;
import contagionJVM.Entities.PlayerEntity;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.IScriptEventHandler;
import contagionJVM.Repository.PlayerRepository;
import contagionJVM.System.RadioSystem;
import org.nwnx.nwnx2.jvm.*;

@SuppressWarnings("unused")
public class Module_OnClientLeave implements IScriptEventHandler {
	@Override
	public void runScript(NWObject objSelf) {
		RadioSystem radioSystem = new RadioSystem();

		NWObject pc = NWScript.getExitingObject();
		NWScript.exportSingleCharacter(pc);
		SaveCharacter(pc);

		// SimTools
		NWScript.executeScript("fky_chat_clexit", objSelf);
		// Radio System - Also used for NWNX chat (Different from SimTools)
		radioSystem.OnModuleLeave();
	}

	private void SaveCharacter(NWObject pc) {

		if(NWScript.getIsDM(pc)) return;

		PlayerGO gameObject = new PlayerGO(pc);
		PlayerRepository repo = new PlayerRepository();
		String uuid = gameObject.getUUID();
		NWLocation location = NWScript.getLocation(pc);

		PlayerEntity entity = repo.getByUUID(uuid);
		entity.setCharacterName(NWScript.getName(pc, false));
		entity.setHitPoints(NWScript.getCurrentHitPoints(pc));
		entity.setLocationAreaTag(NWScript.getTag(NWScript.getArea(pc)));
		entity.setLocationOrientation(NWScript.getFacing(pc));
		entity.setLocationX(location.getX());
		entity.setLocationY(location.getY());
		entity.setLocationZ(location.getZ());

		repo.save(entity);
	}
}
