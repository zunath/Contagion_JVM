package contagionJVM.System;

import contagionJVM.NWNX.NWNX_Funcs;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.constants.Ability;
import org.nwnx.nwnx2.jvm.constants.Feat;
import org.nwnx.nwnx2.jvm.constants.SavingThrow;

public class ProgressionSystem {

    public void InitializePlayer(NWObject oPC, boolean resetFeats)
    {
        if(!NWScript.getIsPC(oPC)) return;

        if(resetFeats)
        {
            int numberOfFeats = NWNX_Funcs.GetTotalKnownFeats(oPC);
            for(int currentFeat = 1; currentFeat <= numberOfFeats; currentFeat++)
            {
                NWNX_Funcs.SetKnownFeat(oPC, currentFeat, -1);
            }

            NWNX_Funcs.AddKnownFeat(oPC, Feat.ARMOR_PROFICIENCY_LIGHT, -1);
            NWNX_Funcs.AddKnownFeat(oPC, Feat.ARMOR_PROFICIENCY_MEDIUM, -1);
            NWNX_Funcs.AddKnownFeat(oPC, Feat.ARMOR_PROFICIENCY_HEAVY, -1);
            NWNX_Funcs.AddKnownFeat(oPC, Feat.SHIELD_PROFICIENCY, -1);
            NWNX_Funcs.AddKnownFeat(oPC, Feat.WEAPON_PROFICIENCY_EXOTIC, -1);
            NWNX_Funcs.AddKnownFeat(oPC, Feat.WEAPON_PROFICIENCY_MARTIAL, -1);
            NWNX_Funcs.AddKnownFeat(oPC, Feat.WEAPON_PROFICIENCY_SIMPLE, -1);
            NWNX_Funcs.AddKnownFeat(oPC, 1116, -1); // Reload

            NWNX_Funcs.SetAbilityScore(oPC, Ability.STRENGTH, 10);
            NWNX_Funcs.SetAbilityScore(oPC, Ability.DEXTERITY, 10);
            NWNX_Funcs.SetAbilityScore(oPC, Ability.CONSTITUTION, 10);
            NWNX_Funcs.SetAbilityScore(oPC, Ability.WISDOM, 10);
            NWNX_Funcs.SetAbilityScore(oPC, Ability.CHARISMA, 10);
            NWNX_Funcs.SetAbilityScore(oPC, Ability.INTELLIGENCE, 10);

            NWNX_Funcs.SetMaxHitPointsByLevel(oPC, 1, 30);
            NWNX_Funcs.SetCurrentHitPoints(oPC, NWScript.getMaxHitPoints(oPC));

            for(int iCurSkill = 1; iCurSkill <= 27; iCurSkill++)
            {
                NWNX_Funcs.SetSkillRank(oPC, iCurSkill, 0);
            }
            NWNX_Funcs.SetSavingThrowBonus(oPC, SavingThrow.FORT, 0);
            NWNX_Funcs.SetSavingThrowBonus(oPC, SavingThrow.REFLEX, 0);
            NWNX_Funcs.SetSavingThrowBonus(oPC, SavingThrow.WILL, 0);
        }

    }

}
