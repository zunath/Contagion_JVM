package contagionJVM.Dialog;

import contagionJVM.GameObject.PlayerGO;
import org.nwnx.nwnx2.jvm.NWObject;

@SuppressWarnings("UnusedDeclaration")
public class DialogTest extends DialogBase implements IDialogHandler {

    @Override
    public void Initialize(NWObject oPC)
    {
        PlayerGO pcGO = new PlayerGO(oPC);

        DialogPage page = new DialogPage();
        page.getResponses().add(new DialogResponse("Sue"));
        page.getResponses().add(new DialogResponse("Sally"));
        page.getResponses().add(new DialogResponse("Billy"));
        page.getResponses().add(new DialogResponse("John"));
        page.getResponses().add(new DialogResponse("Jack"));
        page.getResponses().add(new DialogResponse("Jake"));
        page.getResponses().add(new DialogResponse("Picard"));
        page.getResponses().add(new DialogResponse("Jean"));
        page.getResponses().add(new DialogResponse("Raiden"));
        page.getResponses().add(new DialogResponse("Snake"));
        page.getResponses().add(new DialogResponse("Numbah 11"));

        PlayerDialog dialog = new PlayerDialog(page);
        page = new DialogPage();
        page.getResponses().add(new DialogResponse("page2 resp1"));
        page.getResponses().add(new DialogResponse("page2 resp2"));
        page.getResponses().add(new DialogResponse("page2 resp3"));
        dialog.getDialogPages().add(page);

        DialogManager.storePlayerDialog(pcGO.getUUID(), dialog);

    }

    @Override
    public void DoAction(NWObject oPC, int pageID, int responseID)
    {
        switch(responseID)
        {
            case 1:
                ChangePage(1);
                break;
            default:
                break;
        }
    }

    @Override
    public void EndDialog()
    {
        super.EndDialog();
    }
}
