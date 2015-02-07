package contagionJVM.Authorization;

import contagionJVM.Entities.AuthorizedDMEntity;
import contagionJVM.IScriptEventHandler;
import contagionJVM.Repository.AuthorizedDMRepository;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

@SuppressWarnings("unused")
public class ValidateAdmin implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {

        int isAdmin = 0;
        String cdKey = NWScript.getPCPublicCDKey(objSelf, false);
        AuthorizedDMRepository repo = new AuthorizedDMRepository();
        AuthorizedDMEntity entity = repo.getByCDKey(cdKey);

        if(entity != null && entity.getDMRole() == 2) // 2 = Admin
        {
            isAdmin = 1;
        }

        NWScript.setLocalInt(objSelf, "AUTH_IS_ADMIN", isAdmin);
    }
}
