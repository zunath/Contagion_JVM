package contagionJVM.System;

import contagionJVM.Entities.AuthorizedDMEntity;
import contagionJVM.Entities.PCAuthorizedCDKeyEntity;
import contagionJVM.Helper.ColorToken;
import contagionJVM.NWNX.NWNX_Funcs;
import contagionJVM.Repository.AuthorizedDMRepository;
import contagionJVM.Repository.PCAuthorizedCDKeysRepository;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.Scheduler;

public class PlayerAuthorizationSystem {


    public static void OnModuleEnter()
    {
        NWObject oPC = NWScript.getEnteringObject();
        if(!NWScript.getIsPC(oPC) || NWScript.getIsDM(oPC)) return;
        String account = NWScript.getPCPlayerName(oPC);
        PCAuthorizedCDKeysRepository repo = new PCAuthorizedCDKeysRepository();
        PCAuthorizedCDKeyEntity entity = repo.GetByAccountName(account);

        if(entity != null && entity.isAddingKey())
        {
            AddNewKey(oPC);
        }
        else
        {
            ValidatePlayerCDKey(oPC);
        }
    }


    private static boolean CDKeyExistsForAccount(PCAuthorizedCDKeyEntity entity, String cdKey)
    {
        return !(!entity.getCdKey1().equals(cdKey) &&
                !entity.getCdKey2().equals(cdKey) &&
                !entity.getCdKey3().equals(cdKey) &&
                !entity.getCdKey4().equals(cdKey) &&
                !entity.getCdKey5().equals(cdKey) &&
                !entity.getCdKey6().equals(cdKey) &&
                !entity.getCdKey7().equals(cdKey) &&
                !entity.getCdKey8().equals(cdKey) &&
                !entity.getCdKey9().equals(cdKey) &&
                !entity.getCdKey10().equals(cdKey));
    }

    private static void AddNewKey(final NWObject oPC)
    {
        PCAuthorizedCDKeysRepository repo = new PCAuthorizedCDKeysRepository();
        String account = NWScript.getPCPlayerName(oPC);
        PCAuthorizedCDKeyEntity entity = repo.GetByAccountName(account);
        final String cdKey = NWScript.getPCPublicCDKey(oPC, false);
        entity.setIsAddingKey(false);

        if(CDKeyExistsForAccount(entity, cdKey))
        {
            Scheduler.delay(oPC, 8000, new Runnable() {
                @Override
                public void run() {
                    NWScript.floatingTextStringOnCreature(ColorToken.Red() + "Unable to add CD key to account. CD key already exists!" + ColorToken.End(), oPC, false);
                }
            });
        }
        else
        {
            if(entity.getCdKey1().equals("")) entity.setCdKey1(cdKey);
            else if(entity.getCdKey2().equals("")) entity.setCdKey2(cdKey);
            else if(entity.getCdKey3().equals("")) entity.setCdKey3(cdKey);
            else if(entity.getCdKey4().equals("")) entity.setCdKey4(cdKey);
            else if(entity.getCdKey5().equals("")) entity.setCdKey5(cdKey);
            else if(entity.getCdKey6().equals("")) entity.setCdKey6(cdKey);
            else if(entity.getCdKey7().equals("")) entity.setCdKey7(cdKey);
            else if(entity.getCdKey8().equals("")) entity.setCdKey8(cdKey);
            else if(entity.getCdKey9().equals("")) entity.setCdKey9(cdKey);
            else if(entity.getCdKey10().equals("")) entity.setCdKey10(cdKey);
            else
            {
                // Out of slots. Boot with message stating so.
                NWNX_Funcs.BootPCWithMessage(oPC, 16782505);
                return;
            }

            Scheduler.delay(oPC, 8000, new Runnable() {
                @Override
                public void run() {
                    NWScript.floatingTextStringOnCreature(ColorToken.Green() + "CD key added to your account successfully. ( " + cdKey + " )" + ColorToken.End(), oPC, false);
                }
            });
        }

        repo.Save(entity);
    }

    private static void ValidatePlayerCDKey(NWObject oPC)
    {
        PCAuthorizedCDKeysRepository repo = new PCAuthorizedCDKeysRepository();
        String account = NWScript.getPCPlayerName(oPC);
        PCAuthorizedCDKeyEntity entity = repo.GetByAccountName(account);
        String cdKey = NWScript.getPCPublicCDKey(oPC, false);

        if(entity == null)
        {
            entity = new PCAuthorizedCDKeyEntity();
            entity.setAccountID(account);
            entity.setCdKey1(cdKey);
            repo.Save(entity);
        }

        if(!CDKeyExistsForAccount(entity, cdKey))
        {
            NWNX_Funcs.BootPCWithMessage(oPC, 16782506);
        }
    }

    public static boolean IsPCRegisteredAsDM(NWObject oPC)
    {
        if(NWScript.getIsDM(oPC)) return true;
        if(!NWScript.getIsPC(oPC)) return false;

        String cdKey = NWScript.getPCPublicCDKey(oPC, false);
        AuthorizedDMRepository repo = new AuthorizedDMRepository();

        AuthorizedDMEntity entity = repo.getByCDKey(cdKey);
        return entity != null;

    }
}
