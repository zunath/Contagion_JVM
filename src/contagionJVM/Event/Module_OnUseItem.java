package contagionJVM.Event;

import contagionJVM.Bioware.XP2;
import contagionJVM.IScriptEventHandler;
import contagionJVM.NWNX.NWNX_Events;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.constants.IpConst;

import java.io.PrintWriter;
import java.io.StringWriter;

@SuppressWarnings("UnusedDeclaration")
public class Module_OnUseItem implements IScriptEventHandler {
    @Override
    public void runScript(NWObject oPC) {

        HandleGeneralItemUses(oPC);
        HandleSpecificItemUses(oPC);

    }

    private void HandleGeneralItemUses(NWObject oPC)
    {
        NWObject oItem = NWNX_Events.GetEventItem();

        String className = NWScript.getLocalString(oItem, "JavaClass");
        if(className.equals("")) return;

        try
        {
            NWNX_Events.BypassEvent();
            Class scriptClass = Class.forName("contagionJVM.Item." + className);
            IScriptEventHandler script = (IScriptEventHandler)scriptClass.newInstance();
            script.runScript(oPC);
        }
        catch (Exception ex)
        {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();

            String message = "Module_OnActivateItem was unable to execute class method: contagionJVM.Item." + className + ".runScript()";
            System.out.println(message);
            System.out.println("Exception: ");
            System.out.println(exceptionAsString);

            NWScript.writeTimestampedLogEntry(message);
            NWScript.writeTimestampedLogEntry("Exception:");
            NWScript.writeTimestampedLogEntry(exceptionAsString);
        }
    }


    private void HandleSpecificItemUses(NWObject oPC)
    {
        NWObject oItem = NWNX_Events.GetEventItem();
        String sTag = NWScript.getTag(oItem);
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
