package contagionJVM.NWNX;

import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.NWVector;
import org.nwnx.nwnx2.jvm.constants.Gender;

public class NWNX_Events {
    final int EVENT_TYPE_ALL                = 0;
    final int EVENT_TYPE_SAVE_CHAR          = 1;
    final int EVENT_TYPE_PICKPOCKET         = 2;
    final int EVENT_TYPE_ATTACK             = 3;
    final int EVENT_TYPE_USE_ITEM           = 4;
    final int EVENT_TYPE_QUICKCHAT          = 5;
    final int EVENT_TYPE_EXAMINE            = 6;
    final int EVENT_TYPE_USE_SKILL          = 7;
    final int EVENT_TYPE_USE_FEAT           = 8;
    final int EVENT_TYPE_TOGGLE_MODE        = 9;
    final int EVENT_TYPE_CAST_SPELL         = 10;
    final int EVENT_TYPE_TOGGLE_PAUSE       = 11;
    final int EVENT_TYPE_POSSESS_FAMILIAR   = 12;
    final int EVENT_TYPE_VALIDATE_CHARACTER = 13;
    final int EVENT_TYPE_DESTROY_OBJECT     = 14;

// DEPRECATED
// For backwards compatibility only - use above constants instead
    final int EVENT_SAVE_CHAR = 1;
    final int EVENT_PICKPOCKET = 2;
    final int EVENT_ATTACK = 3;
    final int EVENT_USE_ITEM = 4;
    final int EVENT_QUICKCHAT = 5;
    final int EVENT_EXAMINE = 6;
    final int EVENT_USE_SKILL = 7;
    final int EVENT_USE_FEAT = 8;
    final int EVENT_TOGGLE_MODE = 9;

    final int NODE_TYPE_STARTING_NODE = 0;
    final int NODE_TYPE_ENTRY_NODE    = 1;
    final int NODE_TYPE_REPLY_NODE    = 2;

    final int LANGUAGE_ENGLISH              = 0;
    final int LANGUAGE_FRENCH               = 1;
    final int LANGUAGE_GERMAN               = 2;
    final int LANGUAGE_ITALIAN              = 3;
    final int LANGUAGE_SPANISH              = 4;
    final int LANGUAGE_POLISH               = 5;
    final int LANGUAGE_KOREAN               = 128;
    final int LANGUAGE_CHINESE_TRADITIONAL  = 129;
    final int LANGUAGE_CHINESE_SIMPLIFIED   = 130;
    final int LANGUAGE_JAPANESE             = 131;

    public static int GetEventType()
    {   
        NWScript.setLocalString(NWObject.MODULE, "NWNX!EVENTS!GET_EVENT_ID", "      ");
        return NWScript.stringToInt(NWScript.getLocalString(NWObject.MODULE, "NWNX!EVENTS!GET_EVENT_ID"));
    }

    public static int GetEventSubType()
    {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!EVENTS!GET_EVENT_SUBID", "      ");
        return NWScript.stringToInt(NWScript.getLocalString(NWObject.MODULE, "NWNX!EVENTS!GET_EVENT_SUBID"));
    }

    public static NWObject GetEventTarget()
    {
        return NWScript.getLocalObject(NWObject.MODULE, "NWNX!EVENTS!TARGET");
    }

    // DEPRECATED
// For backwards compatibility only - use GetEventTarget instead
    NWObject GetActionTarget()
    {
        return GetEventTarget();
    }

    public static NWObject GetEventItem()
    {
        return NWScript.getLocalObject(NWObject.MODULE, "NWNX!EVENTS!ITEM");
    }

    public static NWVector GetEventPosition()
    {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!EVENTS!GET_EVENT_POSITION", "                                              ");
        String sVector = NWScript.getLocalString(NWObject.MODULE, "NWNX!EVENTS!GET_EVENT_POSITION");
        float x, y, z;

        //Get X
        int nPos = NWScript.findSubString(sVector, "¬", 0);
        if(nPos == -1) return new NWVector(0.0f, 0.0f, 0.0f);
        x = NWScript.stringToFloat(NWScript.getStringLeft(sVector, nPos));
        sVector = NWScript.getStringRight(sVector, NWScript.getStringLength(sVector) - nPos - 1);

        //Get Y
        nPos = NWScript.findSubString(sVector, "¬", 0);
        if(nPos == -1) return new NWVector(0.0f, 0.0f, 0.0f);
        y = NWScript.stringToFloat(NWScript.getStringLeft(sVector, nPos));
        sVector = NWScript.getStringRight(sVector, NWScript.getStringLength(sVector) - nPos - 1);

        //Get Z
        nPos = NWScript.findSubString(sVector, "¬", 0);
        if(nPos == -1)
        {
            z = NWScript.stringToFloat(sVector);
        }
        else return new NWVector(0.0f, 0.0f, 0.0f);
        return new NWVector(x, y, z);
    }

