package contagionJVM.Creature.Zombie;

import contagionJVM.IScriptEventHandler;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

@SuppressWarnings("unused")
public class Zombie_OnRoundEnd implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        NWScript.executeScript("nw_c2_default3", objSelf);
    }
}
