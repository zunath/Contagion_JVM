package contagionJVM.Placeable.OverflowStorage;

import contagionJVM.Entities.PCOverflowItemEntity;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.IScriptEventHandler;
import contagionJVM.Repository.OverflowItemRepository;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.SCORCO;

import java.util.List;

@SuppressWarnings("unused")
public class OverflowStorage_OnOpened implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        NWObject oPC = NWScript.getLastOpenedBy();
        PlayerGO pcGO = new PlayerGO(oPC);
        OverflowItemRepository repo = new OverflowItemRepository();
        List<PCOverflowItemEntity> items = repo.GetAllByPlayerID(pcGO.getUUID());

        for(PCOverflowItemEntity item : items)
        {
            NWObject oItem = SCORCO.loadObject(item.getItemObject(), NWScript.getLocation(objSelf), objSelf);
            NWScript.setLocalInt(oItem, "TEMP_OVERFLOW_ITEM_ID", item.getPcOverflowItemID());
        }

        NWScript.setUseableFlag(objSelf, false);
    }
}
