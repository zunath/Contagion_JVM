package contagionJVM.Dialog;

import contagionJVM.Entities.PlayerEntity;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.Helper.ColorToken;
import contagionJVM.Repository.PlayerRepository;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.Scheduler;

@SuppressWarnings("UnusedDeclaration")
public class Conversation_RestMenu extends DialogBase implements IDialogHandler {
    @Override
    public PlayerDialog SetUp(NWObject oPC) {

        PlayerDialog dialog = new PlayerDialog();
        DialogPage mainPage = new DialogPage(
                BuildMainPageHeader(oPC),
                "Allocate Skill Points",
                "View Badges",
                "View Key Items",
                "Modify Clothes",
                "Character Management");

        dialog.addPage("MainPage", mainPage);

        return dialog;
    }

    @Override
    public void Initialize()
    {

    }

    @Override
    public void DoAction(final NWObject oPC, String pageName, int responseID) {
        switch (pageName) {
            case "MainPage":
                switch (responseID) {
                    // Allocate Skill Points
                    case 1:
                        SwitchConversation("AllocateSkillPoints");
                        break;
                    // View Badges
                    case 2:
                        SwitchConversation("ViewBadges");
                        break;
                    // Key Item Categories Page
                    case 3:
                        SwitchConversation("KeyItems");
                        break;
                    // Modify Clothes
                    case 4:
                        Scheduler.assign(oPC, new Runnable() {
                            @Override
                            public void run() {
                                NWScript.actionStartConversation(oPC, "x0_skill_ctrap", false, false);
                            }
                        });
                        break;
                    // Character Management
                    case 5:
                        SwitchConversation("CharacterManagement");
                        break;
                }
                break;

        }

        Scheduler.flushQueues();
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
