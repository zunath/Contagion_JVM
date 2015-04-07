package contagionJVM.Placeable.StructureSystem;

import contagionJVM.Entities.PCTerritoryFlagStructureEntity;
import contagionJVM.Entities.PCTerritoryFlagStructureItemEntity;
import contagionJVM.Helper.ColorToken;
import contagionJVM.IScriptEventHandler;
import contagionJVM.Repository.StructureRepository;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.SCORCO;
import org.nwnx.nwnx2.jvm.Scheduler;

@SuppressWarnings("unused")
public class PersistentStorage_OnDisturbed implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        final NWObject oPC = NWScript.getLastDisturbed();
        final NWObject item = NWScript.getInventoryDisturbItem();
        int structureID = NWScript.getLocalInt(objSelf, "STRUCTURE_TEMP_STRUCTURE_ID");
        StructureRepository repo = new StructureRepository();
        PCTerritoryFlagStructureEntity entity = repo.GetPCStructureByID(structureID);
        int itemCount = 0;
        boolean reachedLimit = false;

        entity.getItems().clear();
        for(final NWObject inventory : NWScript.getItemsInInventory(objSelf))
        {
            itemCount++;

            if(itemCount <= entity.getBlueprint().getItemStorageCount())
            {
                PCTerritoryFlagStructureItemEntity itemEntity = new PCTerritoryFlagStructureItemEntity();
                itemEntity.setItemName(NWScript.getName(inventory, false));
                itemEntity.setItemObject(SCORCO.saveObject(inventory));
                itemEntity.setItemResref(NWScript.getResRef(inventory));
                itemEntity.setItemTag((NWScript.getTag(inventory)));
                itemEntity.setStructure(entity);

                entity.getItems().add(itemEntity);
            }
            else
            {
                reachedLimit = true;

                Scheduler.assign(objSelf, new Runnable() {
                    @Override
                    public void run() {
                        NWScript.actionGiveItem(inventory, oPC);
                    }
                });
            }
        }

        if(reachedLimit)
        {
            NWScript.sendMessageToPC(oPC, ColorToken.Red() + "No more items can be placed inside." + ColorToken.End());
        }
        else
        {
            NWScript.sendMessageToPC(oPC, ColorToken.White() + "Item Limit: "  + itemCount + " / " + ColorToken.End() + ColorToken.Red() + entity.getBlueprint().getItemStorageCount() + ColorToken.End());
        }

        repo.Save(entity);
    }
}
