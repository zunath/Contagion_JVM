package contagionJVM.Dialog;

import contagionJVM.Entities.PCAuthorizedCDKeyEntity;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.Repository.PCAuthorizedCDKeysRepository;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnusedDeclaration")
public class Conversation_ManageCDKeys extends DialogBase implements  IDialogHandler {
    @Override
    public PlayerDialog SetUp(NWObject oPC) {
        PlayerDialog dialog = new PlayerDialog();
        DialogPage mainPage = new DialogPage(
                "This menu may be used to manage CD keys which are allowed to access the characters on your account.\n\n" +
                        "Note that actions taken here will affect your entire account.",
                "Add a CD Key",
                "Remove a CD Key",
                "Back"

        );

        DialogPage removeKeyPage = new DialogPage(
                "The following CD keys are tied to your account. Select a CD key to remove it from your account.\n\n" +
                        "Note that you cannot remove the CD key you are currently logged in under.",
                "CD Key #1: ",
                "CD Key #2: ",
                "CD Key #3: ",
                "CD Key #4: ",
                "CD Key #5: ",
                "CD Key #6: ",
                "CD Key #7: ",
                "CD Key #8: ",
                "CD Key #9: ",
                "CD Key #10: ",
                "Back"
        );

        dialog.addPage("MainPage", mainPage);
        dialog.addPage("RemoveCDKeyPage", removeKeyPage);

        return dialog;
    }

    @Override
    public void Initialize() {
        NWObject oPC = GetPC();
        LoadRemoveCDKeyOptions();

        boolean isAdding = NWScript.getLocalInt(oPC, "TEMP_ADDING_CD_KEY_TO_ACCOUNT") == 1;

        if(isAdding)
        {
            SetResponseText("MainPage", 1, "CANCEL ADDING CD KEY TO ACCOUNT");
        }
        else
        {
            SetResponseText("MainPage", 1, "Add a CD Key");
        }

    }

    @Override
    public void DoAction(NWObject oPC, String pageName, int responseID) {

        switch (pageName)
        {
            case "MainPage":
            {

                switch(responseID)
                {
                    case 1: // Add a CD Key
                        HandleAddCDKey();
                        break;
                    case 2: // Remove a CD Key
                        ChangePage("RemoveCDKeyPage");
                        LoadRemoveCDKeyOptions();
                        break;
                    case 3: // Back
                        SwitchConversation("CharacterManagement");
                        break;
                }
                break;
            }
            case "RemoveCDKeyPage":
            {
                switch (responseID)
                {
                    case 11:
                        ChangePage("MainPage");
                        break;
                    default:
                        HandleRemoveCDKeyOption(responseID);
                        break;
                }
                break;
            }
        }

    }

    @Override
    public void EndDialog() {

    }


    private void LoadRemoveCDKeyOptions()
    {
        String account = NWScript.getPCPlayerName(GetPC());
        List<DialogResponse> responses = new ArrayList<>();
        PCAuthorizedCDKeysRepository repo = new PCAuthorizedCDKeysRepository();
        PCAuthorizedCDKeyEntity entity = repo.GetByAccountName(account);
        String cdKey = NWScript.getPCPublicCDKey(GetPC(), false);

        if(entity == null)
        {
            for(int x = 1; x <= 10; x++)
            {
                SetResponseVisible("RemoveCDKeyPage", x, false);
            }
        }
        else
        {
            if(entity.getCdKey1().equals(""))
                SetResponseVisible("RemoveCDKeyPage", 1, false);
            else
                SetResponseText("RemoveCDKeyPage", 1, "CD Key #1: " + entity.getCdKey1()
                        + (cdKey.equals(entity.getCdKey1()) ? " (CURRENT)" : ""));

            if(entity.getCdKey2().equals(""))
                SetResponseVisible("RemoveCDKeyPage", 2, false);
            else
                SetResponseText("RemoveCDKeyPage", 2, "CD Key #2: " + entity.getCdKey2()
                        + (cdKey.equals(entity.getCdKey2()) ? " (CURRENT)" : ""));

            if(entity.getCdKey3().equals(""))
                SetResponseVisible("RemoveCDKeyPage", 3, false);
            else
                SetResponseText("RemoveCDKeyPage", 3, "CD Key #3: " + entity.getCdKey3()
                        + (cdKey.equals(entity.getCdKey3()) ? " (CURRENT)" : ""));

            if(entity.getCdKey4().equals(""))
                SetResponseVisible("RemoveCDKeyPage", 4, false);
            else
                SetResponseText("RemoveCDKeyPage", 4, "CD Key #4: " + entity.getCdKey4()
                        + (cdKey.equals(entity.getCdKey4()) ? " (CURRENT)" : ""));

            if(entity.getCdKey5().equals(""))
                SetResponseVisible("RemoveCDKeyPage", 5, false);
            else
                SetResponseText("RemoveCDKeyPage", 5, "CD Key #5: " + entity.getCdKey5()
                        + (cdKey.equals(entity.getCdKey5()) ? " (CURRENT)" : ""));

            if(entity.getCdKey6().equals(""))
                SetResponseVisible("RemoveCDKeyPage", 6, false);
            else
                SetResponseText("RemoveCDKeyPage", 6, "CD Key #6: " + entity.getCdKey6()
                        + (cdKey.equals(entity.getCdKey6()) ? " (CURRENT)" : ""));

            if(entity.getCdKey7().equals(""))
                SetResponseVisible("RemoveCDKeyPage", 7, false);
            else
                SetResponseText("RemoveCDKeyPage", 7, "CD Key #7: " + entity.getCdKey7()
                        + (cdKey.equals(entity.getCdKey7()) ? " (CURRENT)" : ""));

            if(entity.getCdKey8().equals(""))
                SetResponseVisible("RemoveCDKeyPage", 8, false);
            else
                SetResponseText("RemoveCDKeyPage", 8, "CD Key #8: " + entity.getCdKey8()
                        + (cdKey.equals(entity.getCdKey8()) ? " (CURRENT)" : ""));

            if(entity.getCdKey9().equals(""))
                SetResponseVisible("RemoveCDKeyPage", 9, false);
            else
                SetResponseText("RemoveCDKeyPage", 9, "CD Key #9: " + entity.getCdKey9()
                        + (cdKey.equals(entity.getCdKey9()) ? " (CURRENT)" : ""));

            if(entity.getCdKey10().equals(""))
                SetResponseVisible("RemoveCDKeyPage", 10, false);
            else
                SetResponseText("RemoveCDKeyPage", 10, "CD Key #10: " + entity.getCdKey10()
                        + (cdKey.equals(entity.getCdKey10()) ? " (CURRENT)" : ""));

        }
    }

