package contagionJVM.Dialog;

import contagionJVM.Entities.PlayerEntity;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.Helper.ColorToken;
import contagionJVM.Repository.OverflowItemRepository;
import contagionJVM.Repository.PlayerRepository;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.Scheduler;
import org.nwnx.nwnx2.jvm.constants.ObjectType;

@SuppressWarnings("UnusedDeclaration")
public class Conversation_RestMenu extends DialogBase implements IDialogHandler {
    @Override
    public PlayerDialog SetUp(NWObject oPC) {

        PlayerDialog dialog = new PlayerDialog();
        DialogPage mainPage = new DialogPage(
                BuildMainPageHeader(oPC),
                ColorToken.Green() + "Open Overflow Inventory" + ColorToken.End(),
                "Allocate Skill Points",
                "Dice Bag",
                "Emote Menu",
                "View Badges",
                "View Key Items",
                "Modify Clothes",
                "Dye Clothes",
                "Character Management");

        dialog.addPage("MainPage", mainPage);

        return dialog;
    }

    @Override
    public void Initialize()
    {
        PlayerGO pcGO = new PlayerGO(GetPC());
        OverflowItemRepository repo = new OverflowItemRepository();
        long overflowCount = repo.GetPlayerOverflowItemCount(pcGO.getUUID());

        if(overflowCount <= 0)
        {
            SetResponseVisible("MainPage", 1, false);
        }
    }

    @Override
    public void DoAction(final NWObject oPC, String pageName, int responseID) {
        switch (pageName) {
            case "MainPage":
                switch (responseID) {
                    // Open Overflow Inventory
                    case 1:
                        final NWObject storage = NWScript.createObject(ObjectType.PLACEABLE, "overflow_storage", NWScript.getLocation(oPC), false, "");
                        Scheduler.assign(oPC, new Runnable() {
                            @Override
                            public void run() {
                                NWScript.actionInteractObject(storage);
                            }
                        });
                        break;
                    // Allocate Skill Points
                    case 2:
                        SwitchConversation("AllocateSkillPoints");
                        break;
                    // Dice Bag
                    case 3:
                        NWScript.setLocalObject(oPC, "dmfi_univ_target", oPC);
                        NWScript.setLocalLocation(oPC, "dmfi_univ_location", NWScript.getLocation(oPC));
                        NWScript.setLocalString(oPC, "dmfi_univ_conv", "pc_dicebag");
                        Scheduler.assign(oPC, new Runnable() {
                            @Override
                            public void run() {
                                NWScript.clearAllActions(false);
                                NWScript.actionStartConversation(oPC, "dmfi_universal", true, false);
                            }
                        });
                        break;
                    // Emote Menu
                    case 4:
                        NWScript.setLocalObject(oPC, "dmfi_univ_target", oPC);
                        NWScript.setLocalLocation(oPC, "dmfi_univ_location", NWScript.getLocation(oPC));
                        NWScript.setLocalString(oPC, "dmfi_univ_conv", "pc_emote");
                        Scheduler.assign(oPC, new Runnable() {
                            @Override
                            public void run() {
                                NWScript.clearAllActions(false);
                                NWScript.actionStartConversation(oPC, "dmfi_universal", true, false);
                            }
                        });
                        break;
                    // View Badges
                    case 5:
                        SwitchConversation("ViewBadges");
                        break;
                    // Key Item Categories Page
                    case 6:
                        SwitchConversation("KeyItems");
                        break;
                    // Modify Clothes
                    case 7:
                        Scheduler.assign(oPC, new Runnable() {
                            @Override
                            public void run() {
                                NWScript.actionStartConversation(oPC, "x0_skill_ctrap", true, false);
                            }
                        });
                        break;
                    // Dye Clothes
                    case 8:
                        Scheduler.assign(oPC, new Runnable() {
                            @Override
                            public void run() {
                                NWScript.actionStartConversation(oPC, "dye_dyekit", true, false);
                            }
                        });

                        break;
                    // Character Management
                    case 9:
                        SwitchConversation("CharacterManagement");
                        break;
                }
                break;

        }
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
        header += ColorToken.Green() + "Infection: " + ColorToken.End() + entity.getCurrentInfection() + "%\n";

        return header;
    }


}
