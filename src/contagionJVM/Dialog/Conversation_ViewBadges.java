package contagionJVM.Dialog;
import contagionJVM.Entities.PCBadgeEntity;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.Helper.ColorToken;
import contagionJVM.Repository.BadgeRepository;
import org.nwnx.nwnx2.jvm.NWObject;
import java.util.List;

@SuppressWarnings("UnusedDeclaration")
public class Conversation_ViewBadges extends DialogBase implements IDialogHandler {
    @Override
    public PlayerDialog SetUp(NWObject oPC) {
        PlayerDialog dialog = new PlayerDialog();
        DialogPage mainPage = new DialogPage(
                "The following lists the badges you have discovered. Click a badge to learn more about it."
        );

        dialog.addPage("MainPage", mainPage);
        return dialog;
    }

    @Override
    public void Initialize() {
        BuildResponses();
    }

    @Override
    public void DoAction(NWObject oPC, String pageName, int responseID) {
        DialogResponse response = GetResponseByID("MainPage", responseID);
        int badgeID = (int)response.getCustomData();

        if(badgeID <= -1)
        {
            SwitchConversation("RestMenu");
        }
        else
        {
            SetPageHeader("MainPage", BuildBadgeHeader(badgeID));
        }

    }

    @Override
    public void EndDialog() {

    }

    private String BuildBadgeHeader(int badgeID)
    {
        NWObject oPC = GetPC();
        PlayerGO pcGO = new PlayerGO(oPC);
        BadgeRepository repo = new BadgeRepository();
        PCBadgeEntity entity = repo.GetByID(pcGO.getUUID(), badgeID);
        String header = ColorToken.Green() + "Badge Name: " + ColorToken.End() + entity.getBadge().getName() + "\n";
        header += ColorToken.Green() + "Date: " + ColorToken.End() + entity.getAcquiredDate().toString() + "\n";
        header += ColorToken.Green() + "Acquired Area: " + ColorToken.End() + entity.getAcquiredAreaName() + "\n\n";
        header += ColorToken.Green() + "Description: " + ColorToken.End() + entity.getBadge().getDescription() + "\n";

        return header;
    }

    private void BuildResponses()
    {
        NWObject oPC = GetPC();
        PlayerGO pcGO = new PlayerGO(oPC);
        BadgeRepository repo = new BadgeRepository();
        List<PCBadgeEntity> badges = repo.GetByUUID(pcGO.getUUID());
        DialogPage page = GetPageByName("MainPage");
        page.getResponses().clear();

        for(PCBadgeEntity badge : badges)
        {
            DialogResponse response = new DialogResponse(badge.getBadge().getName(), badge.getBadgeID());
            page.getResponses().add(response);
        }

        page.getResponses().add(new DialogResponse("Back", -1));
    }
}
