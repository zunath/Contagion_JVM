package contagionJVM.Helper;

import contagionJVM.NWNX.LocalVariable;
import contagionJVM.NWNX.NWNX_Funcs;
import contagionJVM.NWNX.VariableType;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

public class LocalVariableHelper {

    public static void CopyVariables(NWObject oSource, NWObject oCopy)
    {
        int iVarCount = NWNX_Funcs.GetLocalVariableCount(oSource);
        LocalVariable stCurVar = NWNX_Funcs.GetFirstLocalVariable(oSource);
        for(int iCurVar = 0; iCurVar <= iVarCount; iCurVar++)
        {
            switch(stCurVar.type)
            {
                case VariableType.IntVar:
                    NWScript.setLocalInt(oCopy, stCurVar.name, NWScript.getLocalInt(oSource, stCurVar.name));
                    break;
                case VariableType.FloatVar:
                    NWScript.setLocalFloat(oCopy, stCurVar.name, NWScript.getLocalFloat(oSource, stCurVar.name));
                    break;
                case VariableType.StringVar:
                    NWScript.setLocalString(oCopy, stCurVar.name, NWScript.getLocalString(oSource, stCurVar.name));
                    break;
                case VariableType.ObjectVar:
                    NWScript.setLocalObject(oCopy, stCurVar.name, NWScript.getLocalObject(oSource, stCurVar.name));
                    break;
                case VariableType.LocationVar:
                    NWScript.setLocalLocation(oCopy, stCurVar.name, NWScript.getLocalLocation(oSource, stCurVar.name));
                    break;
            }
            stCurVar = NWNX_Funcs.GetNextLocalVariable(stCurVar);
        }
    }
}
