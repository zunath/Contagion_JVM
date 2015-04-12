package contagionJVM.Helper;

import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

public class EffectHelper {

    // Direct calls to NWNX_Structs's SetEffect* methods don't work from Java.
    // For this reason, I've had to make a script that calls the methods inside NWScript.
    public static void ApplyEffectIcon(NWObject oTarget, int iconID, float duration)
    {
        NWScript.setLocalInt(oTarget, "jvm_effectdirect_iconid", iconID);
        NWScript.setLocalFloat(oTarget, "jvm_effectdirect_duration", duration);
        NWScript.executeScript("jvm_effectdirect", oTarget);
    }
}
