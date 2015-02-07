package contagionJVM.Event;

import contagionJVM.Bioware.XP2;
import contagionJVM.Constants;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.IScriptEventHandler;
import contagionJVM.NWNX.NWNX_Events;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.constants.IpConst;

public class Module_OnUseItem implements IScriptEventHandler {

    // NWNX Events for Linux allows us to bypass the animation that plays when you use an item.
    // The trade off for this is that you have to rework the way items are used. This script
    // handles all variations of item properties that may be used. If the item doesn't fall
    // into one of these categories then the generic tag based scripting takes over.
    //
    // Created by Zunath on July 21, 2011
    @Override
    public void runScript(NWObject objSelf) {
        NWObject oPC = objSelf;
        PlayerGO pcGO = new PlayerGO(oPC);

        NWObject oTarget = NWNX_Events.GetEventTarget();
        NWObject oItem = NWNX_Events.GetEventItem();
        NWObject oDatabase = pcGO.GetDatabaseItem();
        String sTag = NWScript.getTag(oItem);
        int iEvent   = NWNX_Events.GetEventType();
        int iSubtype = NWNX_Events.GetEventSubType();

        // Change Ammo Priority Property
        boolean bAmmoPriority = XP2.IPGetItemHasProperty(oItem, NWScript.itemPropertyCastSpell(548, IpConst.CASTSPELL_NUMUSES_UNLIMITED_USE), -1, false);
        // Change Firing Mode Property
        boolean bChangeFiringMode = XP2.IPGetItemHasProperty(oItem, NWScript.itemPropertyCastSpell(546, IpConst.CASTSPELL_NUMUSES_UNLIMITED_USE), -1, false);
        // Combine Property
        boolean bCombine = XP2.IPGetItemHasProperty(oItem, NWScript.itemPropertyCastSpell(547, IpConst.CASTSPELL_NUMUSES_UNLIMITED_USE), -1, false);
        // Unique Power: Self Only Property
        boolean bActivateSelf = XP2.IPGetItemHasProperty(oItem, NWScript.itemPropertyCastSpell(335, IpConst.CASTSPELL_NUMUSES_UNLIMITED_USE), -1, false);
        // Toggle Radio Power Property
        boolean bRadioPower = XP2.IPGetItemHasProperty(oItem, NWScript.itemPropertyCastSpell(549, IpConst.CASTSPELL_NUMUSES_UNLIMITED_USE), -1, false);
        // Change Radio Channel Property
        boolean bRadioChannel = XP2.IPGetItemHasProperty(oItem, NWScript.itemPropertyCastSpell(550, IpConst.CASTSPELL_NUMUSES_UNLIMITED_USE), -1, false);
        // Use Lockpick Property
        boolean bLockpick = XP2.IPGetItemHasProperty(oItem, NWScript.itemPropertyCastSpell(551, IpConst.CASTSPELL_NUMUSES_UNLIMITED_USE), -1, false);


        String sChangeAmmoScript = "gun_changeammo";
        String sChangeModeScript = "gun_changemode";
        String sCombineScript = "reo_combine";
        String sRadioTogglePowerScript = "radio_toggpower";
        String sRadioChangeChannelScript = "radio_changechan";
        String sUseLockpickScript = "item_lockpick";

        boolean bBypassEvent = true;

        // Firearms - Ammo Priority (0), Change Firing Mode (1), Combine (2)
        if(bAmmoPriority && bCombine && bChangeFiringMode)
        {
            if(iSubtype == 0)
            {
                NWScript.executeScript(sChangeAmmoScript, oPC);
            }
            else if(iSubtype == 1)
            {
                NWScript.executeScript(sChangeModeScript, oPC);
            }
            else if(iSubtype == 2)
            {
                NWScript.executeScript(sCombineScript, oPC);
            }
        }
        // Firearms - Ammo Priority (0), Change Firing Mode (1)
        else if(bAmmoPriority && bChangeFiringMode)
        {
            if(iSubtype == 0)
            {
                NWScript.executeScript(sChangeAmmoScript, oPC);
            }
            else if(iSubtype == 1)
            {
                NWScript.executeScript(sChangeModeScript, oPC);
            }
        }
        // Firearms - Ammo Priority (0), Combine (1)
        else if(bAmmoPriority && bCombine)
        {
            if(iSubtype == 0)
            {
                NWScript.executeScript(sChangeAmmoScript, oPC);
            }
            else if(iSubtype == 1)
            {
                NWScript.executeScript(sCombineScript, oPC);
            }
        }
        // Firearms - Ammo Priority (0)
        else if(bAmmoPriority)
        {
            if(iSubtype == 0)
            {
                NWScript.executeScript(sChangeAmmoScript, oPC);
            }
        }

        // Unique Power Self Only and Combine
        else if(bCombine && bActivateSelf)
        {
            // Combine
            if(iSubtype == 0)
            {
                NWScript.executeScript(sCombineScript, oPC);
            }
            else if(iSubtype == 1)
            {
                bBypassEvent = false;
            }
        }

        // Combine (0)
        else if(bCombine)
        {
            if(iSubtype == 0)
            {
                NWScript.executeScript(sCombineScript, oPC);
            }
        }

        // Change Radio Channel (0) and Toggle Radio Power (1)
        else if(bRadioPower && bRadioChannel)
        {
            bBypassEvent = true;
            // Change Radio Channel
            if(iSubtype == 0)
            {
                NWScript.executeScript(sRadioChangeChannelScript, oPC);
            }
            // Toggle Radio Power
            else if(iSubtype == 1)
            {
                NWScript.executeScript(sRadioTogglePowerScript, oPC);
            }
        }

        // Use Lockpick (0)
        else if(bLockpick)
        {
            bBypassEvent = true;
            NWScript.executeScript(sUseLockpickScript, oPC);
        }

        // All of the updated items. These will all bypass the Use Item animation.
        else if(sTag == Constants.PCDatabaseTag)
        {
            NWScript.executeScript(sTag, objSelf);
            bBypassEvent = true;
        }
        // Fire tag based scripting in all other cases (I.E: Don't bypass this event)
        // Allows for backwards compatibility until we convert other systems over to Linux
        else
        {
            bBypassEvent = false;
        }

        // The entirety of the OnActivateItem will be skipped if bBypassEvent is true.
        if(bBypassEvent)
        {
            NWNX_Events.BypassEvent();
        }
    }
}
