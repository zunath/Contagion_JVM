package contagionJVM.Feat;

import contagionJVM.IScriptEventHandler;
import contagionJVM.NWNX.NWNX_Events;
import contagionJVM.System.CombatSystem;
import org.nwnx.nwnx2.jvm.NWObject;

@SuppressWarnings("UnusedDeclaration")
public class ChangeGunAmmo implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        CombatSystem combatSystem = new CombatSystem();
        NWObject oWeapon = NWNX_Events.GetEventItem();
        combatSystem.ChangeGunAmmoPriority(oWeapon);
    }
}
