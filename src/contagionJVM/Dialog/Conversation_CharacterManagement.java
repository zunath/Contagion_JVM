package contagionJVM.Dialog;

import org.nwnx.nwnx2.jvm.NWObject;

@SuppressWarnings("UnusedDeclaration")
public class Conversation_CharacterManagement extends DialogBase implements IDialogHandler {
    @Override
    public PlayerDialog Initialize(NWObject oPC) {
        PlayerDialog dialog = new PlayerDialog();
        DialogPage characterManagementPage = new DialogPage(
                "Character Management & Information Page",
                "Disable PVP Protection",
                "Manage CD Keys",
                "Change Portrait",
                "Change Head",
                "Back"
        );

        dialog.addPage("MainPage", characterManagementPage);

        return dialog;
    }

    @Override
    public void DoAction(NWObject oPC, String pageName, int responseID) {
        switch (pageName)
        {
            case "MainPage":
            {
                switch (responseID)
                {
                    case 1: // Disable PVP Protection
                        break;
                    case 2: // Manage CD Keys
                        SwitchConversation("ManageCDKeys");
                        break;
                    case 3: // Change Portrait
                        SwitchConversation("ChangePortrait");
                        break;
                    case 4: // Change Head
                        SwitchConversation("ChangeHead");
                        break;
                    case 5: // Back
                        SwitchConversation("RestMenu");
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
