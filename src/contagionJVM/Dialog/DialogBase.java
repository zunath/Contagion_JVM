package contagionJVM.Dialog;

import contagionJVM.GameObject.PlayerGO;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

public abstract class DialogBase {

    protected NWObject getPC()
    {
        return NWScript.getPCSpeaker();
    }

    protected void EndDialog()
    {
        PlayerGO pcGO = new PlayerGO(getPC());
        DialogManager.removePlayerDialog(pcGO.getUUID());
    }

}
