package contagionJVM.Event;

import contagionJVM.IScriptEventHandler;
import contagionJVM.NWNX.NWNX_Chat;
import org.nwnx.nwnx2.jvm.NWObject;

@SuppressWarnings("UnusedDeclaration")
public class NWNX_OnCCMessage implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        NWObject oPC = objSelf;
        int nType = NWNX_Chat.GetCCMessageType();
        int nSubtype = NWNX_Chat.GetCCMessagSubtype();

        // Turn these on to find new event types and subtypes. Useful for disabling those pesky system messages!
        //SendMessageToPC(oPC, "CC_SCRIPT: nType = " + IntToString(nType));
        //SendMessageToPC(oPC, "CC_SCRIPT: nSubtype = " + IntToString(nSubtype));

        switch(nType)
        {
            //Initiative rolls
            case 14:
                NWNX_Chat.SuppressMessage();
                break;

            // Damage data
            case 3:
            {
                break;
            }

            // Unknown
            case 4:
            {
                break;
            }

            // Attack data
            case 5:
            {
                break;
            }
            //Attack data to party
            case 6:
            {
                break;
            }
            //Saving throws
            case 7:
            {
                break;
            }
            // Use item message
            case 8:
            {
                NWNX_Chat.SuppressMessage();
                break;
            }
            // Skill data
            case 9:
            {
                break;
            }

            // Feedback Messages
            case 11:
            {
                switch(nSubtype)
                {
                    case 71:  // Armor Check Penalty
                    case 182: // XP Given
                    case 183: // XP Lost
                    case 50:  // Item Gained
                    case 51:  // Item Lost
                    case 18:  // Resting
                    case 20:  // Resting canceled.
                        NWNX_Chat.SuppressMessage();
                        break;

                    case 204: //Custom text message
                        break;
                }
                break;
            }

            default:

                break;
        }
    }
}
