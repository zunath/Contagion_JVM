package contagionJVM.Placeable.StructureSystem;

import contagionJVM.Dialog.DialogManager;
import contagionJVM.Entities.PCTerritoryFlagEntity;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.IScriptEventHandler;
import contagionJVM.Repository.StructureRepository;
import contagionJVM.System.StructureSystem;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

@SuppressWarnings("unused")
public class TerritoryFlag_OnUsed implements IScriptEventHandler {
    @Override
    public void runScript(NWObject site) {
        NWObject oPC = NWScript.getLastUsedBy();
        PlayerGO pcGO = new PlayerGO(oPC);
        StructureRepository repo = new StructureRepository();
        int flagID = StructureSystem.GetTerritoryFlagID(site);
        PCTerritoryFlagEntity entity = repo.GetPCTerritoryFlagByID(flagID);

        if(!pcGO.getUUID().equals(entity.getPlayerID()) && !NWScript.getIsDM(oPC))
        {
            NWScript.sendMessageToPC(oPC, "Only the owner of the territory may use this.");
        }
        else
        {
            DialogManager.startConversation(oPC, site, "TerritoryFlag");
        }
    }
}
