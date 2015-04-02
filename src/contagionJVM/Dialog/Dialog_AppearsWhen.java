package contagionJVM.Dialog;

import contagionJVM.GameObject.PlayerGO;
import contagionJVM.IScriptEventHandler;
import contagionJVM.NWNX.NWNX_Events;
import contagionJVM.NWNX.NodeType;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

@SuppressWarnings("UnusedDeclaration")
public class Dialog_AppearsWhen implements IScriptEventHandler {
    @Override
    public void runScript(NWObject oNPC) {
        NWObject oPC = NWScript.getPCSpeaker();
        PlayerGO pcGO = new PlayerGO(oPC);
        String uuid = pcGO.getUUID();
        PlayerDialog dialog = DialogManager.loadPlayerDialog(uuid);
        DialogPage page = dialog.getCurrentPage();
        int nodeID = NWNX_Events.GetCurrentNodeID();
        int nodeType = NWNX_Events.GetCurrentNodeType();
        int gender = NWScript.getGender(oPC);
        boolean displayNode = false;
        String nodeText = NWNX_Events.GetCurrentNodeText(NWNX_Events.LANGUAGE_ENGLISH, gender);
        String newNodeText = nodeText;

        if(nodeText.equals("Next"))
        {
            int displayCount = page.getNumberOfResponses() - (DialogManager.NumberOfResponsesPerPage * dialog.getPageOffset());

            if(displayCount > DialogManager.NumberOfResponsesPerPage)
            {
                displayNode = true;
            }
        }
        else if(nodeText.equals("Previous"))
        {
            if(dialog.getPageOffset() > 0)
            {
                displayNode = true;
            }
        }
        else if(nodeText.equals("End"))
        {
            displayNode = true;
        }
        else if(nodeType == NodeType.ReplyNode)
        {
            int responseID = (dialog.getPageOffset() * DialogManager.NumberOfResponsesPerPage) + nodeID;

            if(responseID + 1 <= page.getNumberOfResponses()) {
                DialogResponse response = page.getResponses().get(responseID);

                if (response != null) {
                    newNodeText = response.getText();
                    displayNode = response.isActive();
                }
            }
        }
        else if(nodeType == NodeType.StartingNode || nodeType == NodeType.EntryNode)
        {
            newNodeText = page.getHeader();
            NWScript.setCustomToken(90000, newNodeText);
            NWScript.setLocalInt(oNPC, "REO_CONVERSATION_SHOW_NODE", 1);
            return;
        }


        NWScript.setCustomToken(90001 + nodeID, newNodeText);
        //NWNX_Events.SetCurrentNodeText(newNodeText, NWNX_Events.LANGUAGE_ENGLISH, gender);
        NWScript.setLocalInt(oNPC, "REO_CONVERSATION_SHOW_NODE", displayNode ? 1 : 0);

    }
}
