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
public class TreatmentKit implements IScriptEventHandler {
    @Override
    public void runScript(NWObject oPC) {
        NWObject oTarget = NWNX_Events.GetEventTarget();
        if(!NWScript.getIsPC(oTarget) || NWScript.getIsDM(oTarget)) return;

        PlayerGO pcGO = new PlayerGO(oTarget);
        NWObject oItem = NWNX_Events.GetEventItem();
        PlayerRepository repo = new PlayerRepository();
        PlayerEntity entity = repo.getByUUID(pcGO.getUUID());
        int skillLevel = ProgressionSystem.GetPlayerSkillLevel(oPC, ProgressionSystem.SkillType_FIRST_AID);

        if(entity.getCurrentInfection() <= 0)
        {
            NWScript.sendMessageToPC(oPC, "Your target is not infected. There would be no point to use this item.");
            return;
        }

        DiseaseSystem.DecreaseDiseaseLevel(oTarget, NWScript.random(7) + 1 + NWScript.random(skillLevel * 2));
        NWScript.destroyObject(oItem, 0.0f);

    }
}
