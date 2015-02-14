package contagionJVM.Helper;

import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

public class ItemPropertyHelper {

    // Direct calls to NWNX_Structs's SetItemPropertyInteger don't work from Java.
    // For this reason, I've had to make a script that calls the methods inside NWScript.
    public static void AddCustomItemProperty(NWObject oItem,
                                             int type,
                                             int subType,
                                             int costTable,
                                             int costValue,
                                             int paramTable,
                                             int paramValue)
    {
        NWScript.setLocalInt(NWObject.MODULE, "jvm_ipdirect_type", type);
        NWScript.setLocalInt(NWObject.MODULE, "jvm_ipdirect_subtype", subType);
        NWScript.setLocalInt(NWObject.MODULE, "jvm_ipdirect_costtable", costTable);
        NWScript.setLocalInt(NWObject.MODULE, "jvm_ipdirect_costvalue", costValue);
        NWScript.setLocalInt(NWObject.MODULE, "jvm_ipdirect_paramtable", paramTable);
        NWScript.setLocalInt(NWObject.MODULE, "jvm_ipdirect_paramvalue", paramValue);

        NWScript.executeScript("jvm_ipdirect", oItem);
    }
}
