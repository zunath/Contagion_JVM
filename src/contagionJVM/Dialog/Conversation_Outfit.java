package contagionJVM.Dialog;

import org.nwnx.nwnx2.jvm.NWObject;

@SuppressWarnings("UnusedDeclaration")
public class Conversation_Outfit extends DialogBase implements IDialogHandler {

    @Override
    public PlayerDialog Initialize(NWObject oPC) {
        PlayerDialog dialog = new PlayerDialog();

        return dialog;
    }

    @Override
    public void DoAction(NWObject oPC, String pageName, int responseID) {

    }

    @Override
    public void EndDialog() {

    }
}
