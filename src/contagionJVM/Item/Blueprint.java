package contagionJVM.Item;

import contagionJVM.GameObject.PlayerGO;
import contagionJVM.IScriptEventHandler;
import contagionJVM.NWNX.NWNX_Events;
import contagionJVM.Repository.CraftRepository;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

@SuppressWarnings("unused")
public class Blueprint implements IScriptEventHandler {
    @Override
    public void runScript(NWObject oPC) {
        PlayerGO pcGO = new PlayerGO(oPC);
        NWObject oItem = NWNX_Events.GetEventItem();
        int blueprintID = NWScript.getLocalInt(oItem, "CRAFT_BLUEPRINT_ID");

        if(blueprintID <= 0)
        {
            NWScript.sendMessageToPC(oPC, "Unable to add blueprint.");
            return;
        }

        CraftRepository repo = new CraftRepository();
        boolean success = repo.AddBlueprintToPC(pcGO.getUUID(), blueprintID);

        if(success)
        {
            NWScript.destroyObject(oItem, 0.0f);
            NWScript.sendMessageToPC(oPC, "You add the blueprint to your collection.");
        }
        else
        {
            NWScript.sendMessageToPC(oPC, "You already added that blueprint to your collection.");
        }

    }
}
