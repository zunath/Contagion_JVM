package contagionJVM.Dialog;

import org.nwnx.nwnx2.jvm.NWObject;

public interface IDialogHandler {
    void Initialize(NWObject oPC);
    void DoAction(int responseID);
    void EndDialog();
}
