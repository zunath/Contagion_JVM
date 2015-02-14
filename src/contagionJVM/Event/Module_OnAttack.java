package contagionJVM.Event;

import contagionJVM.IScriptEventHandler;
import contagionJVM.System.CombatSystem;
import org.nwnx.nwnx2.jvm.*;

@SuppressWarnings("UnusedDeclaration")
public class Module_OnAttack implements IScriptEventHandler {

    @Override
    public void runScript(final NWObject oAttacker) {
        CombatSystem system = new CombatSystem();
        system.OnModuleAttack(oAttacker);
    }
}
