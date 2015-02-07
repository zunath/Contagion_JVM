package contagionJVM.NWNX;

import org.nwnx.nwnx2.jvm.NWLocation;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.NWVector;

public class NWNX_APS {

    public static void SQLInitialize()
    {
        NWObject module = NWObject.MODULE;

        String memory = "";

        for(int i = 0; i < 8; i++)
        {
            memory += "................................................................................................................................";
        }

        NWScript.setLocalString(module, "NWNX!INIT", "1");
        NWScript.setLocalString(module, "NWNX!ODBC!SPACER", memory);
    }

    @SuppressWarnings("unused")
    public static NWVector StringToVector(String sVector)
    {
        float fX= 0.0f;
        float fY = 0.0f;
        float fZ = 0.0f;
        int iPos, iCount;
        int iLen = NWScript.getStringLength(sVector);

        if (iLen > 0)
        {
            iPos = NWScript.findSubString(sVector, "#POSITION_X#", 0) + 12;
            iCount = NWScript.findSubString(NWScript.getSubString(sVector, iPos, iLen - iPos), "#", 0);
            fX = NWScript.stringToFloat(NWScript.getSubString(sVector, iPos, iCount));

            iPos = NWScript.findSubString(sVector, "#POSITION_Y#", 0) + 12;
            iCount = NWScript.findSubString(NWScript.getSubString(sVector, iPos, iLen - iPos), "#", 0);
            fY = NWScript.stringToFloat(NWScript.getSubString(sVector, iPos, iCount));

            iPos = NWScript.findSubString(sVector, "#POSITION_Z#", 0) + 12;
            iCount = NWScript.findSubString(NWScript.getSubString(sVector, iPos, iLen - iPos), "#", 0);
            fZ = NWScript.stringToFloat(NWScript.getSubString(sVector, iPos, iCount));
        }

        return new NWVector(fX, fY, fZ);
    }

    @SuppressWarnings("unused")
    public static String LocationToString(NWLocation lLocation)
    {
        NWObject oArea = NWScript.getAreaFromLocation(lLocation);
        NWVector vPosition = NWScript.getPositionFromLocation(lLocation);
        float fOrientation = NWScript.getFacingFromLocation(lLocation);
        String sReturnValue = "";

        if (NWScript.getIsObjectValid(oArea))
            sReturnValue = "#AREA#" + NWScript.getTag(oArea) +
                           "#POSITION_X#" + vPosition.getX() +
                           "#POSITION_Y#" + vPosition.getY() +
                           "#POSITION_Z#" + vPosition.getZ() +
                           "#ORIENTATION#" + fOrientation +
                           "#END#";

        return sReturnValue;
    }

    @SuppressWarnings("unused")
    public static NWLocation StringToLocation(String sLocation)
    {
        NWObject oArea;
        NWVector vPosition;
        float fOrientation, fX, fY, fZ;

        int iPos, iCount;
        int iLen = NWScript.getStringLength(sLocation);

        if (iLen > 0)
        {
            iPos = NWScript.findSubString(sLocation, "#AREA#", 0) + 6;
            iCount = NWScript.findSubString(NWScript.getSubString(sLocation, iPos, iLen - iPos), "#", 0);
            oArea = NWScript.getObjectByTag(NWScript.getSubString(sLocation, iPos, iCount), 0);

            iPos = NWScript.findSubString(sLocation, "#POSITION_X#", 0) + 12;
            iCount = NWScript.findSubString(NWScript.getSubString(sLocation, iPos, iLen - iPos), "#", 0);
            fX = NWScript.stringToFloat(NWScript.getSubString(sLocation, iPos, iCount));

            iPos = NWScript.findSubString(sLocation, "#POSITION_Y#", 0) + 12;
            iCount = NWScript.findSubString(NWScript.getSubString(sLocation, iPos, iLen - iPos), "#", 0);
            fY = NWScript.stringToFloat(NWScript.getSubString(sLocation, iPos, iCount));

            iPos = NWScript.findSubString(sLocation, "#POSITION_Z#", 0) + 12;
            iCount = NWScript.findSubString(NWScript.getSubString(sLocation, iPos, iLen - iPos), "#", 0);
            fZ = NWScript.stringToFloat(NWScript.getSubString(sLocation, iPos, iCount));

            vPosition = new NWVector(fX, fY, fZ);

            iPos = NWScript.findSubString(sLocation, "#ORIENTATION#", 0) + 13;
            iCount = NWScript.findSubString(NWScript.getSubString(sLocation, iPos, iLen - iPos), "#", 0);
            fOrientation = NWScript.stringToFloat(NWScript.getSubString(sLocation, iPos, iCount));

            return new NWLocation(oArea, vPosition.getX(), vPosition.getY(), vPosition.getZ(), fOrientation);
        }

        return new NWLocation(null, 0.0f, 0.0f, 0.0f, 0.0f);
    }



}
