package contagionJVM.Bioware;

import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.NWVector;
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

    public static float GetChangeInX(float fDistance, float fAngle)
    {
        return (float)(fDistance * Math.cos(fAngle));
    }

    public static float GetChangeInY(float fDistance, float fAngle)
    {
        return (float)(fDistance * Math.sin(fAngle));
    }

    public static NWVector GetChangedPosition(NWVector vOriginal, float fDistance, float fAngle)
    {
        float changedX = vOriginal.getX();
        float changedY = vOriginal.getY();
        float changedZ = vOriginal.getZ();

        changedX = vOriginal.getX() + GetChangeInX(fDistance, fAngle);
        if (changedX < 0.0)
            changedX = - changedX;
        changedY = vOriginal.getY() + GetChangeInY(fDistance, fAngle);
        if (changedY < 0.0)
            changedY = - changedY;

        NWVector vChanged = new NWVector(changedX, changedY, changedZ);
        return vChanged;
    }
    

}
