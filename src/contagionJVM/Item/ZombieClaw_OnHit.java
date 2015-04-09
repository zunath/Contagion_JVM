package contagionJVM.Item;

import contagionJVM.Helper.ColorToken;
import contagionJVM.IScriptEventHandler;
import contagionJVM.System.DiseaseSystem;
import contagionJVM.System.ProgressionSystem;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.constants.Spell;

@SuppressWarnings("UnusedDeclaration")
public class ZombieClaw_OnHit implements IScriptEventHandler {
    @Override
    public void runScript(final NWObject oZombie) {
        NWObject oBite = NWScript.getSpellCastItem();
        NWObject oPC = NWScript.getSpellTargetObject();
        int iChanceToInfect = NWScript.random(100) + 1;
        String itemTag = NWScript.getTag(oBite);
        if (!NWScript.getIsPC(oPC)) return;

        if (itemTag.equals("rotd_zombie_bite") &&
                iChanceToInfect <= 80 &&
                !NWScript.getHasSpellEffect(Spell.SANCTUARY, oPC))
        {
            int iDiseaseCheck = NWScript.random(20) + 1;
            int iDiseaseDC = DiseaseSystem.DCCheck + NWScript.random(6);

            // Disease Resistance grants a bonus of +1 infection resistance per point
            int iDiseaseResistance = ProgressionSystem.GetPlayerSkillLevel(oPC, ProgressionSystem.SkillType_DISEASE_RESISTANCE);

            // Add the bonus to the initial roll
            iDiseaseCheck = iDiseaseCheck + iDiseaseResistance;

            NWScript.sendMessageToPC(oPC, ColorToken.SkillCheck() + "Resist disease roll: " + iDiseaseCheck + " VS " + iDiseaseDC + ColorToken.End());

            // Player failed the DC, give them 1% - 12% infection increase
            if (iDiseaseCheck < iDiseaseDC)
            {
                DiseaseSystem.IncreaseDiseaseLevel(oPC, NWScript.random(12) + 1);
            }
        }
    }
}
