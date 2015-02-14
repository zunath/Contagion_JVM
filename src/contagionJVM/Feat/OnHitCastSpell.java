package contagionJVM.Feat;

import contagionJVM.IScriptEventHandler;
import contagionJVM.System.DurabilitySystem;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.constants.BaseItem;
import org.nwnx.nwnx2.jvm.constants.InventorySlot;

@SuppressWarnings("UnusedDeclaration")
public class OnHitCastSpell implements IScriptEventHandler {
    @Override
    public void runScript(NWObject oItem) {
        NWObject oSpellOrigin = NWScript.getSpellCastItem();

        if(!oItem.equals(NWObject.INVALID) && NWScript.getBaseItemType(oItem) == BaseItem.ARMOR)
        {
            NWObject oBelt = NWScript.getItemInSlot(InventorySlot.BELT, oSpellOrigin);
            if(!oBelt.equals(NWObject.INVALID))
            {
                DurabilitySystem.RunItemDecay(oSpellOrigin, oBelt, 3, NWScript.random(2), true);
            }
        }
    }
}
