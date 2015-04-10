package contagionJVM.Item.Medical;

import contagionJVM.Entities.PlayerEntity;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.IScriptEventHandler;
import contagionJVM.NWNX.NWNX_Events;
import contagionJVM.Repository.PlayerRepository;
import contagionJVM.System.DiseaseSystem;
import contagionJVM.System.ProgressionSystem;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

@SuppressWarnings("unused")
public class HerbalRemedy implements IScriptEventHandler {
    @Override
    public void runScript(NWObject oPC) {
        if(!NWScript.getIsPC(oPC) || NWScript.getIsDM(oPC)) return;

        PlayerGO pcGO = new PlayerGO(oPC);
        NWObject oItem = NWNX_Events.GetEventItem();
        PlayerRepository repo = new PlayerRepository();
        PlayerEntity entity = repo.getByUUID(pcGO.getUUID());
        int skillLevel = ProgressionSystem.GetPlayerSkillLevel(oPC, ProgressionSystem.SkillType_FIRST_AID);

        if(entity.getCurrentInfection() <= 0)
        {
            NWScript.sendMessageToPC(oPC, "You are not infected. There would be no point to use this item.");
            return;
        }

        DiseaseSystem.DecreaseDiseaseLevel(oPC, NWScript.random(10) + 1 + NWScript.random(skillLevel * 2));
        NWScript.destroyObject(oItem, 0.0f);
    }
}
