package contagionJVM.GameObject;

import contagionJVM.Enumerations.CustomItemProperty;
import contagionJVM.Helper.ItemPropertyHelper;
import org.nwnx.nwnx2.jvm.NWItemProperty;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

public class ItemGO {
    private String tag;
    private String resref;
    private String script;
    private NWObject item;

    public ItemGO(NWObject oItem)
    {
        this.item = oItem;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getResref() {
        return resref;
    }

    public void setResref(String resref) {
        this.resref = resref;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public int getDurability()
    {
        if(!canHaveDurability()) return -1;

        NWItemProperty[] itemProperties = NWScript.getItemProperties(item);
        for(NWItemProperty ip : itemProperties)
        {
            int ipType = NWScript.getItemPropertyType(ip);

            if(ipType == CustomItemProperty.ItemDurability)
            {
                return 101 - NWScript.getItemPropertyCostTableValue(ip);
            }
        }

        return -1;
    }

    public void setDurability(int durability)
    {
        if(!canHaveDurability()) return;

        if(durability < 0) durability = 0;
        else if(durability > 100) durability = 100;
        int row2DA = 101 - durability;

        ItemPropertyHelper.AddCustomItemProperty(item, CustomItemProperty.ItemDurability, 0, 35, row2DA, 0, 0);
    }


    private boolean canHaveDurability()
    {
        NWItemProperty[] itemProperties = NWScript.getItemProperties(item);

        for(NWItemProperty ip : itemProperties)
        {
            if(NWScript.getItemPropertyType(ip) == CustomItemProperty.ItemDurability)
            {
                return true;
            }
        }

        return false;
    }


}