    private void HandleRemoveCDKeyOption(int responseID)
    {
        String account = NWScript.getPCPlayerName(GetPC());
        List<DialogResponse> responses = new ArrayList<>();
        PCAuthorizedCDKeysRepository repo = new PCAuthorizedCDKeysRepository();
        PCAuthorizedCDKeyEntity entity = repo.GetByAccountName(account);
        String cdKey = NWScript.getPCPublicCDKey(GetPC(), false);
        String errorMessage = "Cannot remove the CD key you are currently logged into.";


        switch (responseID)
        {
            case 1:
                if(cdKey.equals(entity.getCdKey1()))
                    NWScript.floatingTextStringOnCreature(errorMessage, GetPC(), false);
                else entity.setCdKey1("");
                break;
            case 2:
                if(cdKey.equals(entity.getCdKey2()))
                    NWScript.floatingTextStringOnCreature(errorMessage, GetPC(), false);
                else entity.setCdKey2("");
                break;
            case 3:
                if(cdKey.equals(entity.getCdKey3()))
                    NWScript.floatingTextStringOnCreature(errorMessage, GetPC(), false);
                else entity.setCdKey3("");
                break;
            case 4:
                if(cdKey.equals(entity.getCdKey4()))
                    NWScript.floatingTextStringOnCreature(errorMessage, GetPC(), false);
                else entity.setCdKey4("");
                break;
            case 5:
                if(cdKey.equals(entity.getCdKey5()))
                    NWScript.floatingTextStringOnCreature(errorMessage, GetPC(), false);
                else entity.setCdKey5("");
                break;
            case 6:
                if(cdKey.equals(entity.getCdKey6()))
                    NWScript.floatingTextStringOnCreature(errorMessage, GetPC(), false);
                else entity.setCdKey6("");
                break;
            case 7:
                if(cdKey.equals(entity.getCdKey7()))
                    NWScript.floatingTextStringOnCreature(errorMessage, GetPC(), false);
                else entity.setCdKey7("");
                break;
            case 8:
                if(cdKey.equals(entity.getCdKey8()))
                    NWScript.floatingTextStringOnCreature(errorMessage, GetPC(), false);
                else entity.setCdKey8("");
                break;
            case 9:
                if(cdKey.equals(entity.getCdKey9()))
                    NWScript.floatingTextStringOnCreature(errorMessage, GetPC(), false);
                else entity.setCdKey9("");
                break;
            case 10:
                if(cdKey.equals(entity.getCdKey10()))
                    NWScript.floatingTextStringOnCreature(errorMessage, GetPC(), false);
                else entity.setCdKey10("");
                break;
        }

        repo.Save(entity);
        LoadRemoveCDKeyOptions();
    }

    private void HandleAddCDKey()
    {
        NWObject oPC = GetPC();
        boolean isAdding = NWScript.getLocalInt(oPC, "TEMP_ADDING_CD_KEY_TO_ACCOUNT") == 1;

        if(isAdding)
        {
            SetResponseText("MainPage", 1, "Add a CD Key");
            NWScript.deleteLocalInt(oPC, "TEMP_ADDING_CD_KEY_TO_ACCOUNT");
        }
        else
        {
            NWScript.setLocalInt(oPC, "TEMP_ADDING_CD_KEY_TO_ACCOUNT", 1);
            SetResponseText("MainPage", 1, "CANCEL ADDING CD KEY TO ACCOUNT");
            NWScript.floatingTextStringOnCreature("Please log in to the server under the new CD key to store it to your account.", oPC, false);
        }

    }
}
