package contagionJVM.System;

import contagionJVM.Bioware.AddItemPropertyPolicy;
import contagionJVM.Bioware.XP2;
import contagionJVM.Helper.ColorToken;
import org.nwnx.nwnx2.jvm.NWItemProperty;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.Scheduler;
import org.nwnx.nwnx2.jvm.constants.*;

public class ArmorSystem {

    public static void OnModuleEquipItem()
    {
        final NWObject oPC = NWScript.getPCItemLastEquippedBy();
        final NWObject oItem = NWScript.getPCItemLastEquipped();
        int baseItemType = NWScript.getBaseItemType(oItem);

        if(baseItemType == BaseItem.ARMOR)
        {
            for(NWItemProperty ip : NWScript.getItemProperties(oItem))
            {
                if(NWScript.getItemPropertyType(ip) == ItemProperty.ONHITCASTSPELL)
                {
                    if(NWScript.getItemPropertySubType(ip) == IpConstOnhitCastspell.ONHIT_UNIQUEPOWER)
                    {
                        return;
                    }
                }
            }

            // No item property found. Add it to the armor.
            XP2.IPSafeAddItemProperty(oItem, NWScript.itemPropertyOnHitCastSpell(IpConstOnhitCastspell.ONHIT_UNIQUEPOWER, 40), 0.0f, AddItemPropertyPolicy.ReplaceExisting, false, false);
        }
        else if(baseItemType == BaseItem.BELT)
        {
            if(NWScript.getIsInCombat(oPC) && NWScript.getLocalInt(oItem, "ARMOR_SKIP_MODULE_ON_EQUIP")  == 0)
            {
                NWScript.sendMessageToPC(oPC, ColorToken.Red() + "You cannot equip armor during combat." + ColorToken.End());

                Scheduler.assign(oPC, new Runnable() {
                    @Override
                    public void run() {
                        NWScript.clearAllActions(false);
                        NWScript.actionUnequipItem(oItem);
                    }
                });
            }
            else if(!NWScript.getIsObjectValid(NWScript.getItemInSlot(InventorySlot.CHEST, oPC)))
            {
                NWScript.sendMessageToPC(oPC, ColorToken.Red() + "You must have clothes equipped before armor can be equipped." + ColorToken.End());

                Scheduler.assign(oPC, new Runnable() {
                    @Override
                    public void run() {
                        NWScript.clearAllActions(false);
                        NWScript.actionUnequipItem(oItem);
                    }
                });
            }
        }
    }

    public static void OnModuleUnequipItem()
    {
        final NWObject oPC = NWScript.getPCItemLastUnequippedBy();
        final NWObject oItem = NWScript.getPCItemLastUnequipped();
        int baseItemType = NWScript.getBaseItemType(oItem);

        if(baseItemType != BaseItem.BELT && baseItemType != BaseItem.ARMOR) return;

        if(NWScript.getIsInCombat(oPC) && baseItemType == BaseItem.BELT)
        {
            NWScript.setLocalInt(oItem, "ARMOR_SKIP_MODULE_ON_EQUIP", 1);
            NWScript.sendMessageToPC(oPC, ColorToken.Red() + "You cannot unequip armor during combat." + ColorToken.End());

            Scheduler.assign(oPC, new Runnable() {
                @Override
                public void run() {
                    NWScript.clearAllActions(false);
                    NWScript.actionEquipItem(oItem, InventorySlot.CHEST);
                }
            });

            Scheduler.delay(oPC, 1000, new Runnable() {
                @Override
                public void run() {
                    NWScript.deleteLocalInt(oItem, "ARMOR_SKIP_MODULE_ON_EQUIP");
                }
            });
        }
        else if(baseItemType == BaseItem.ARMOR)
        {
            final NWObject oBelt = NWScript.getItemInSlot(InventorySlot.BELT, oPC);

            if(NWScript.getIsObjectValid(oBelt))
            {
                Scheduler.assign(oPC, new Runnable() {
                    @Override
                    public void run() {
                        NWScript.clearAllActions(false);
                        NWScript.actionUnequipItem(oBelt);
                    }
                });
            }
        }
    }

}
