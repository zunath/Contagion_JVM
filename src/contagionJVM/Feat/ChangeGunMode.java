package contagionJVM.Feat;

import contagionJVM.IScriptEventHandler;
import contagionJVM.NWNX.NWNX_Events;
import contagionJVM.System.CombatSystem;
import org.nwnx.nwnx2.jvm.NWObject;

@SuppressWarnings("UnusedDeclaration")
public class ChangeGunMode implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        CombatSystem combatSystem = new CombatSystem();
        NWObject oItem = NWNX_Events.GetEventItem();
        combatSystem.ChangeWeaponMode(oItem);
    }
}
