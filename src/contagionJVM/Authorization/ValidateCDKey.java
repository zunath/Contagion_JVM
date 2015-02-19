package contagionJVM.Authorization;

import contagionJVM.Entities.AuthorizedDMEntity;
import contagionJVM.IScriptEventHandler;
import contagionJVM.NWNX.NWNX_Funcs;
import contagionJVM.Repository.AuthorizedDMRepository;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

@SuppressWarnings("unused")
public class ValidateCDKey implements IScriptEventHandler {
    @Override
    public void runScript(NWObject pc) {

        String sCDKey = NWScript.getPCPublicCDKey(pc, false);
        String sPlayerName = NWScript.getPCPlayerName(pc);
        String sIP = NWScript.getPCIPAddress(pc);
        boolean isDM = false;
        AuthorizedDMRepository repo = new AuthorizedDMRepository();

        if (NWScript.getIsDM(pc) || (NWScript.getIsDMPossessed(pc) && NWScript.getIsDM(NWScript.getMaster(pc))))
        {
            AuthorizedDMEntity entity = repo.getByCDKey(sCDKey);

            if(entity != null && entity.getDMRole() > 0)
            {
                isDM = true;
            }

            if (isDM)
            {
                NWScript.sendMessageToAllDMs("<DM authorized to join server, " + sCDKey + ", " + sPlayerName + ">" );
            }
            else
            {
                NWScript.sendMessageToAllDMs("<WARNING, UNAUTHORIZED DM JOIN ATTEMPT BY " + sCDKey + ", " + sPlayerName + ", " + sIP + ">" );
                NWNX_Funcs.BootPCWithMessage(pc, 16782504);
            }
        }
    }
}
