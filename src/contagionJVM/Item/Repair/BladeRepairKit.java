package contagionJVM.Item.Repair;

import contagionJVM.IScriptEventHandler;
import contagionJVM.NWNX.NWNX_Events;
import contagionJVM.System.DurabilitySystem;
import contagionJVM.System.ProgressionSystem;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.constants.BaseItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings("unused")
public class BladeRepairKit implements IScriptEventHandler {
    @Override
    public void runScript(NWObject oPC) {
        NWObject target = NWNX_Events.GetEventTarget();
        NWObject item = NWNX_Events.GetEventItem();
        int targetType = NWScript.getBaseItemType(target);
        List<Integer> validTypes = BuildValidTypes();

        if(!validTypes.contains(targetType))
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


    private List<Integer> BuildValidTypes()
    {
        ArrayList<Integer> validTypes = new ArrayList<>();
        validTypes.add(BaseItem.BASTARDSWORD);
        validTypes.add(BaseItem.BATTLEAXE);
        validTypes.add(BaseItem.DAGGER);
        validTypes.add(BaseItem.DOUBLEAXE);
        validTypes.add(BaseItem.DWARVENWARAXE);
        validTypes.add(BaseItem.GREATAXE);
        validTypes.add(BaseItem.GREATSWORD);
        validTypes.add(BaseItem.HALBERD);
        validTypes.add(BaseItem.HANDAXE);
        validTypes.add(BaseItem.KATANA);
        validTypes.add(BaseItem.KUKRI);
        validTypes.add(BaseItem.LONGSWORD);
        validTypes.add(BaseItem.RAPIER);
        validTypes.add(BaseItem.SCIMITAR);
        validTypes.add(BaseItem.SCYTHE);
        validTypes.add(BaseItem.SHORTSWORD);
        validTypes.add(BaseItem.TWOBLADEDSWORD);

        return validTypes;
    }

}
