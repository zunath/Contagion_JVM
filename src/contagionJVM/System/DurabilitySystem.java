package contagionJVM.System;

import contagionJVM.Constants;
import contagionJVM.GameObject.ItemGO;
import contagionJVM.Helper.ColorToken;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.Scheduler;

public class DurabilitySystem {
    private static final int DurabilityLossChance = 2;

    public static void OnModuleEquip()
    {
        NWObject oPC = NWScript.getPCItemLastEquippedBy();
        final NWObject oItem = NWScript.getPCItemLastEquipped();
        ItemGO itemGO = new ItemGO(oItem);
        int iDurability = itemGO.getDurability();

        if(iDurability == 0)
        {
            Scheduler.assign(oPC, new Runnable() {
                @Override
                public void run() {
                    NWScript.clearAllActions(false);
                    NWScript.actionUnequipItem(oItem);
                }
            });

            NWScript.floatingTextStringOnCreature(ColorToken.Red() + "That item is broken and must be repaired before you can use it." + ColorToken.End(), oPC, false);
        }
    }

    public static void RunItemDecay(NWObject oPC, NWObject oItem, int decayChanceModifier, int decayAmountModifier, boolean displayMessage)
    {
        // Item decay doesn't run for any items if Invincible is in effect
        if(NWScript.getPlotFlag(oPC)) return;
        ItemGO itemGO = new ItemGO(oItem);
        
        int iDurability = itemGO.getDurability();
        
        int iRoll = NWScript.random(100) + 1;
        String sItemName = NWScript.getName(oItem, true);
        // Take the base decay loss chance and subtract it by player's skill in Maintenance.
        int iDC = DurabilityLossChance + decayChanceModifier;

        // Player failed the roll - lower durability by a few points
        if(iRoll <= iDC)
        {
            // Lose 1-4 points plus iDecayAmountModifier
            iRoll = NWScript.random(4) + 1 + decayAmountModifier;
            if(iRoll < 1){iRoll = 1;}

            iDurability = iDurability - iRoll;
            // Durability has hit 0% - force PC to unequip the item immediately by copying it and destroying the old one
            if(iDurability <= 0)
            {
                NWObject oCopy = NWScript.copyItem(oItem, oPC, true);
                NWScript.destroyObject(oItem, 0.0f);
                ItemGO copyGO = new ItemGO(oCopy);
                copyGO.setDurability(0);
                
                NWScript.deleteLocalInt(oCopy, Constants.GunEquippedTemporaryVariable);

                if(displayMessage)
                {
                    NWScript.sendMessageToPC(oPC, ColorToken.Red() + "Your " + sItemName + " has broken!" + ColorToken.End());
                }
            }
            // Update the item's durability and change its name
            else
            {
                // Inform player that their weapon has decayed
                if(displayMessage)
                {
                    NWScript.sendMessageToPC(oPC, ColorToken.Red() + "Your " + sItemName + " has been damaged. (" + iDurability + "%)" + ColorToken.End());
                }
                itemGO.setDurability(iDurability);
            }
        }
    }

    public static void RunItemRepair(NWObject oPC, NWObject oItem, int amount)
    {
        // Prevent repairing for less than 1%
        if(amount < 1) return;
        ItemGO itemGO = new ItemGO(oItem);

        // Item has no durability - inform player they can't repair it
        if(itemGO.getDurability() == -1)
        {
            NWScript.sendMessageToPC(oPC, ColorToken.Red() + "You cannot repair that item." + ColorToken.End());
            return;
        }

        itemGO.setDurability(itemGO.getDurability() + amount);

        NWScript.sendMessageToPC(oPC, ColorToken.Green() + "You repaired your " + NWScript.getName(oItem, true) + ". (" + itemGO.getDurability() + "%)" + ColorToken.End());
    }


}
