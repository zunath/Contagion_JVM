package contagionJVM.Event;
import contagionJVM.Constants;
import contagionJVM.Entities.PlayerEntity;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.IScriptEventHandler;
import contagionJVM.Repository.PlayerRepository;
import contagionJVM.System.DiseaseSystem;
import contagionJVM.System.FoodSystem;
import contagionJVM.System.ProgressionSystem;
import org.nwnx.nwnx2.jvm.*;
import org.nwnx.nwnx2.jvm.constants.DurationType;
import org.nwnx.nwnx2.jvm.constants.ObjectType;

import java.util.Objects;

@SuppressWarnings("unused")
public class Module_OnHeartbeat implements IScriptEventHandler {

	PlayerRepository repo;

	@Override
	public void runScript(NWObject objSelf) {

		repo = new PlayerRepository();

		NWObject[] players = NWScript.getPCs();
		for(NWObject pc : players)
		{
			if(!NWScript.getIsDM(pc))
			{
				PlayerGO pcGO = new PlayerGO(pc);
				PlayerEntity entity = repo.getByUUID(pcGO.getUUID());

				entity = HandleRegenerationTick(pc, entity);
				entity = HandleDiseaseTick(pc, entity);
				entity = HandleFoodTick(pc, entity);
				HandleBleedingTick(pc);

				repo.save(entity);
			}
		}

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

	private void HandleBleedingTick(NWObject oPC)
	{
		NWObject oArea = NWScript.getArea(oPC);
		String sTag = NWScript.getTag(oArea);

		if(NWScript.getIsDead(oPC) || Objects.equals(sTag, "ooc_entry") || Objects.equals(sTag, "WelcometoHeaven") || Objects.equals(sTag, "WelcometoHell") || Objects.equals(sTag, "WelcometoLimbo")) return;

		int iCurrentHP = NWScript.getCurrentHitPoints(oPC);
		int iMaxHP = NWScript.getMaxHitPoints(oPC);
		NWLocation lLocation = NWScript.getLocation(oPC);

		if(iCurrentHP <= (iMaxHP * 0.3))
		{
			NWObject oBlood = NWScript.createObject(ObjectType.PLACEABLE, "zep_bloodstain7", lLocation, false, "");
			NWScript.destroyObject(oBlood, 48.0f);
		}
	}
}

