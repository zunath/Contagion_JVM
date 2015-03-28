package contagionJVM.Dialog;

import contagionJVM.Data.CharacterManagementDTO;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.Helper.ColorToken;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

@SuppressWarnings("UnusedDeclaration")
public class Conversation_CharacterManagement extends DialogBase implements IDialogHandler {
    @Override
    public PlayerDialog SetUp(NWObject oPC) {

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
    public void Initialize()
    {
        SetDialogCustomData(new CharacterManagementDTO());
        ToggleDisablePVPProtectionOption();
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
                        HandleDisablePVPProtection();
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

    private void ToggleDisablePVPProtectionOption()
    {
        PlayerGO pcGO = new PlayerGO(GetPC());
        DialogResponse response = GetResponseByID("MainPage", 1);

        if(pcGO.hasPVPSanctuary())
        {
            response.setActive(true);
        }
        else
        {
            response.setActive(false);
        }
    }

    private void HandleDisablePVPProtection()
    {
        PlayerGO pcGO = new PlayerGO(GetPC());
        CharacterManagementDTO dto = (CharacterManagementDTO)GetDialogCustomData();

        if(!pcGO.hasPVPSanctuary())
        {
            return;
        }

        if(dto.isConfirmingDisableSanctuary())
        {
            pcGO.setHasPVPSanctuaryOverride(true);
            dto.setIsConfirmingDisableSanctuary(false);
            NWScript.floatingTextStringOnCreature(ColorToken.Red() + "PVP protection has been disabled. You may now attack and be attacked by other players." + ColorToken.End(), GetPC(), false);
            SetResponseText("MainPage", 1, "Disable PVP Protection");
        }
        else
        {
            dto.setIsConfirmingDisableSanctuary(true);
            NWScript.floatingTextStringOnCreature(ColorToken.Red() + "WARNING: PVP protection prevents other players from attacking you. If you disable this, players will immediately be able to attack you anywhere. Click again to confirm." + ColorToken.End(), GetPC(), false);
            SetResponseText("MainPage", 1, "CONFIRM DISABLE PVP PROTECTION");
        }

        SetDialogCustomData(dto);

        ToggleDisablePVPProtectionOption();
    }
}