    public static void BypassEvent()
    {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!EVENTS!BYPASS", "1");
    }

    public static void SetReturnValue(int nRetVal)
    {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!EVENTS!RETURN", NWScript.intToString(nRetVal));
    }

    public static void SetGlobalEventHandler(int nEventID, String sHandler)
    {
        if (sHandler == "")
            sHandler = "-";

        String sKey = "NWNX!EVENTS!SET_EVENT_HANDLER_" + NWScript.intToString(nEventID);
        NWScript.setLocalString(NWObject.MODULE, sKey, sHandler);
        NWScript.deleteLocalString(NWObject.MODULE, sKey);
    }

    public static int GetCurrentNodeType()
    {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!EVENTS!GET_NODE_TYPE", "      ");
        return NWScript.stringToInt(NWScript.getLocalString(NWObject.MODULE, "NWNX!EVENTS!GET_NODE_TYPE"));
    }

    public static int GetCurrentNodeID()
    {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!EVENTS!GET_NODE_ID", "      ");
        return NWScript.stringToInt(NWScript.getLocalString(NWObject.MODULE, "NWNX!EVENTS!GET_NODE_ID"));
    }

    public static int GetCurrentAbsoluteNodeID()
    {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!EVENTS!GET_ABSOLUTE_NODE_ID", "      ");
        return NWScript.stringToInt(NWScript.getLocalString(NWObject.MODULE, "NWNX!EVENTS!GET_ABSOLUTE_NODE_ID"));
    }

    public static int GetSelectedNodeID()
    {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!EVENTS!GET_SELECTED_NODE_ID", "      ");
        return NWScript.stringToInt(NWScript.getLocalString(NWObject.MODULE, "NWNX!EVENTS!GET_SELECTED_NODE_ID"));
    }

    public static int GetSelectedAbsoluteNodeID()
    {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!EVENTS!GET_SELECTED_ABSOLUTE_NODE_ID", "      ");
        return NWScript.stringToInt(NWScript.getLocalString(NWObject.MODULE, "NWNX!EVENTS!GET_SELECTED_ABSOLUTE_NODE_ID"));
    }

    public static String GetSelectedNodeText(int nLangID, int nGender)
    {
        if (nGender != Gender.FEMALE) nGender = Gender.MALE;
        NWScript.setLocalString(NWObject.MODULE, "NWNX!EVENTS!GET_SELECTED_NODE_TEXT", NWScript.intToString(nLangID * 2 + nGender));
        return NWScript.getLocalString(NWObject.MODULE, "NWNX!EVENTS!GET_SELECTED_NODE_TEXT");
    }

    public static String GetCurrentNodeText(int nLangID, int nGender)
    {
        if (nGender != Gender.FEMALE) nGender = Gender.MALE;
        NWScript.setLocalString(NWObject.MODULE, "NWNX!EVENTS!GET_NODE_TEXT", NWScript.intToString(nLangID * 2 + nGender));
        return NWScript.getLocalString(NWObject.MODULE, "NWNX!EVENTS!GET_NODE_TEXT");
    }

    public static void SetCurrentNodeText(String sText, int nLangID, int nGender)
    {
        if (nGender != Gender.FEMALE) nGender = Gender.MALE;
        NWScript.setLocalString(NWObject.MODULE, "NWNX!EVENTS!SET_NODE_TEXT", NWScript.intToString(nLangID * 2 + nGender) + "¬" + sText);
    }

    public static int GetScriptReturnValue()
    {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!EVENTS!GET_SCRIPT_RETURN_VALUE", "         ");
        return NWScript.stringToInt(NWScript.getLocalString(NWObject.MODULE, "NWNX!EVENTS!GET_SCRIPT_RETURN_VALUE"));
    }
}
