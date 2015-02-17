package contagionJVM.Dialog;

import org.nwnx.nwnx2.jvm.NWObject;

public interface IDialogHandler {
    PlayerDialog Initialize(NWObject oPC);
    void DoAction(NWObject oPC, String pageName, int responseID);
    void EndDialog();
}
