package contagionJVM.Event;

import contagionJVM.IScriptEventHandler;
import contagionJVM.NWNX.NWNX_Events;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

public class Module_OnCastSpell implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {

        NWObject oPC = objSelf;
        NWObject oTarget = NWNX_Events.GetEventTarget();
        NWObject oItem = NWNX_Events.GetEventItem();
        String sTag = NWScript.getTag(oItem);
        int nEvent   = NWNX_Events.GetEventType();
        int nSubtype = NWNX_Events.GetEventSubType();

        NWScript.sendMessageToPC(oPC, "oItem = " + NWScript.getName(oItem, false)); // DEBUG
        NWScript.sendMessageToPC(oPC, "oTarget = " + NWScript.getName(oTarget, false)); // DEBUG
        NWScript.sendMessageToPC(oPC, "nEvent = " + nEvent); // DEBUG
        NWScript.sendMessageToPC(oPC, "nSubtype = " + nSubtype); // DEBUG

        // Tag based scripting
        NWScript.executeScript(sTag, oPC);

        NWNX_Events.BypassEvent();
    }
}
