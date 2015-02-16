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
        page.getResponses().add(new DialogResponse("John"));
        page.getResponses().add(new DialogResponse("John"));
        page.getResponses().add(new DialogResponse("John"));
        page.getResponses().add(new DialogResponse("John"));
        page.getResponses().add(new DialogResponse("John"));
        page.getResponses().add(new DialogResponse("John"));
        page.getResponses().add(new DialogResponse("Numbah 11"));

        PlayerDialog dialog = new PlayerDialog(page);
        DialogManager.storePlayerDialog(pcGO.getUUID(), dialog);

    }

    @Override
    public void DoAction(int responseID)
    {

    }

    @Override
    public void EndDialog()
    {


        super.EndDialog();
    }
}
