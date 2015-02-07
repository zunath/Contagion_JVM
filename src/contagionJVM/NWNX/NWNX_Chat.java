package contagionJVM.NWNX;

import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

public class NWNX_Chat {

    private static String LIST_ITEM_NAME = "PC_";
    private static String PC_ID_NAME = "OID";

    public static final int CHAT_CHANNEL_TALK        = 1;
    public static final int CHAT_CHANNEL_SHOUT       = 2;
    public static final int CHAT_CHANNEL_WHISPER     = 3;
    public static final int CHAT_CHANNEL_PRIVATE     = 4;
    public static final int CHAT_CHANNEL_SERVER_MSG  = 5;
    public static final int CHAT_CHANNEL_PARTY       = 6;

    public static String GetStringFrom(String s, int from)
    {
        return NWScript.getStringRight(s, NWScript.getStringLength(s) - from);
    }

    public static void Initialize()
    {
        int i;
        NWObject oMod = NWObject.MODULE;
        // memory for chat text
        String sMemory = "";
        for (i = 0; i < 8; i++) // reserve 8*128 bytes
            sMemory += "................................................................................................................................";
        NWScript.setLocalString(oMod, "NWNX!CHAT!SPACER", sMemory);
    }

    private static String GetSpacer()
    {
        return NWScript.getLocalString(NWObject.MODULE, "NWNX!CHAT!SPACER");
    }

    public static void PCEnter(NWObject oPC)
    {
        if (!NWScript.getIsObjectValid(oPC)) return;
        NWObject oMod = NWObject.MODULE;
        NWScript.setLocalString(oPC, "NWNX!CHAT!GETID", oPC.getObjectId() + "        ");
        String sID = NWScript.getLocalString(oPC, "NWNX!CHAT!GETID");
        int nID = NWScript.stringToInt(sID);
        if (nID != -1)
        {
            NWScript.setLocalObject(oMod, LIST_ITEM_NAME + sID, oPC);
            NWScript.setLocalInt(oPC, PC_ID_NAME, nID);
        }
        NWScript.deleteLocalString(oPC, "NWNX!CHAT!GETID");
    }

    public static void PCExit(NWObject oPC)
    {
        if (!NWScript.getIsObjectValid(oPC)) return;
        int nID = NWScript.getLocalInt(oPC, PC_ID_NAME);
        NWScript.deleteLocalInt(oPC, PC_ID_NAME);
        NWScript.deleteLocalObject(NWObject.MODULE, LIST_ITEM_NAME + nID);
    }

    public static NWObject GetPCByPlayerID(int nID)
    {
        return NWScript.getLocalObject(NWObject.MODULE, LIST_ITEM_NAME + nID);
    }


    public static String GetMessageText()
    {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!CHAT!TEXT", GetSpacer());
        return NWScript.getLocalString(NWObject.MODULE, "NWNX!CHAT!TEXT");
    }

    public static ChatMessage GetMessage()
    {
        ChatMessage cmMessage = new ChatMessage();
        String sText = GetMessageText();

        int nMode = NWScript.stringToInt(NWScript.getStringLeft(sText, 2));
        int nTo = NWScript.stringToInt(NWScript.getSubString(sText, 2, 10));
        sText = GetStringFrom(sText, 12);
        cmMessage.setMode(nMode); 
        cmMessage.setRecipient(GetPCByPlayerID(nTo));
        cmMessage.setText(sText);
        return cmMessage;
    }

    public static void Log(String sLogMessage)
    {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!CHAT!LOG", sLogMessage);
    }

    public static void SuppressMessage()
    {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!CHAT!SUPRESS", "1");
        NWScript.deleteLocalString(NWObject.MODULE, "NWNX!CHAT!SUPRESS");
    }

    public static int SendMessage(NWObject oSender, int nChannel, String sMessage, NWObject oRecipient)
    {
        if (!NWScript.getIsObjectValid(oSender)) return 0;
        if (NWScript.findSubString(sMessage, "¬", 0)!=-1) return 0;
        if (nChannel == CHAT_CHANNEL_PRIVATE && !NWScript.getIsObjectValid(oRecipient)) return 0;
        NWScript.setLocalString(oSender, "NWNX!CHAT!SPEAK", oSender.getObjectId() + "¬" + oRecipient.getObjectId() + "¬" + nChannel + "¬" + sMessage);
        if(NWScript.getLocalString(oSender, "NWNX!CHAT!SPEAK")=="1") return 1;
        else return 0;
    }

    public static void SendMessageVoid(NWObject oSender, int nChannel, String sMessage, NWObject oRecipient)
    {
        SendMessage(oSender, nChannel, sMessage, oRecipient);
    }

    public static int GetCCMessageType()
    {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!CHAT!TYPE", "  ");
        return NWScript.stringToInt(NWScript.getLocalString(NWObject.MODULE, "NWNX!CHAT!TYPE"));
    }

    public static int GetCCMessagSubtype()
    {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!CHAT!SUBTYPE", "  ");
        return NWScript.stringToInt(NWScript.getLocalString(NWObject.MODULE, "NWNX!CHAT!SUBTYPE"));
    }

}
