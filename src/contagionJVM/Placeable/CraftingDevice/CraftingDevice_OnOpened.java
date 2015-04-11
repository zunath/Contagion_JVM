package contagionJVM.Placeable.CraftingDevice;

import contagionJVM.Entities.CraftBlueprintEntity;
import contagionJVM.IScriptEventHandler;
import contagionJVM.Repository.CraftRepository;
import contagionJVM.System.CraftSystem;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

@SuppressWarnings("unused")
public class CraftingDevice_OnOpened implements IScriptEventHandler {
    @Override
    public void runScript(NWObject device) {
        NWObject oPC = NWScript.getLastOpenedBy();
        int blueprintSelected = NWScript.getLocalInt(device, "CRAFT_BLUEPRINT_ID");

        NWScript.createItemOnObject("cft_choose_bp", device, 1, "");
        if(blueprintSelected > 0)
        {
            CraftRepository repo = new CraftRepository();
            CraftBlueprintEntity entity = repo.GetBlueprintByID(blueprintSelected);

            NWObject menuItem = NWScript.createItemOnObject("cft_craft_item", device, 1, "");
            NWScript.setName(menuItem, "Craft Item: " + entity.getItemName());

            NWScript.sendMessageToPC(oPC, CraftSystem.BuildBlueprintHeader(oPC, blueprintSelected));
        }

    }
}
