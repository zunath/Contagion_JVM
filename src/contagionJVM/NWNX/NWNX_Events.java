package contagionJVM.NWNX;

import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.NWVector;
import org.nwnx.nwnx2.jvm.constants.Gender;

import java.util.Objects;

@SuppressWarnings("UnusedDeclaration")
public class NWNX_Events {
    public static final int EVENT_TYPE_ALL                = 0;
    public static final int EVENT_TYPE_SAVE_CHAR          = 1;
    public static final int EVENT_TYPE_PICKPOCKET         = 2;
    public static final int EVENT_TYPE_ATTACK             = 3;
    public static final int EVENT_TYPE_USE_ITEM           = 4;
    public static final int EVENT_TYPE_QUICKCHAT          = 5;
    public static final int EVENT_TYPE_EXAMINE            = 6;
    public static final int EVENT_TYPE_USE_SKILL          = 7;
    public static final int EVENT_TYPE_USE_FEAT           = 8;
    public static final int EVENT_TYPE_TOGGLE_MODE        = 9;
    public static final int EVENT_TYPE_CAST_SPELL         = 10;
    public static final int EVENT_TYPE_TOGGLE_PAUSE       = 11;
    public static final int EVENT_TYPE_POSSESS_FAMILIAR   = 12;
    public static final int EVENT_TYPE_VALIDATE_CHARACTER = 13;
    public static final int EVENT_TYPE_DESTROY_OBJECT     = 14;


    public static final int LANGUAGE_ENGLISH              = 0;
    public static final int LANGUAGE_FRENCH               = 1;
    public static final int LANGUAGE_GERMAN               = 2;
    public static final int LANGUAGE_ITALIAN              = 3;
    public static final int LANGUAGE_SPANISH              = 4;
    public static final int LANGUAGE_POLISH               = 5;
    public static final int LANGUAGE_KOREAN               = 128;
    public static final int LANGUAGE_CHINESE_TRADITIONAL  = 129;
    public static final int LANGUAGE_CHINESE_SIMPLIFIED   = 130;
    public static final int LANGUAGE_JAPANESE             = 131;

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
        if (Objects.equals(sHandler, ""))
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
