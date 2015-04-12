package contagionJVM.Item;

import contagionJVM.GameObject.PlayerGO;
import contagionJVM.Helper.ColorToken;
import contagionJVM.Helper.EffectHelper;
import contagionJVM.IScriptEventHandler;
import contagionJVM.NWNX.NWNX_Funcs;
import contagionJVM.NWNX.NWNX_Structs;
import contagionJVM.System.DiseaseSystem;
import contagionJVM.System.ProgressionSystem;
import org.nwnx.nwnx2.jvm.NWEffect;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.constants.DurationType;
import org.nwnx.nwnx2.jvm.constants.Spell;

import java.util.Random;

@SuppressWarnings("UnusedDeclaration")
public class ZombieClaw_OnHit implements IScriptEventHandler {
    @Override
    public void runScript(final NWObject oZombie) {
        NWObject oPC = NWScript.getSpellTargetObject();
        NWObject oBite = NWScript.getSpellCastItem();
        String itemTag = NWScript.getTag(oBite);
        if (!NWScript.getIsPC(oPC) || !itemTag.equals("reo_zombie_claw") || NWScript.getIsDM(oPC)) return;

        RunInfectionRoutine(oPC);
        RunBleedingRoutine(oPC, oZombie);
    }

    private void RunInfectionRoutine(NWObject oPC)
    {
        Random random = new Random();
        int iChanceToInfect = random.nextInt(100) + 1;

        if (iChanceToInfect <= 80 && !NWScript.getHasSpellEffect(Spell.SANCTUARY, oPC))
        {
            int iDiseaseCheck = NWScript.random(20) + 1;
            int iDiseaseDC = DiseaseSystem.DCCheck + random.nextInt(6);

            // Disease Resistance grants a bonus of +1 infection resistance per point
            int iDiseaseResistance = ProgressionSystem.GetPlayerSkillLevel(oPC, ProgressionSystem.SkillType_DISEASE_RESISTANCE);

            // Add the bonus to the initial roll
            iDiseaseCheck = iDiseaseCheck + iDiseaseResistance;

            NWScript.sendMessageToPC(oPC, ColorToken.SkillCheck() + "Resist disease roll: " + iDiseaseCheck + " VS " + iDiseaseDC + ColorToken.End());

            // Player failed the DC, give them 1% - 12% infection increase
            if (iDiseaseCheck < iDiseaseDC)
            {
                DiseaseSystem.IncreaseDiseaseLevel(oPC, random.nextInt(12) + 1);
            }
        }
    }

    private void RunBleedingRoutine(NWObject oPC, NWObject oZombie)
    {
        PlayerGO pcGO = new PlayerGO(oPC);

        pcGO.setBleedingTicks(10);
    }
}