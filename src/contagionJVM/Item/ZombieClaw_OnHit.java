package contagionJVM.Item;

import contagionJVM.Helper.ColorToken;
import contagionJVM.IScriptEventHandler;
import contagionJVM.System.DiseaseSystem;
import contagionJVM.System.ProgressionSystem;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.constants.Spell;

import java.util.Objects;

public class ZombieClaw_OnHit implements IScriptEventHandler {
    @Override
    public void runScript(final NWObject oZombie) {
        NWObject oBite = NWScript.getSpellCastItem();
        NWObject oPC = NWScript.getSpellTargetObject();
        int iChanceToInfect = NWScript.random(100) + 1;

        if (!NWScript.getIsPC(oPC)) return;

        // Fires only for the zombie bite, only if it's valid, and only 80% of the hits
        if (Objects.equals(NWScript.getTag(oBite), "rotd_zombie_bite") && iChanceToInfect <= 80 && !NWScript.getHasSpellEffect(Spell.SANCTUARY, oPC))
        {
            int iDiseaseCheck = NWScript.random(20) + 1;
            int iDiseaseDC = DiseaseSystem.DCCheck + NWScript.random(4);

            // Disease Resistance grants a bonus of +1 infection resistance per point
            int iDiseaseResistance = ProgressionSystem.GetPlayerSkillLevel(oPC, ProgressionSystem.SkillType_DISEASE_RESISTANCE);


            // Add the bonus to the initial roll
            iDiseaseCheck = iDiseaseCheck + iDiseaseResistance;

            NWScript.sendMessageToPC(oPC, ColorToken.SkillCheck() + "Resist disease roll: " + iDiseaseCheck + " VS " + iDiseaseDC + ColorToken.End());

            // Player failed the DC, give them 1% - 12% infection increase
            if (iDiseaseCheck < iDiseaseDC)
            {
                DiseaseSystem system = new DiseaseSystem();
                system.IncreaseDiseaseLevel(oPC, NWScript.random(12) + 1);
            }
        }
    }
}
