package contagionJVM.Event;
import contagionJVM.Constants;
import contagionJVM.Entities.ActivePlayerEntity;
import contagionJVM.Entities.PlayerEntity;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.IScriptEventHandler;
import contagionJVM.Repository.ActivePlayerRepository;
import contagionJVM.Repository.PlayerRepository;
import contagionJVM.System.DiseaseSystem;
import contagionJVM.System.FoodSystem;
import contagionJVM.System.ProgressionSystem;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.nwnx.nwnx2.jvm.*;
import org.nwnx.nwnx2.jvm.constants.DurationType;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Module_OnHeartbeat implements IScriptEventHandler {

	PlayerRepository playerRepo;

	@Override
	public void runScript(NWObject objSelf) {

		playerRepo = new PlayerRepository();

		NWObject[] players = NWScript.getPCs();
		for(NWObject pc : players)
		{
			if(!NWScript.getIsDM(pc))
			{
				PlayerGO pcGO = new PlayerGO(pc);
				PlayerEntity entity = playerRepo.getByUUID(pcGO.getUUID());

				if(entity != null)
				{
					entity = HandleRegenerationTick(pc, entity);
					entity = HandleDiseaseTick(pc, entity);
					entity = HandleFoodTick(pc, entity);
					playerRepo.save(entity);
				}
			}
		}

		SaveCharacters();
		RefreshActivePlayers();
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

	private PlayerEntity HandleRegenerationTick(NWObject oPC, PlayerEntity entity)
	{
		entity.setRegenerationTick(entity.getRegenerationTick() - 1);
		int rate = Constants.BaseHPRegenRate - ProgressionSystem.GetPlayerSkillLevel(oPC, ProgressionSystem.SkillType_NATURAL_REGENERATION);
		int amount = entity.getHpRegenerationAmount();

		if(entity.getRegenerationTick() <= 0)
		{
			if(NWScript.getCurrentHitPoints(oPC) < NWScript.getMaxHitPoints(oPC))
			{
				NWScript.applyEffectToObject(DurationType.INSTANT, NWScript.effectHeal(amount), oPC, 0.0f);
			}

			entity.setRegenerationTick(rate);
		}

		return entity;
	}

	private PlayerEntity HandleDiseaseTick(NWObject oPC, PlayerEntity entity)
	{
		return DiseaseSystem.RunDiseaseRemovalProcess(oPC, entity);
	}

	private PlayerEntity HandleFoodTick(NWObject oPC, PlayerEntity entity)
	{
		return FoodSystem.RunHungerCycle(oPC, entity);
	}

	private void RefreshActivePlayers()
	{
		int activePlayersTick = NWScript.getLocalInt(NWObject.MODULE, "ACTIVE_PLAYERS_TICK") + 1;

		if(activePlayersTick >= 10)
		{
			ActivePlayerRepository repo = new ActivePlayerRepository();
			List<ActivePlayerEntity> entities = new ArrayList<>();

			for(NWObject pc : NWScript.getPCs())
			{
				if(!NWScript.getIsDM(pc))
				{
					PlayerEntity playerEntity = playerRepo.getByUUID(new PlayerGO(pc).getUUID());
					int level = ProgressionSystem.GetPlayerLevel(pc);
					int expPercentage = (int) ((float) playerEntity.getExperience() / (float) ProgressionSystem.GetLevelExperienceRequired(level) * 100.0f);
					ActivePlayerEntity entity = new ActivePlayerEntity();
					entity.setAccountName(NWScript.getPCPlayerName(pc));
					entity.setCharacterName(NWScript.getName(pc, false));
					entity.setLevelPercentage((int) (((float) level / (float) ProgressionSystem.LevelCap) * 100.0f));
					entity.setLevel(level);
					entity.setExpPercentage(expPercentage);
					entity.setAreaName(NWScript.getName(NWScript.getArea(pc), false));
					entity.setCreateDate(new DateTime(DateTimeZone.UTC).toDate());
					entity.setDescription(NWScript.getDescription(pc, false, true));

					entities.add(entity);
				}
			}

			repo.Save(entities);
			activePlayersTick = 0;
		}

		NWScript.setLocalInt(NWObject.MODULE, "ACTIVE_PLAYERS_TICK", activePlayersTick);
	}
}

