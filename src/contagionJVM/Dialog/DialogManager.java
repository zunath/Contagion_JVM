package contagionJVM.Dialog;

import java.util.HashMap;

public class DialogManager {

    public static final int NumberOfResponsesPerPage = 10;
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
}
