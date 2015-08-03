package contagionJVM.Item.Medical;

import contagionJVM.Entities.PlayerEntity;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.Helper.ItemHelper;
import contagionJVM.IScriptEventHandler;
import contagionJVM.NWNX.NWNX_Events;
import contagionJVM.NWNX.NWNX_Funcs;
import contagionJVM.Repository.PlayerRepository;
import contagionJVM.System.DiseaseSystem;
import contagionJVM.System.ProgressionSystem;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.Scheduler;
import org.nwnx.nwnx2.jvm.constants.Animation;

import java.util.Random;

@SuppressWarnings("unused")
public class TreatmentKit implements IScriptEventHandler {
    @Override
    public void runScript(final NWObject oPC) {
        final NWObject target = NWNX_Events.GetEventTarget();
        final PlayerGO pcGO = new PlayerGO(oPC);

        if(pcGO.isBusy())
        {
            NWScript.sendMessageToPC(oPC, "You are busy.");
            return;
        }

        PlayerGO targetGO = new PlayerGO(target);
        NWObject oItem = NWNX_Events.GetEventItem();
        PlayerRepository repo = new PlayerRepository();
        PlayerEntity entity = repo.getByUUID(targetGO.getUUID());

        if(!NWScript.getIsPC(target) || NWScript.getIsDM(target) || entity.getCurrentInfection() <= 0)
        {
            NWScript.sendMessageToPC(oPC, "Only infected players may be targeted with this item.");
            return;
        }

        float distance = NWScript.getDistanceBetween(oPC, target);
        if(distance > 3.5f)
        {
            NWScript.sendMessageToPC(oPC, "Your target is too far away.");
            return;
        }

        Random random = new Random();
        final NWObject item = NWNX_Events.GetEventItem();
        int skill = ProgressionSystem.GetPlayerSkillLevel(oPC, ProgressionSystem.SkillType_FIRST_AID);
        final float delay = 8.0f - (skill * 0.5f);
        int baseAmount = 1 + NWScript.getLocalInt(item, "ENHANCED_AMOUNT") + (skill > 0 ? random.nextInt(skill) : 0);
        final int restoreAmount = baseAmount + random.nextInt(7);

        NWNX_Funcs.StartTimingBar(oPC, (int) delay, "");

        Scheduler.assign(oPC, new Runnable() {
            @Override
            public void run() {
                pcGO.setIsBusy(true);
                NWScript.setFacingPoint(NWScript.getPosition(target));
                NWScript.actionPlayAnimation(Animation.LOOPING_GET_MID, 1.0f, delay);
                NWScript.setCommandable(false, oPC);
            }
        });

        Scheduler.delay(oPC, (int) (delay * 1000), new Runnable() {
            @Override
            public void run() {
                pcGO.setIsBusy(false);
                NWScript.setCommandable(true, oPC);
                float distance = NWScript.getDistanceBetween(oPC, target);

                if (distance > 3.5f) {
                    NWScript.sendMessageToPC(oPC, "Your target is too far away.");
                    return;
                }

                DiseaseSystem.DecreaseDiseaseLevel(target, restoreAmount);
                ItemHelper.ReduceItemStack(item);
            }
        });
    }
}
