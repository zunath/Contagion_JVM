package contagionJVM.Dialog;

import contagionJVM.GameObject.PlayerGO;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

@SuppressWarnings("UnusedDeclaration")
public abstract class DialogBase {

    protected NWObject GetPC()
    {
        return NWScript.getPCSpeaker();
    }

    protected void ChangePage(String pageName)
    {
        PlayerGO pcGO = new PlayerGO(GetPC());
        PlayerDialog dialog = DialogManager.loadPlayerDialog(pcGO.getUUID());
        dialog.setCurrentPageName(pageName);
        dialog.setPageOffset(0);
    }

    protected void SwitchConversation(String conversationName)
    {
        PlayerGO pcGO = new PlayerGO(GetPC());
        PlayerDialog dialog = DialogManager.loadPlayerDialog(pcGO.getUUID());
        DialogManager.startConversation(GetPC(), dialog.getDialogTarget(), conversationName);
    }

}
