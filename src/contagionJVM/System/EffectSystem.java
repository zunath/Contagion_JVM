package contagionJVM.System;

import contagionJVM.GameObject.PlayerGO;
import org.nwnx.nwnx2.jvm.NWObject;

public class EffectSystem {

    public static void OnPlayerHeartbeat(NWObject oPC)
    {
        PlayerGO pcGO = new PlayerGO(oPC);


    }

}
