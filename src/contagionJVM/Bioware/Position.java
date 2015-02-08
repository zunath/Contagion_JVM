package contagionJVM.Bioware;

import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.Scheduler;

public class Position {

    public static void TurnToFaceObject(final NWObject oObjectToFace, NWObject oTarget)
    {
        Scheduler.assign(oTarget, new Runnable() {
            @Override
            public void run() {
                NWScript.setFacingPoint(NWScript.getPosition(oObjectToFace));
            }
        });

        Scheduler.flushQueues();
    }

}
