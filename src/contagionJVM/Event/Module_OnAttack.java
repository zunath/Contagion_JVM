package contagionJVM.Event;

import contagionJVM.Bioware.Position;
import contagionJVM.GameObject.GunGO;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.Helper.ColorToken;
import contagionJVM.IScriptEventHandler;
import contagionJVM.NWNX.NWNX_Events;
import contagionJVM.System.CombatSystem;
import org.nwnx.nwnx2.jvm.*;
import org.nwnx.nwnx2.jvm.constants.Action;
import org.nwnx.nwnx2.jvm.constants.ActionMode;
import org.nwnx.nwnx2.jvm.constants.EffectType;
import org.nwnx.nwnx2.jvm.constants.InventorySlot;

@SuppressWarnings("UnusedDeclaration")
public class Module_OnAttack implements IScriptEventHandler {

    @Override
    public void runScript(final NWObject oAttacker) {
        CombatSystem system = new CombatSystem();
        system.OnModuleAttack(oAttacker);
    }
}
