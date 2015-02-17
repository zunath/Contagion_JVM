package contagionJVM.NWNX;

import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

@SuppressWarnings("UnusedDeclaration")
public class NWNX_Names {

    // Call this function on ClientEnter event
    public static void InitPlayerNameList(NWObject oObject, int nUnknownStyle) {
        NWScript.setLocalString(oObject, "NWNX!NAMES!INITPLAYERNAMELIST", NWScript.intToString(nUnknownStyle));
    }

    // Call SetNamesEnabled after initializing the name list to activate the system for the player
    // SetNamesEnabled(oPC, 0) can be used to exclude DMs from names replacement
    // and always show them the true names
    public static void SetNamesEnabled(NWObject oPlayer, int bEnabled) {
        if (!NWScript.getIsPC(oPlayer)) return;
        NWScript.setLocalString(oPlayer, "NWNX!NAMES!SETNAMESENABLED", NWScript.intToString(bEnabled));
    }

    public static String GetDynamicName(NWObject oPlayer, NWObject oObject) {
        NWScript.setLocalString(oPlayer, "NWNX!NAMES!GETDYNAMICNAME", Integer.toHexString(oObject.getObjectId()));
        return NWScript.getLocalString(oPlayer, "NWNX!NAMES!GETDYNAMICNAME");
    }

    public static void SetDynamicName(NWObject oPlayer, NWObject oObject, String sName) {
        NWScript.setLocalString(oPlayer, "NWNX!NAMES!SETDYNAMICNAME", Integer.toHexString(oObject.getObjectId()) + "¬" + sName);
    }

    //Force the name to update on the client
    public static void UpdateDynamicName(NWObject oPlayer, NWObject oObject) {
        if (!NWScript.getIsObjectValid(oPlayer) || !NWScript.getIsObjectValid(oObject)) return;
        NWScript.setLocalString(oPlayer, "NWNX!NAMES!UPDATEDYNAMICNAME", Integer.toHexString(oObject.getObjectId()));
    }

    // Force the whole player list to update
    // Use on DMs every time a new player enters
    public static void UpdatePlayerList(NWObject oPlayer) {
        if (!NWScript.getIsObjectValid(oPlayer)) return;
        NWScript.setLocalString(oPlayer, "NWNX!NAMES!UPDATEPLAYERLIST", " ");
    }

    public static void DeleteDynamicName(NWObject oPlayer, NWObject oObject) {
        NWScript.setLocalString(oPlayer, "NWNX!NAMES!DELETEDYNAMICNAME", Integer.toHexString(oObject.getObjectId()));
    }

    // Do not use this
    private static void ClearPlayerNameList(NWObject oPlayer) {
        NWScript.setLocalString(oPlayer, "NWNX!NAMES!CLEARPLAYERNAMELIST", " ");
    }
}
