package contagionJVM.GameObject;

import contagionJVM.Bioware.AddItemPropertyPolicy;
import contagionJVM.Bioware.XP2;
import contagionJVM.Enumerations.CustomItemProperty;
import contagionJVM.NWNX.NWNX_Structs;
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

    public ItemGO(String tag, String resref, String script) {
        this.setTag(tag);
        this.setResref(resref);
        this.setScript(script);
        this.item = NWObject.INVALID;
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
        NWItemProperty[] itemProperties = NWScript.getItemProperties(item);
        for(NWItemProperty ip : itemProperties)
        {
            int ipType = NWScript.getItemPropertyType(ip);

            if(ipType == CustomItemProperty.ItemDurability)
            {
                return NWScript.getItemPropertyCostTableValue(ip);
            }
        }

        return -1;
    }

    public void setDurability(int durability)
    {
        if(durability < 0) durability = 0;
        else if(durability > 100) durability = 100;

        int row2DA = 100 - durability;

        NWItemProperty durabilityIP = NWNX_Structs.ItemPropertyDirect(CustomItemProperty.ItemDurability, 0, 35, row2DA, 0, 0);
        XP2.IPSafeAddItemProperty(item, durabilityIP, 0.0f, AddItemPropertyPolicy.ReplaceExisting, false, true);

    }



}
