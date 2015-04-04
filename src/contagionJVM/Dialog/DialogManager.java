package contagionJVM.Dialog;

import contagionJVM.GameObject.PlayerGO;
import contagionJVM.Helper.ErrorHelper;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.Scheduler;

import java.util.HashMap;

public class DialogManager {

    public static final int NumberOfResponsesPerPage = 12;
    private static HashMap<String, PlayerDialog> playerDialogs;

    public static void storePlayerDialog(String uuid, PlayerDialog dialog)
    {
        if(playerDialogs == null)
        {
            playerDialogs = new HashMap<>();
        }


        playerDialogs.put(uuid, dialog);
    }

    public static PlayerDialog loadPlayerDialog(String uuid)
    {
        if(playerDialogs == null)
        {
            playerDialogs = new HashMap<>();
        }

        if(playerDialogs.containsKey(uuid))
        {
            return playerDialogs.get(uuid);
        }
        else
        {
            return null;
        }
    }

    public static void removePlayerDialog(String uuid)
    {
        if(playerDialogs == null)
        {
            playerDialogs = new HashMap<>();
        }

        playerDialogs.remove(uuid);
    }

    public static void loadConversation(NWObject oPC, NWObject oTalkTo, String conversationName)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException
    {
        PlayerGO pcGO = new PlayerGO(oPC);
        Class scriptClass = Class.forName("contagionJVM.Dialog.Conversation_" + conversationName);
        IDialogHandler script = (IDialogHandler)scriptClass.newInstance();
        PlayerDialog dialog = script.SetUp(oPC);
        dialog.setActiveDialogName(conversationName);
        dialog.setDialogTarget(oTalkTo);
        DialogManager.storePlayerDialog(pcGO.getUUID(), dialog);

    }

    public static void startConversation(NWObject oPC, final NWObject oTalkTo, final String conversationName)
    {
        try {
            loadConversation(oPC, oTalkTo, conversationName);

            Scheduler.assign(oPC, new Runnable() {
                @Override
                public void run() {
                    NWScript.actionStartConversation(oTalkTo, "reo_dialog", true, false);
                }
            });
            Scheduler.flushQueues();
        }
        catch(Exception ex) {
            ErrorHelper.HandleException(ex, "Dialog_Start was unable to execute class method: contagionJVM.Dialog.Conversation_" + conversationName + ".Initialize()");
        }
    }

    public static void endConversation(NWObject oPC)
    {
        PlayerGO pcGO = new PlayerGO(oPC);
        PlayerDialog playerDialog = DialogManager.loadPlayerDialog(pcGO.getUUID());
        playerDialog.setIsEnding(true);
        DialogManager.storePlayerDialog(pcGO.getUUID(), playerDialog);
    }

}
