package contagionJVM.Dialog;

import contagionJVM.Entities.PlayerEntity;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.Helper.ColorToken;
import contagionJVM.Repository.PlayerRepository;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

@SuppressWarnings("UnusedDeclaration")
public class Conversation_RestMenu extends DialogBase implements IDialogHandler {
    @Override
    public PlayerDialog Initialize(NWObject oPC) {

        PlayerDialog dialog = new PlayerDialog();
        DialogPage mainPage = new DialogPage(
                BuildMainPageHeader(oPC),
                "Allocate Skill Points",
                "View Key Items",
                "Modify Clothes",
                "Character Management");

        dialog.getDialogPages().add(mainPage);

        return dialog;
    }

    @Override
    public void DoAction(NWObject oPC, int pageID, int responseID) {

    }

    @Override
    public void EndDialog()
    {
    }


    private String BuildMainPageHeader(NWObject oPC)
    {
        PlayerGO pcGO = new PlayerGO(oPC);
        PlayerRepository repo = new PlayerRepository();
        PlayerEntity entity = repo.getByUUID(pcGO.getUUID());

        String header = ColorToken.Green() + "Name: " + ColorToken.End() + NWScript.getName(oPC, false) + "\n\n";
        header += ColorToken.Green() + "Level: " + ColorToken.End() + entity.getLevel() + "\n";
        header += ColorToken.Green() + "EXP: " + ColorToken.End() + entity.getExperience() + "\n";
        header += ColorToken.Green() + "Skill Points: " + ColorToken.End() + entity.getUnallocatedSP() + "\n";
        header += ColorToken.Green() + "Hunger: " + ColorToken.End() + entity.getCurrentHunger() + "%\n";
        header += ColorToken.Green() + "Thirst: " + ColorToken.End() + entity.getCurrentThirst() + "%\n";
        header += ColorToken.Green() + "Infection: " + ColorToken.End() + entity.getCurrentInfection() + "%\n";

        return header;
    }

}
