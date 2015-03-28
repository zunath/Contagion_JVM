package contagionJVM.System;

import contagionJVM.Entities.PCKeyItemEntity;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.Repository.KeyItemRepository;
import org.nwnx.nwnx2.jvm.NWObject;

public class KeyItemSystem {

    public static boolean PlayerHasKeyItem(NWObject oPC, int keyItemID)
    {
        PlayerGO pcGO = new PlayerGO(oPC);
        KeyItemRepository repo = new KeyItemRepository();
        PCKeyItemEntity entity = repo.GetPCKeyItemByKeyItemID(pcGO.getUUID(), keyItemID);
        return entity != null;
    }


    public static void GivePlayerKeyItem(NWObject oPC, int keyItemID)
    {
        if(!PlayerHasKeyItem(oPC, keyItemID))
        {
            PlayerGO pcGO = new PlayerGO(oPC);

            KeyItemRepository repo = new KeyItemRepository();
            PCKeyItemEntity entity = new PCKeyItemEntity();
            entity.setPlayerID(pcGO.getUUID());
            entity.setKeyItemID(keyItemID);
            repo.Save(entity);
        }
    }


}
