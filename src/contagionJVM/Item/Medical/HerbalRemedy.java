package contagionJVM.Item.Medical;

import contagionJVM.Entities.PlayerEntity;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.Helper.ItemHelper;
import contagionJVM.IScriptEventHandler;
import contagionJVM.NWNX.NWNX_Events;
import contagionJVM.Repository.PlayerRepository;
import contagionJVM.System.DiseaseSystem;
import contagionJVM.System.ProgressionSystem;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.Scheduler;
import org.nwnx.nwnx2.jvm.constants.Animation;

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

        Scheduler.assign(oPC, new Runnable() {
            @Override
            public void run() {
                NWScript.actionPlayAnimation(Animation.FIREFORGET_SALUTE, 1.0f, 0.0f);
            }
        });

        DiseaseSystem.DecreaseDiseaseLevel(oPC, NWScript.random(10) + 1 + NWScript.random(skillLevel * 2));
        ItemHelper.ReduceItemStack(oItem);
    }
}
