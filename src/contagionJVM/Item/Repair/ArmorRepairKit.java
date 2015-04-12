package contagionJVM.Item.Repair;

import contagionJVM.IScriptEventHandler;
import contagionJVM.NWNX.NWNX_Events;
import contagionJVM.System.DurabilitySystem;
import contagionJVM.System.ProgressionSystem;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.constants.BaseItem;

import java.util.Random;

@SuppressWarnings("unused")
public class ArmorRepairKit implements IScriptEventHandler {
    @Override
    public void runScript(NWObject oPC) {
        NWObject target = NWNX_Events.GetEventTarget();
        NWObject item = NWNX_Events.GetEventItem();
        int targetType = NWScript.getBaseItemType(target);

        if(targetType != BaseItem.BELT)
        {
            NWScript.sendMessageToPC(oPC, "You cannot repair that item with this kit.");
            return;
        }

        int skill = ProgressionSystem.GetPlayerSkillLevel(oPC, ProgressionSystem.SkillType_ITEM_REPAIR) * 2;
        Random random = new Random();
        int repairAmount = random.nextInt(20) + skill + 5;
        DurabilitySystem.RunItemRepair(oPC, item, repairAmount);

        NWScript.destroyObject(item, 0.0f);
    }
}
