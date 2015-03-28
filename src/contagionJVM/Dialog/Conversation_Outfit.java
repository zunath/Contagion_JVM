package contagionJVM.Dialog;

import org.nwnx.nwnx2.jvm.NWObject;

@SuppressWarnings("UnusedDeclaration")
public class Conversation_Outfit extends DialogBase implements IDialogHandler {

    @Override
    public PlayerDialog Initialize(NWObject oPC) {
        PlayerDialog dialog = new PlayerDialog();

        DialogPage mainPage = new DialogPage(
                "Please select an option.",
                "Save Outfit",
                "Load Outfit",
                "Back"
        );

        dialog.addPage("MainPage", mainPage);
        return dialog;
    }

    @Override
    public void DoAction(NWObject oPC, String pageName, int responseID) {

        switch(pageName)
        {
            case "MainPage":
            {
                switch (responseID)
                {
                    case 1: // Save Outfit
                        break;
                    case 2: // Load Outfit
                        break;
                    case 3: // Back
                        break;
                }
                break;
            }
        }
    }

    @Override
    public void EndDialog() {

    }
}
