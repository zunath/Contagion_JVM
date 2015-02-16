package contagionJVM.Dialog;

import contagionJVM.GameObject.PlayerGO;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

public abstract class DialogBase {

    protected NWObject GetPC()
    {
        return NWScript.getPCSpeaker();
    }

    protected void ChangePage(int pageID)
    {
        PlayerGO pcGO = new PlayerGO(GetPC());
        PlayerDialog dialog = DialogManager.loadPlayerDialog(pcGO.getUUID());
        dialog.setCurrentPageID(pageID);
        dialog.setPageOffset(0);
    }

    protected void EndDialog()
    {
        PlayerGO pcGO = new PlayerGO(GetPC());
        DialogManager.removePlayerDialog(pcGO.getUUID());
    }

}
