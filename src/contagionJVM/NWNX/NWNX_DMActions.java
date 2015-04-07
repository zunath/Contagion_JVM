package contagionJVM.NWNX;

import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.NWVector;

public class NWNX_DMActions {
    
    public static void SetDMActionScript(int nAction, String sScript) {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!DMACTIONS!SET_ACTION_SCRIPT", nAction + ":" + sScript);
        NWScript.deleteLocalString(NWObject.MODULE, "NWNX!DMACTIONS!SET_ACTION_SCRIPT");
    }

    public static int nGetDMActionID() {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!DMACTIONS!GETACTIONID", "                ");
        String sAction = NWScript.getLocalString(NWObject.MODULE, "NWNX!DMACTIONS!GETACTIONID");
        NWScript.deleteLocalString(NWObject.MODULE, "NWNX!DMACTIONS!GETACTIONID");
        return NWScript.stringToInt(sAction);
    }

    public static void PreventDMAction() {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!DMACTIONS!PREVENT", "1");
        NWScript.deleteLocalString(NWObject.MODULE, "NWNX!DMACTIONS!PREVENT");
    }

    public static int nGetDMAction_Param(boolean bSecond) {
        String sNth = bSecond?"2":"1";
        NWScript.setLocalString(NWObject.MODULE, "NWNX!DMACTIONS!GETPARAM_" + sNth, "                ");
        String sVal = NWScript.getLocalString(NWObject.MODULE, "NWNX!DMACTIONS!GETPARAM_" + sNth);
        NWScript.deleteLocalString(NWObject.MODULE, "NWNX!DMACTIONS!GETPARAM_" + sNth);
        return NWScript.stringToInt(sVal);
    }

    public static String sGetDMAction_Param() {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!DMACTIONS!GETSTRPARAM1", "                                ");
        String sVal = NWScript.getLocalString(NWObject.MODULE, "NWNX!DMACTIONS!GETSTRPARAM1");
        NWScript.deleteLocalString(NWObject.MODULE, "NWNX!DMACTIONS!GETSTRPARAM1");
        return sVal;
    }

    public static NWObject oGetDMAction_Target(boolean bSecond) {
        String sNth = bSecond?"2":"1";
        return NWScript.getLocalObject(NWObject.MODULE, "NWNX!DMACTIONS!TARGET_" + sNth);
    }

    public static NWVector vGetDMAction_Position() {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!DMACTIONS!GETPOS", "                                              ");
        String sVector = NWScript.getLocalString(NWObject.MODULE, "NWNX!DMACTIONS!GETPOS");
        NWScript.deleteLocalString(NWObject.MODULE, "NWNX!DMACTIONS!GETPOS");
        float x, y, z;

        //Get X
        int nPos = NWScript.findSubString(sVector, "¬", 0);
        if(nPos == -1) return NWScript.vector(0.0f, 0.0f, 0.0f);
        x = NWScript.stringToFloat(NWScript.getStringLeft(sVector, nPos));
        sVector = NWScript.getStringRight(sVector, NWScript.getStringLength(sVector) - nPos - 1);

        //Get Y
        nPos = NWScript.findSubString(sVector, "¬", 0);
        if(nPos == -1) return NWScript.vector(0.0f, 0.0f, 0.0f);
        y = NWScript.stringToFloat(NWScript.getStringLeft(sVector, nPos));
        sVector = NWScript.getStringRight(sVector, NWScript.getStringLength(sVector) - nPos - 1);

        //Get Z
        nPos = NWScript.findSubString(sVector, "¬", 0);
        if(nPos == -1) {
            z = NWScript.stringToFloat(sVector);
        } else return NWScript.vector(0.0f, 0.0f, 0.0f);

        return NWScript.vector(x, y, z);
    }

    public static int nGetDMAction_TargetsCount() {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!DMACTIONS!GETTARGETSCOUNT", "                ");
        String sVal = NWScript.getLocalString(NWObject.MODULE, "NWNX!DMACTIONS!GETTARGETSCOUNT");
        NWScript.deleteLocalString(NWObject.MODULE, "NWNX!DMACTIONS!GETTARGETSCOUNT");
        return NWScript.stringToInt(sVal);
    }

    public static int nGetDMAction_TargetsCurrent() {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!DMACTIONS!GETTARGETSCURRENT", "                ");
        String sVal = NWScript.getLocalString(NWObject.MODULE, "NWNX!DMACTIONS!GETTARGETSCURRENT");
        NWScript.deleteLocalString(NWObject.MODULE, "NWNX!DMACTIONS!GETTARGETSCURRENT");
        return NWScript.stringToInt(sVal);
    }


}
