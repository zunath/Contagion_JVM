package contagionJVM.NWNX;

import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

public class NWNX_TMI {

    public static int GetTMILimit ()
    {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!TMI!GETLIMIT", "          ");
        return NWScript.stringToInt(NWScript.getLocalString(NWObject.MODULE, "NWNX!TMI!GETLIMIT"));
    }

    public static void SetTMILimit (int nLimit)
    {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!TMI!SETLIMIT", NWScript.intToString(nLimit));
    }
}
