package contagionJVM.Placeable.StructureSystem;

import contagionJVM.Dialog.DialogManager;
import contagionJVM.Entities.PCTerritoryFlagStructureEntity;
import contagionJVM.Enumerations.StructurePermission;
import contagionJVM.IScriptEventHandler;
import contagionJVM.Repository.StructureRepository;
import contagionJVM.System.StructureSystem;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

@SuppressWarnings("unused")
public class PersistentStorage_OnUsed implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        NWObject oPC = NWScript.getLastUsedBy();
        int structureID = StructureSystem.GetPlaceableStructureID(objSelf);
        StructureRepository repo = new StructureRepository();
        PCTerritoryFlagStructureEntity structure = repo.GetPCStructureByID(structureID);

        if(StructureSystem.PlayerHasPermission(oPC, StructurePermission.CanAccessPersistentStorage, structure.getPcTerritoryFlag().getPcTerritoryFlagID()))
        {
            DialogManager.startConversation(oPC, objSelf, "StructureStorage");
        }
        else
        {
            NWScript.floatingTextStringOnCreature("You do not have permission to access this structure.", oPC, false);
        }

    }
}
