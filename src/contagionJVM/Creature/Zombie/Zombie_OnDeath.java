package contagionJVM.Creature.Zombie;

import contagionJVM.Entities.PlayerEntity;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.IScriptEventHandler;
import contagionJVM.Repository.PlayerRepository;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

@SuppressWarnings("unused")
public class Zombie_OnDeath implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        NWScript.executeScript("nw_c2_default7", objSelf);
        NWScript.setIsDestroyable(false, true, false);
        NWScript.executeScript("gb_loot_corpse", objSelf);
        IncrementKillCount();
    }

    private void IncrementKillCount()
    {
        NWObject oPC = NWScript.getLastKiller();
        if(!NWScript.getIsPC(oPC) || NWScript.getIsDM(oPC)) return;

        PlayerGO pcGO = new PlayerGO(oPC);
        PlayerRepository repo = new PlayerRepository();
        PlayerEntity entity = repo.getByUUID(pcGO.getUUID());

        if(entity != null)
        {
            entity.setZombieKillCount(entity.getZombieKillCount() + 1);
            repo.save(entity);
        }
    }

}
