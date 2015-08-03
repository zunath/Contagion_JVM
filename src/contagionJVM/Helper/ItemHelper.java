package contagionJVM.Helper;

import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

public class ItemHelper {

    public static void ReduceItemStack(NWObject item)
    {
        int stackSize = NWScript.getItemStackSize(item);
        if(stackSize > 1)
        {
            NWScript.setItemStackSize(item, stackSize-1);
        }
        else
        {
            NWScript.destroyObject(item, 0.0f);
        }
    }

}
