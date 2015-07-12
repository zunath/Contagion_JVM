package contagionJVM.Trigger;

import contagionJVM.Entities.BadgeEntity;
import contagionJVM.Entities.PCBadgeEntity;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.IScriptEventHandler;
import contagionJVM.Repository.BadgeRepository;
import contagionJVM.System.ProgressionSystem;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

import java.sql.Timestamp;

@SuppressWarnings("unused")
public class BadgeTrigger implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        NWObject oPC = NWScript.getEnteringObject();
        PlayerGO pcGO = new PlayerGO(oPC);
        int badgeID = NWScript.getLocalInt(objSelf, "BadgeID");
        BadgeRepository repo = new BadgeRepository();
        PCBadgeEntity pcBadge = repo.GetByID(pcGO.getUUID(), badgeID);

        if(pcBadge == null)
        {
            NWObject oArea = NWScript.getArea(objSelf);
            BadgeEntity entity = repo.GetByID(badgeID);
            if(entity == null) return;

            pcBadge = new PCBadgeEntity();
            pcBadge.setAcquiredAreaName(NWScript.getName(oArea, false));
            pcBadge.setAcquiredAreaResref(NWScript.getResRef(oArea));
            pcBadge.setAcquiredAreaTag(NWScript.getTag(oArea));
            pcBadge.setBadgeID(badgeID);
            pcBadge.setPlayerID(pcGO.getUUID());
            pcBadge.setAcquiredDate(new Timestamp(new DateTime().getMillis()));
            NWScript.floatingTextStringOnCreature("You discovered a new area! Badge acquired: " + entity.getName(), oPC, false);
            ProgressionSystem.GiveExperienceToPC(oPC, entity.getExperience());
            repo.Save(pcBadge);
        }

    }
}
