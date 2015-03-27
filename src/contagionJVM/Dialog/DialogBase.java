package contagionJVM.Dialog;

import contagionJVM.GameObject.PlayerGO;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

import java.io.PrintWriter;
import java.io.StringWriter;

@SuppressWarnings("UnusedDeclaration")
public abstract class DialogBase {

    protected NWObject GetPC()
    {
        return NWScript.getPCSpeaker();
    }

    protected void ChangePage(String pageName)
    {
        PlayerGO pcGO = new PlayerGO(GetPC());
        PlayerDialog dialog = DialogManager.loadPlayerDialog(pcGO.getUUID());
        dialog.setCurrentPageName(pageName);
        dialog.setPageOffset(0);
    }

    protected void SetPageHeader(String pageName, String header)
    {
        PlayerGO pcGO = new PlayerGO(GetPC());
        PlayerDialog dialog = DialogManager.loadPlayerDialog(pcGO.getUUID());
        DialogPage page = dialog.getPageByName(pageName);
        page.setHeader(header);
    }

    protected void SwitchConversation(String conversationName)
    {
        PlayerGO pcGO = new PlayerGO(GetPC());
        PlayerDialog dialog = DialogManager.loadPlayerDialog(pcGO.getUUID());

        try
        {
            DialogManager.loadConversation(GetPC(), dialog.getDialogTarget(), conversationName);
        }
        catch (Exception ex)
        {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();

            String message = "Error running DialogBase.SwitchConversation();";
            System.out.println(message);
            System.out.println("Exception: ");
            System.out.println(exceptionAsString);

            NWScript.writeTimestampedLogEntry(message);
            NWScript.writeTimestampedLogEntry("Exception:");
            NWScript.writeTimestampedLogEntry(exceptionAsString);
        }
        dialog = DialogManager.loadPlayerDialog(pcGO.getUUID());
        dialog.resetPage();
        ChangePage(dialog.getCurrentPageName());


    }

}
