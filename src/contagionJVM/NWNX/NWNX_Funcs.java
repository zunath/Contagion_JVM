package contagionJVM.NWNX;

import org.nwnx.nwnx2.jvm.NWLocation;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.NWVector;

@SuppressWarnings("UnusedDeclaration")
public class NWNX_Funcs {

    private static int NWNXFuncsZero (NWObject oObject, String sFunc) {
        NWScript.setLocalString(oObject, sFunc, "          ");
        return NWScript.stringToInt(NWScript.getLocalString(oObject, sFunc));
    }

    private static int NWNXFuncsOne (NWObject oObject, String sFunc, int nVal1) {
        NWScript.setLocalString(oObject, sFunc, NWScript.intToString(nVal1) + "          ");
        return NWScript.stringToInt(NWScript.getLocalString(oObject, sFunc));
    }

    private static int NWNXFuncsTwo (NWObject oObject, String sFunc, int nVal1, int nVal2) {
        NWScript.setLocalString(oObject, sFunc, NWScript.intToString(nVal1) + " " + NWScript.intToString(nVal2) + "          ");
        return NWScript.stringToInt(NWScript.getLocalString(oObject, sFunc));
    }

    private static int NWNXFuncsThree (NWObject oObject, String sFunc, int nVal1, int nVal2, int nVal3) {
        NWScript.setLocalString(oObject, sFunc, NWScript.intToString(nVal1) + " " + NWScript.intToString(nVal2) +
                " " + NWScript.intToString(nVal3) + "          ");
        return NWScript.stringToInt(NWScript.getLocalString(oObject, sFunc));
    }


    void USleep (int usec) {
        NWNXFuncsOne(NWObject.MODULE, "NWNX!FUNCS!USLEEP", usec);
    }

    public static TimeValue GetTimeOfDay() {
        TimeValue ret = new TimeValue();
        String sFunc = "NWNX!FUNCS!GETTIMEOFDAY";
        NWScript.setLocalString(NWObject.MODULE,
                sFunc, "                                         ");
        String time = NWScript.getLocalString(NWObject.MODULE, sFunc);
        int idx = NWScript.findSubString(time, ".", 0);
        if (-1 != idx) {
            ret.sec = NWScript.stringToInt(NWScript.getSubString(time, 0, idx));
            ret.usec = NWScript.stringToInt(NWScript.getSubString(time, idx + 1, 32));
        }
        return ret;
    }

    public static int SetAbilityScore (NWObject oCreature, int nAbility, int nValue) {
        return NWNXFuncsTwo(oCreature, "NWNX!FUNCS!SETABILITYSCORE", nAbility, nValue);
    }

    public static int ModifyAbilityScore (NWObject oCreature, int nAbility, int nValue) {
        return NWNXFuncsTwo(oCreature, "NWNX!FUNCS!MODIFYABILITYSCORE", nAbility, nValue);
    }


    public static int SetSkillRank (NWObject oCreature, int nSkill, int nValue) {
        return NWNXFuncsTwo(oCreature, "NWNX!FUNCS!SETSKILLRANK", nSkill, nValue);
    }

    public static int ModifySkillRank (NWObject oCreature, int nSkill, int nValue) {
        return NWNXFuncsTwo(oCreature, "NWNX!FUNCS!MODIFYSKILLRANK", nSkill, nValue);
    }

    public static int ModifySkillRankByLevel (NWObject oCreature, int nLevel, int nSkill, int nValue) {
        return NWNXFuncsThree(oCreature, "NWNX!FUNCS!MODIFYSKILLRANKBYLEVEL", nLevel, nSkill, nValue);
    }


    public static int GetACNaturalBase (NWObject oCreature) {
        return NWNXFuncsZero(oCreature, "NWNX!FUNCS!GETACNATURALBASE");
    }

    public static int SetACNaturalBase (NWObject oCreature, int nAC) {
        return NWNXFuncsOne(oCreature, "NWNX!FUNCS!SETACNATURALBASE", nAC);
    }


    public static int GetKnowsFeat (int nFeatId, NWObject oCreature) {
        return NWNXFuncsOne(oCreature, "NWNX!FUNCS!GETKNOWSFEAT", nFeatId);
    }

    public static int AddKnownFeat (NWObject oCreature, int nFeat, int nLevel) {
        if (nLevel == 0)
            nLevel = NWScript.getHitDice(oCreature);

        if (nLevel > 0)
            return NWNXFuncsTwo(oCreature, "NWNX!FUNCS!ADDKNOWNFEATATLEVEL", nLevel, nFeat);

        return NWNXFuncsOne(oCreature, "NWNX!FUNCS!ADDKNOWNFEAT", nFeat);
    }

    public static int RemoveKnownFeat (NWObject oCreature, int nFeat) {
        return NWNXFuncsOne(oCreature, "NWNX!FUNCS!REMOVEKNOWNFEAT", nFeat);
    }


    public static int GetTotalKnownFeats (NWObject oCreature) {
        return NWNXFuncsZero(oCreature, "NWNX!FUNCS!GETTOTALKNOWNFEATS");
    }

    public static int GetKnownFeat (NWObject oCreature, int nIndex) {
        return NWNXFuncsOne(oCreature, "NWNX!FUNCS!GETKNOWNFEAT", nIndex);
    }

    public static int SetKnownFeat (NWObject oCreature, int nIndex, int nFeat) {
        return NWNXFuncsTwo(oCreature, "NWNX!FUNCS!SETKNOWNFEAT", nIndex, nFeat);
    }


    public static int GetTotalKnownFeatsByLevel (NWObject oCreature, int nLevel) {
        return NWNXFuncsOne(oCreature, "NWNX!FUNCS!GETTOTALKNOWNFEATSBYLEVEL", nLevel);
    }

    public static int GetKnownFeatByLevel (NWObject oCreature, int nLevel, int nIndex) {
        return NWNXFuncsTwo(oCreature, "NWNX!FUNCS!GETKNOWNFEATBYLEVEL", nLevel, nIndex);
    }

    public static int SetKnownFeatByLevel (NWObject oCreature, int nLevel, int nIndex, int nFeat) {
        return NWNXFuncsThree(oCreature, "NWNX!FUNCS!SETKNOWNFEATBYLEVEL", nLevel, nIndex, nFeat);
    }


    public static int GetRemainingFeatUses (NWObject oCreature, int nFeat) {
        return NWNXFuncsOne(oCreature, "NWNX!FUNCS!GETREMAININGFEATUSES", nFeat);
    }

    public static int GetTotalFeatUses (NWObject oCreature, int nFeat) {
        return NWNXFuncsOne(oCreature, "NWNX!FUNCS!GETTOTALFEATUSES", nFeat);
    }


    public static String GetAllRemainingFeatUses (NWObject oCreature) {
        String sFeats = NWScript.getLocalString(NWObject.MODULE, "NWNX!ODBC!SPACER");

        NWScript.setLocalString(oCreature, "NWNX!FUNCS!GETALLREMAININGFEATUSES", sFeats + sFeats + sFeats + sFeats);
        sFeats = NWScript.getLocalString(oCreature, "NWNX!FUNCS!GETALLREMAININGFEATUSES");
        NWScript.deleteLocalString(oCreature, "NWNX!FUNCS!GETALLREMAININGFEATUSES");

        return sFeats;
    }

    public static int RestoreReadyFeats (NWObject oCreature, String sFeats) {
        NWScript.setLocalString(oCreature, "NWNX!FUNCS!RESTOREREADYFEATS", sFeats + " ");
        return NWScript.stringToInt(NWScript.getLocalString(oCreature, "NWNX!FUNCS!RESTOREREADYFEATS"));
    }


    public static int GetMeetsFeatRequirements (NWObject oCreature, int nFeat) {
        return NWNXFuncsOne(oCreature, "NWNX!FUNCS!GETMEETSFEATREQUIREMENTS", nFeat);
    }

    public static int GetMeetsLevelUpFeatRequirements (NWObject oCreature, int nFeat, int nClass, int nAbility, CreatureSkills sk) {
        NWScript.setLocalString(oCreature, "NWNX!FUNCS!GETMEETSFEATREQUIREMENTS", ">" +
                NWScript.intToString(nFeat) + " " +
                NWScript.intToString(nClass) + " " +
                NWScript.intToString(nAbility) + " ¬" +
                NWScript.intToString(sk.sk_aniemp) + "¬" +
                NWScript.intToString(sk.sk_conc) + "¬" +
                NWScript.intToString(sk.sk_distrap) + "¬" +
                NWScript.intToString(sk.sk_disc) + "¬" +
                NWScript.intToString(sk.sk_heal) + "¬" +
                NWScript.intToString(sk.sk_hide) + "¬" +
                NWScript.intToString(sk.sk_listen) + "¬" +
                NWScript.intToString(sk.sk_lore) + "¬" +
                NWScript.intToString(sk.sk_movesil) + "¬" +
                NWScript.intToString(sk.sk_openlock) + "¬" +
                NWScript.intToString(sk.sk_parry) + "¬" +
                NWScript.intToString(sk.sk_perform) + "¬" +
                NWScript.intToString(sk.sk_persuade) + "¬" +
                NWScript.intToString(sk.sk_ppocket) + "¬" +
                NWScript.intToString(sk.sk_search) + "¬" +
                NWScript.intToString(sk.sk_settrap) + "¬" +
                NWScript.intToString(sk.sk_spcraft) + "¬" +
                NWScript.intToString(sk.sk_spot) + "¬" +
                NWScript.intToString(sk.sk_taunt) + "¬" +
                NWScript.intToString(sk.sk_umd) + "¬" +
                NWScript.intToString(sk.sk_appraise) + "¬" +
                NWScript.intToString(sk.sk_tumble) + "¬" +
                NWScript.intToString(sk.sk_ctrap) + "¬" +
                NWScript.intToString(sk.sk_bluff) + "¬" +
                NWScript.intToString(sk.sk_intim) + "¬" +
                NWScript.intToString(sk.sk_carmor) + "¬" +
                NWScript.intToString(sk.sk_cweapon) + "¬" +
                NWScript.intToString(sk.sk_ride));
        return NWScript.stringToInt(NWScript.getLocalString(oCreature, "NWNX!FUNCS!GETMEETSFEATREQUIREMENTS"));
    }


    public static int GetIsClassBonusFeat (int nClass, int nFeat) {
        return NWNXFuncsTwo(NWObject.MODULE, "NWNX!FUNCS!GETISCLASSBONUSFEAT", nClass, nFeat);
    }

    public static int GetIsClassGeneralFeat (int nClass, int nFeat) {
        return NWNXFuncsTwo(NWObject.MODULE, "NWNX!FUNCS!GETISCLASSGENERALFEAT", nClass, nFeat);
    }

    public static int GetIsClassGrantedFeat (int nClass, int nFeat) {
        return NWNXFuncsTwo(NWObject.MODULE, "NWNX!FUNCS!GETISCLASSGRANTEDFEAT", nClass, nFeat);
    }

    public static int GetIsClassSkill (int nClass, int nSkill) {
        return NWNXFuncsTwo(NWObject.MODULE, "NWNX!FUNCS!GETISCLASSSKILL", nClass, nSkill);
    }


    public static int GetClassByLevel (NWObject oCreature, int nLevel) {
        return NWNXFuncsOne(oCreature, "NWNX!FUNCS!GETCLASSBYLEVEL", nLevel);
    }

    public static int GetAbilityIncreaseByLevel (NWObject oCreature, int nLevel) {
        return NWNXFuncsOne(oCreature, "NWNX!FUNCS!GETABILITYINCREASEBYLEVEL", nLevel);
    }

    public static int GetSkillIncreaseByLevel (NWObject oCreature, int nLevel, int nSkill) {
        return NWNXFuncsTwo(oCreature, "NWNX!FUNCS!GETSKILLINCREASEBYLEVEL", nLevel, nSkill);
    }


    public static int GetSavingThrowBonus (NWObject oCreature, int nSave) {
        return NWNXFuncsOne(oCreature, "NWNX!FUNCS!GETSAVINGTHROWBONUS", nSave);
    }

    public static int SetSavingThrowBonus (NWObject oCreature, int nSave, int nValue) {
        return NWNXFuncsTwo(oCreature, "NWNX!FUNCS!SETSAVINGTHROWBONUS", nSave, nValue);
    }

    public static int ModifySavingThrowBonus (NWObject oCreature, int nSave, int nValue) {
        if (nSave < 1 || nSave > 3)
            return -1;

        int nBonus = GetSavingThrowBonus(oCreature, nSave) + nValue;

        if (nBonus < 0)
            nBonus = 0;
        else if (nBonus > 127)
            nBonus = 127;

        return SetSavingThrowBonus(oCreature, nSave, nBonus);
    }


    public static int GetMaxHitPointsByLevel (NWObject oCreature, int nLevel) {
        return NWNXFuncsOne(oCreature, "NWNX!FUNCS!GETMAXHITPOINTSBYLEVEL", nLevel);
    }

    public static int SetMaxHitPointsByLevel (NWObject oCreature, int nLevel, int nValue) {
        return NWNXFuncsTwo(oCreature, "NWNX!FUNCS!SETMAXHITPOINTSBYLEVEL", nLevel, nValue);
    }


    public static int SetGender (NWObject oCreature, int nSize) {
        return NWNXFuncsOne(oCreature, "NWNX!FUNCS!SETGENDER", nSize);
    }

    public static int SetCreatureSize (NWObject oCreature, int nSize) {
        return NWNXFuncsOne(oCreature, "NWNX!FUNCS!SETCREATURESIZE", nSize);
    }


    public static int GetCriticalHitMultiplier (NWObject oCreature, int bOffhand) {
        return NWNXFuncsOne(oCreature, "NWNX!FUNCS!GETCRITICALHITMULTIPLIER", bOffhand);
    }

    public static int GetCriticalHitRange (NWObject oCreature, int bOffhand) {
        return NWNXFuncsOne(oCreature, "NWNX!FUNCS!GETCRITICALHITRANGE", bOffhand);
    }


    public static int GetPCSkillPoints (NWObject oPC) {
        return NWNXFuncsZero(oPC, "NWNX!FUNCS!GETPCSKILLPOINTS");
    }

    public static int SetPCSkillPoints (NWObject oPC, int nSkillPoints) {
        return NWNXFuncsOne(oPC, "NWNX!FUNCS!SETPCSKILLPOINTS", nSkillPoints);
    }


    public static int SetPCLootable (NWObject oPC, int nLootable) {
        return NWNXFuncsOne(oPC, "NWNX!FUNCS!SETPCLOOTABLE", nLootable);
    }


    public static int GetPCBodyBag (NWObject oPC) {
        return NWNXFuncsZero(oPC, "NWNX!FUNCS!GETPCBODYBAG");
    }

    public static int SetPCBodyBag (NWObject oPC, int nBodyBag) {
        return NWNXFuncsOne(oPC, "NWNX!FUNCS!SETPCBODYBAG", nBodyBag);
    }


    public static int GetDamageImmunity (NWObject oCreature, int nDamType) {
        return NWNXFuncsOne(oCreature, "NWNX!FUNCS!GETDAMAGEIMMUNITY", nDamType);
    }


    public static int SetAlignmentGoodEvil (NWObject oCreature, int nValue) {
        return NWNXFuncsTwo(oCreature, "NWNX!FUNCS!SETALIGNMENTVALUE", 0, nValue);
    }

    public static int SetAlignmentLawChaos (NWObject oCreature, int nValue) {
        return NWNXFuncsTwo(oCreature, "NWNX!FUNCS!SETALIGNMENTVALUE", 1, nValue);
    }


    public static int ModifyCurrentHitPoints (NWObject oCreature, int nHP) {
        return NWNXFuncsOne(oCreature, "NWNX!FUNCS!MODIFYCURRENTHITPOINTS", nHP);
    }

    public static int SetCurrentHitPoints (NWObject oCreature, int nHP) {
        return NWNXFuncsOne(oCreature, "NWNX!FUNCS!SETCURRENTHITPOINTS", nHP);
    }

    public static int SetMaxHitPoints (NWObject oCreature, int nHP) {
        return NWNXFuncsOne(oCreature, "NWNX!FUNCS!SETMAXHITPOINTS", nHP);
    }


    public static int RecalculateDexModifier (NWObject oCreature) {
        return NWNXFuncsZero(oCreature, "NWNX!FUNCS!RECALCULATEDEXMODIFIER");
    }


    public static int GetKnowsSpell (int nSpellId, NWObject oCreature, int nClass) {
        return NWNXFuncsTwo(oCreature, "NWNX!FUNCS!GETKNOWSSPELL", nClass, nSpellId);
    }

    public static int GetKnownSpell (NWObject oCreature, int nClass, int nSpellLevel, int nIndex) {
        return NWNXFuncsThree(oCreature, "NWNX!FUNCS!GETKNOWNSPELL", nClass, nSpellLevel, nIndex);
    }

    public static int SetKnownSpell (NWObject oCreature, int nClass, int nSpellLevel, int nIndex, int nSpellId) {
        NWScript.setLocalString(oCreature, "NWNX!FUNCS!SETKNOWNSPELL",
                NWScript.intToString(nClass) + " " + NWScript.intToString(nSpellLevel) + " " +
                        NWScript.intToString(nIndex) + " " + NWScript.intToString(nSpellId) + "          ");
        return NWScript.stringToInt(NWScript.getLocalString(oCreature, "NWNX!FUNCS!SETKNOWNSPELL"));
    }

    public static int GetTotalKnownSpells (NWObject oCreature, int nClass, int nSpellLevel) {
        return NWNXFuncsTwo(oCreature, "NWNX!FUNCS!GETTOTALKNOWNSPELLS", nClass, nSpellLevel);
    }

    public static int AddKnownSpell (NWObject oCreature, int nClass, int nSpellLevel, int nSpellId) {
        return NWNXFuncsThree(oCreature, "NWNX!FUNCS!ADDKNOWNSPELL", nClass, nSpellLevel, nSpellId);
    }

    public static int RemoveKnownSpell (NWObject oCreature, int nClass, int nSpellLevel, int nSpellId) {
        return NWNXFuncsThree(oCreature, "NWNX!FUNCS!REMOVEKNOWNSPELL", nClass, nSpellLevel, nSpellId);
    }

    public static int ReplaceKnownSpell (NWObject oCreature, int nClass, int nOldSpell, int nNewSpell) {
        return NWNXFuncsThree(oCreature, "NWNX!FUNCS!REPLACEKNOWNSPELL", nClass, nOldSpell, nNewSpell);
    }


    public static MemorizedSpellSlot GetMemorizedSpell (NWObject oCreature, int nClass, int nLevel, int nIndex) {
        NWScript.setLocalString(oCreature, "NWNX!FUNCS!GETMEMORIZEDSPELL",
                NWScript.intToString(nClass) + " " + NWScript.intToString(nLevel) + " " + NWScript.intToString(nIndex) + "          ");

        int nSpell = NWScript.stringToInt(NWScript.getLocalString(oCreature, "NWNX!FUNCS!GETMEMORIZEDSPELL"));

        MemorizedSpellSlot mss = new MemorizedSpellSlot();

        if (nSpell >= 0) {
            mss.id    = nSpell & 0xFFFF;
            mss.meta  = (nSpell >> 16) & 0x7F;
            mss.ready = (nSpell >> 24) & 1;
        } else
            mss.id = -1;

        return mss;
    }

    public static int SetMemorizedSpell (NWObject oCreature, int nClass, int nLevel, int nIndex, MemorizedSpellSlot mss) {

        boolean isReady = mss.ready != 0;
        boolean domainSet = mss.domain != 0;

        // Source: int nFlags = (mss.ready != 0) | ((mss.domain != 0) << 1);
        int nFlags = (isReady ? 1 : 0) | ((domainSet ? 1 : 0) << 1); // This line was confusing.
                                                                     // If we have problems in the future, look at the source because this might not be a correct translation.
        NWScript.setLocalString(oCreature, "NWNX!FUNCS!SETMEMORIZEDSPELL",
                NWScript.intToString(nClass) + " " + NWScript.intToString(nLevel) + " " + NWScript.intToString(nIndex) + " " +
                        NWScript.intToString(mss.id) + " " + NWScript.intToString(mss.meta & 0x7F) + " " + NWScript.intToString(nFlags) + "          ");
        return NWScript.stringToInt(NWScript.getLocalString(oCreature, "NWNX!FUNCS!GETMEMORIZEDSPELL"));
    }

    public static int ClearMemorizedSpell (NWObject oCreature, int nClass, int nLevel, int nIndex) {
        MemorizedSpellSlot mss = new MemorizedSpellSlot();

        mss.id = -1;
        return SetMemorizedSpell(oCreature, nClass, nLevel, nIndex, mss);
    }


    public static int GetMaxSpellSlots (NWObject oCreature, int nClass, int nSpellLevel) {
        return NWNXFuncsTwo(oCreature, "NWNX!FUNCS!GETMAXSPELLSLOTS", nClass, nSpellLevel);
    }

    public static int GetBonusSpellSlots (NWObject oCreature, int nClass, int nSpellLevel) {
        return NWNXFuncsTwo(oCreature, "NWNX!FUNCS!GETBONUSSPELLSLOTS", nClass, nSpellLevel);
    }

    public static int GetRemainingSpellSlots (NWObject oCreature, int nClass, int nSpellLevel) {
        return NWNXFuncsTwo(oCreature, "NWNX!FUNCS!GETREMAININGSPELLSLOTS", nClass, nSpellLevel);
    }

    public static int SetRemainingSpellSlots (NWObject oCreature, int nClass, int nSpellLevel, int nSlots) {
        return NWNXFuncsThree(oCreature, "NWNX!FUNCS!SETREMAININGSPELLSLOTS", nClass, nSpellLevel, nSlots);
    }


    public static String GetAllMemorizedSpells (NWObject oCreature) {
        String sSpells = NWScript.getLocalString(NWObject.MODULE, "NWNX!ODBC!SPACER");

        NWScript.setLocalString(oCreature, "NWNX!FUNCS!GETALLMEMORIZEDSPELLS", sSpells + sSpells + sSpells + sSpells);
        sSpells = NWScript.getLocalString(oCreature, "NWNX!FUNCS!GETALLMEMORIZEDSPELLS");
        NWScript.deleteLocalString(oCreature, "NWNX!FUNCS!GETALLMEMORIZEDSPELLS");

        return sSpells;
    }

    public static int RestoreReadySpells (NWObject oCreature, String sSpells) {
        NWScript.setLocalString(oCreature, "NWNX!FUNCS!RESTOREREADYSPELLS", sSpells + " ");
        return NWScript.stringToInt(NWScript.getLocalString(oCreature, "NWNX!FUNCS!RESTOREREADYSPELLS"));
    }


    public static int GetClericDomain (NWObject oCreature, int nIndex) {
        return NWNXFuncsOne(oCreature, "NWNX!FUNCS!GETCLERICDOMAIN", nIndex);
    }

    public static int SetClericDomain (NWObject oCreature, int nIndex, int nDomain) {
        return NWNXFuncsTwo(oCreature, "NWNX!FUNCS!SETCLERICDOMAIN", nIndex, nDomain);
    }

    public static int GetWizardSpecialization (NWObject oCreature) {
        return NWNXFuncsZero(oCreature, "NWNX!FUNCS!GETWIZARDSPECIALIZATION");
    }

    public static int SetWizardSpecialization (NWObject oCreature, int nSchool) {
        return NWNXFuncsOne(oCreature, "NWNX!FUNCS!SETWIZARDSPECIALIZATION", nSchool);
    }


    public static SpecialAbilitySlot GetSpecialAbility (NWObject oCreature, int nIndex) {
        NWScript.setLocalString(oCreature, "NWNX!FUNCS!GETSPECIALABILITY",
                NWScript.intToString(nIndex) + "          ");

        int nSpec = NWScript.stringToInt(NWScript.getLocalString(oCreature, "NWNX!FUNCS!GETSPECIALABILITY"));

        SpecialAbilitySlot sas = new SpecialAbilitySlot();

        if (nSpec >= 0) {
            sas.id    = nSpec & 0xFFFF;
            sas.level = (nSpec >> 16) & 0x7F;
            sas.ready = (nSpec >> 24) & 1;
        } else
            sas.id = -1;

        return sas;
    }

    public static int SetSpecialAbility (NWObject oCreature, int nIndex, SpecialAbilitySlot sas) {
        NWScript.setLocalString(oCreature, "NWNX!FUNCS!SETSPECIALABILITY",
                NWScript.intToString(nIndex) + " " + NWScript.intToString(sas.id) + " " +
                        NWScript.intToString(sas.level & 0x7F) + " " + NWScript.intToString((sas.ready != 0) ? 1 : 0) + "          ");
        return NWScript.stringToInt(NWScript.getLocalString(oCreature, "NWNX!FUNCS!SETSPECIALABILITY"));
    }

    public static int GetTotalSpecialAbilities (NWObject oCreature) {
        return NWNXFuncsZero(oCreature, "NWNX!FUNCS!GETTOTALSPECIALABILITIES");
    }

    public static int AddSpecialAbility (NWObject oCreature, SpecialAbilitySlot sas) {
        NWScript.setLocalString(oCreature, "NWNX!FUNCS!ADDSPECIALABILITY",
                NWScript.intToString(sas.id) + " " + NWScript.intToString(sas.level & 0x7F) + " " + NWScript.intToString((sas.ready != 0) ? 1 : 0) + "          ");
        return NWScript.stringToInt(NWScript.getLocalString(oCreature, "NWNX!FUNCS!ADDSPECIALABILITY"));
    }

    public static int RemoveSpecialAbility (NWObject oCreature, int nIndex) {
        return NWNXFuncsOne(oCreature, "NWNX!FUNCS!REMOVESPECIALABILITY", nIndex);
    }


    public static String GetRawQuickBarSlot (NWObject oPC, int nSlot) {
        NWScript.setLocalString(oPC, "NWNX!FUNCS!GETQUICKBARSLOT",
                NWScript.intToString(nSlot) + "                                                                                                                                ");
        return NWScript.getLocalString(oPC, "NWNX!FUNCS!GETQUICKBARSLOT");
    }

    public static void SetRawQuickBarSlot (NWObject oPC, String sSlot) {
        NWScript.setLocalString(oPC, "NWNX!FUNCS!SETQUICKBARSLOT", sSlot);
    }


    public static String GetPortrait (NWObject oCreature) {
        NWScript.setLocalString(oCreature, "NWNX!FUNCS!GETPORTRAIT", "                    ");
        return NWScript.getLocalString(oCreature, "NWNX!FUNCS!GETPORTRAIT");
    }

    public static int SetPortrait (NWObject oCreature, String sPortrait) {
        NWScript.setLocalString(oCreature, "NWNX!FUNCS!SETPORTRAIT", sPortrait);
        return NWScript.stringToInt(NWScript.getLocalString(oCreature, "NWNX!FUNCS!SETPORTRAIT"));
    }


    public static int GetSoundset (NWObject oCreature) {
        return NWNXFuncsZero(oCreature, "NWNX!FUNCS!GETSOUNDSET");
    }

    public static int SetSoundset (NWObject oCreature, int nSoundset) {
        return NWNXFuncsOne(oCreature, "NWNX!FUNCS!SETSOUNDSET", nSoundset);
    }


    public static int SetTrapCreator (NWObject oTrap, NWObject oCreator) {
        NWScript.setLocalString(oTrap, "NWNX!FUNCS!SETTRAPCREATOR", String.valueOf(oCreator.getObjectId()));
        return NWScript.stringToInt(NWScript.getLocalString(oTrap, "NWNX!FUNCS!SETTRAPCREATOR"));
    }


    public static String GetConversation (NWObject oCreature) {
        NWScript.setLocalString(oCreature, "NWNX!FUNCS!GETCONVERSATION", "                ");
        return NWScript.getLocalString(oCreature, "NWNX!FUNCS!GETCONVERSATION");
    }

    public static String SetConversation (NWObject oCreature, String sConv) {
        NWScript.setLocalString(oCreature, "NWNX!FUNCS!SETCONVERSATION", sConv);
        return NWScript.getLocalString(oCreature, "NWNX!FUNCS!SETCONVERSATION");
    }


    public static boolean GetIsVariableValid (LocalVariable lv) {
        return (lv.type >= 1 && lv.type <= 5);
    }

    public static int GetLocalVariableCount (NWObject oObject) {
        NWScript.deleteLocalString(oObject, "NWNX!FUNCS!GETLOCALVARIABLECOUNT");
        NWScript.deleteLocalString(oObject, "NWNX!FUNCS!GETLOCALVARIABLEBYPOSITION");

        int nVariables = NWNXFuncsZero(oObject, "NWNX!FUNCS!GETLOCALVARIABLECOUNT");
        NWScript.deleteLocalString(oObject, "NWNX!FUNCS!GETLOCALVARIABLECOUNT");

        return nVariables;
    }


    public static LocalVariable GetLocalVariableByPosition (NWObject oObject, int nPos) {
        LocalVariable lv = new LocalVariable();

        NWScript.deleteLocalString(oObject, "NWNX!FUNCS!GETLOCALVARIABLEBYPOSITION");
        NWScript.setLocalString(oObject, "NWNX!FUNCS!GETLOCALVARIABLEBYPOSITION",
                NWScript.intToString(nPos) + "                                                                                                                                ");

        lv.name = NWScript.getLocalString(oObject, "NWNX!FUNCS!GETLOCALVARIABLEBYPOSITION");
        NWScript.deleteLocalString(oObject, "NWNX!FUNCS!GETLOCALVARIABLEBYPOSITION");

        lv.type = NWScript.stringToInt(lv.name);
        lv.name = NWScript.getSubString(lv.name, 2, 1000);
        lv.pos  = nPos;
        lv.obj  = oObject;

        return lv;
    }

    public static LocalVariable GetFirstLocalVariable (NWObject oObject) {
        return GetLocalVariableByPosition(oObject, 0);
    }

    public static LocalVariable GetNextLocalVariable (LocalVariable lv) {
        return GetLocalVariableByPosition(lv.obj, lv.pos + 1);
    }

    public static String DumpLocalVariables (NWObject oObject) {
        int i, nVariables = GetLocalVariableCount(oObject);
        NWObject oVar;
        NWVector vPos;
        NWLocation lLoc;
        String sDump = "";
        LocalVariable lv;

        for (i = 0; i < nVariables; i++) {
            lv = GetLocalVariableByPosition(oObject, i);

            switch (lv.type) {
                case VariableType.IntVar:
                    sDump += lv.name + " (int): " + NWScript.intToString(NWScript.getLocalInt(oObject, lv.name)) + "\n";
                    break;

                case VariableType.FloatVar:
                    sDump += lv.name + " (float): " + NWScript.floatToString(NWScript.getLocalFloat(oObject, lv.name), 1, 2) + "\n";
                    break;

                case VariableType.StringVar:
                    sDump += lv.name + " (String): '" + NWScript.getLocalString(oObject, lv.name) + "'\n";
                    break;

                case VariableType.ObjectVar:
                    oVar = NWScript.getLocalObject(oObject, lv.name);
                    if (NWScript.getIsPC(oVar))
                        sDump += lv.name + " (NWObject): " + NWScript.getName(oVar, false) + " (player: " +
                                NWScript.getPCPlayerName(oVar) + ") [" + oVar.getObjectId() + "]\n";
                    else
                        sDump += lv.name + " (NWObject): " + NWScript.getName(oVar, false) + " (tag: " + NWScript.getTag(oVar) +
                                " resref: " + NWScript.getResRef(oVar) + ") [" + oVar.getObjectId() + "]\n";
                    break;

                case VariableType.LocationVar:
                    lLoc = NWScript.getLocalLocation(oObject, lv.name);
                    oVar = NWScript.getAreaFromLocation(lLoc);
                    vPos = NWScript.getPositionFromLocation(lLoc);
                    sDump += lv.name + " (NWLocation): " + NWScript.getName(oVar, false) + " (" + NWScript.getResRef(oVar) + ") [" +
                            NWScript.floatToString(vPos.getX(), 1, 2) + ", " + NWScript.floatToString(vPos.getY(), 1, 2) + ", " +
                            NWScript.floatToString(vPos.getZ(), 1, 2) + " | " +
                            NWScript.floatToString(NWScript.getFacingFromLocation(lLoc), 1, 2) + "]\n";
                    break;

                default:
                    sDump += lv.name + " (UNKNOWN)\n";
                    break;
            }
        }

        return sDump;
    }


    public static NWObject GetFirstArea () {
        return NWScript.getLocalObject(NWObject.MODULE, "NWNX!FUNCS!GETFIRSTAREA");
    }

    public static NWObject GetNextArea () {
        return NWScript.getLocalObject(NWObject.MODULE, "NWNX!FUNCS!GETNEXTAREA");
    }

    public static int SetBaseItemType (NWObject oItem, int nBaseItem)
    {
        NWScript.setLocalString(oItem, "NWNX!FUNCS!SETBASEITEMTYPE", NWScript.intToString(nBaseItem));
        return NWScript.stringToInt(NWScript.getLocalString(oItem, "NWNX!FUNCS!SETBASEITEMTYPE"));
    }


    public static int SetGoldPieceValue (NWObject oItem, int nValue) {
        return NWNXFuncsOne(oItem, "NWNX!FUNCS!SETGOLDPIECEVALUE", nValue);
    }

    public static int SetItemWeight (NWObject oItem, int nTenthLbs) {
        return NWNXFuncsOne(oItem, "NWNX!FUNCS!SETITEMWEIGHT", nTenthLbs);
    }


    public static String GetEntireItemAppearance (NWObject oItem) {
        NWScript.setLocalString(oItem, "NWNX!FUNCS!GETENTIREITEMAPPEARANCE", "                                                            ");
        String sApp = NWScript.getLocalString(oItem, "NWNX!FUNCS!GETENTIREITEMAPPEARANCE");
        NWScript.deleteLocalString(oItem, "NWNX!FUNCS!GETENTIREITEMAPPEARANCE");

        return sApp;
    }

    public static void RestoreItemAppearance (NWObject oItem, String sApp) {
        NWScript.setLocalString(oItem, "NWNX!FUNCS!RESTOREITEMAPPEARANCE", sApp);
        NWScript.deleteLocalString(oItem, "NWNX!FUNCS!RESTOREITEMAPPEARANCE");
    }

    public static int SetItemAppearance (NWObject oItem, int nIndex, int nValue) {
        int nRet = NWNXFuncsTwo(oItem, "NWNX!FUNCS!SETITEMAPPEARANCE", nIndex, nValue);
        NWScript.deleteLocalString(oItem, "NWNX!FUNCS!SETITEMAPPEARANCE");
        return nRet;
    }

    public static int SetItemColor (NWObject oItem, int nIndex, int nColor) {
        int nRet = NWNXFuncsTwo(oItem, "NWNX!FUNCS!SETITEMCOLOR", nIndex, nColor);
        NWScript.deleteLocalString(oItem, "NWNX!FUNCS!SETITEMCOLOR");
        return nRet;
    }

    public static int GetIsStatic (NWObject oPlace) {
        int nRet = NWNXFuncsZero(oPlace, "NWNX!FUNCS!GETISSTATIC");
        NWScript.deleteLocalString(oPlace, "NWNX!FUNCS!GETISSTATIC");
        return nRet;
    }

    public static int SetPlaceableAppearance (NWObject oPlace, int nApp) {
        int nRet = NWNXFuncsOne(oPlace, "NWNX!FUNCS!SETPLACEABLEAPPEARANCE", nApp);
        NWScript.deleteLocalString(oPlace, "NWNX!FUNCS!SETPLACEABLEAPPEARANCE");
        return nRet;
    }


    public static float GetGroundHeight (NWObject oArea, NWVector vPos) {
        NWScript.setLocalString(oArea, "NWNX!FUNCS!GETGROUNDHEIGHT",
                NWScript.floatToString(vPos.getX(), 18, 9) + "¬" + NWScript.floatToString(vPos.getY(), 18, 9) + "¬" +
                        NWScript.floatToString(vPos.getZ(), 18, 9) + "                    ");
        return NWScript.stringToFloat(NWScript.getLocalString(oArea, "NWNX!FUNCS!GETGROUNDHEIGHT"));
    }

    public static float GetGroundHeightFromLocation (NWLocation lLoc) {
        return GetGroundHeight(NWScript.getAreaFromLocation(lLoc), NWScript.getPositionFromLocation(lLoc));
    }

    public static int GetIsWalkable (NWObject oArea, NWVector vPos) {
        NWScript.setLocalString(oArea, "NWNX!FUNCS!GETISWALKABLE",
                NWScript.floatToString(vPos.getX(), 18, 9) + "¬" + NWScript.floatToString(vPos.getY(), 18, 9) + "¬" +
                        NWScript.floatToString(vPos.getZ(), 18, 9) + "                    ");
        return NWScript.stringToInt(NWScript.getLocalString(oArea, "NWNX!FUNCS!GETISWALKABLE"));
    }

    public static int GetIsWalkableLocation (NWLocation lLoc) {
        return GetIsWalkable(NWScript.getAreaFromLocation(lLoc), NWScript.getPositionFromLocation(lLoc));
    }


    public static void ActionUseItem (NWObject oItem, NWObject oTarget, NWLocation lTarget, int nProp, NWObject objSelf) {
        NWObject oArea = NWScript.getAreaFromLocation(lTarget);
        NWVector vVec = NWScript.getPositionFromLocation(lTarget);

        if (!NWScript.getIsObjectValid(oArea))
            return;

        NWScript.setLocalString(objSelf, "NWNX!FUNCS!ACTIONUSEITEM",
                oItem.getObjectId() + "¬" + oTarget.getObjectId() + "¬" +
                        oArea.getObjectId() + "¬" + NWScript.floatToString(vVec.getX(), 18, 9) + "¬" +
                        NWScript.floatToString(vVec.getY(), 18, 9) + "¬" + NWScript.floatToString(vVec.getZ(), 18, 9) + "¬" +
                        NWScript.intToString(nProp));
    }

    public static void ActionUseItemAtLocation (NWObject oItem, NWLocation lTarget, int nProp, NWObject objSelf) {
        ActionUseItem(oItem, NWObject.INVALID, lTarget, nProp, objSelf);
    }

    public static void ActionUseItemOnObject (NWObject oItem, NWObject oTarget, int nProp, NWObject objSelf) {
        ActionUseItem(oItem, NWObject.INVALID, NWScript.getLocation(oTarget), nProp, objSelf);
    }


    public static int GetPCPort (NWObject oPC) {
        if (!NWScript.getIsPC(oPC) || NWScript.getIsDMPossessed(oPC))
            return 0;
        return NWNXFuncsZero(oPC, "NWNX!FUNCS!GETPCPORT");
    }

    public static void BootPCWithMessage (NWObject oPC, int nStrRef) {
        NWScript.setLocalString(oPC, "NWNX!FUNCS!BOOTPCWITHMESSAGE", NWScript.intToString(nStrRef));
    }


    public static String GetCreatureEventHandler (NWObject oCreature, int nEvent) {
        NWScript.setLocalString(oCreature, "NWNX!FUNCS!GETCREATUREEVENTHANDLER", NWScript.intToString(nEvent) + "                ");
        return NWScript.getLocalString(oCreature, "NWNX!FUNCS!GETCREATUREEVENTHANDLER");
    }

    public static String SetCreatureEventHandler (NWObject oCreature, int nEvent, String sScript) {
        NWScript.setLocalString(oCreature, "NWNX!FUNCS!SETCREATUREEVENTHANDLER", NWScript.intToString(nEvent) + " " + sScript);
        return NWScript.getLocalString(oCreature, "NWNX!FUNCS!SETCREATUREEVENTHANDLER");
    }

    public static String GetEventHandler (NWObject oObject, int nEvent) {
        NWScript.setLocalString(oObject, "NWNX!FUNCSEXT!GETSCRIPT", NWScript.intToString(nEvent)+"                ");
        String sRet = NWScript.getLocalString(oObject, "NWNX!FUNCSEXT!GETSCRIPT");
        NWScript.deleteLocalString(oObject, "NWNX!FUNCSEXT!GETSCRIPT");
        return sRet;

    }

    public static String SetEventHandler (NWObject oObject, int nEvent, String sScript) {
        NWScript.setLocalString(oObject, "NWNX!FUNCSEXT!SETSCRIPT", NWScript.intToString(nEvent)+":"+sScript+"          ");
        NWScript.deleteLocalString(oObject, "NWNX!FUNCSEXT!SETSCRIPT");
        return "";

    }

    public static int GetFactionId (NWObject oObject) {
        return NWNXFuncsZero(oObject, "NWNX!FUNCS!GETFACTIONID");
    }

    public static int SetFactionId (NWObject oObject, int nFaction) {
        return NWNXFuncsOne(oObject, "NWNX!FUNCS!SETFACTIONID", nFaction);
    }

    public static void SetLastHostileActor (NWObject oObject, NWObject oActor) {
        NWScript.setLocalString(oObject, "NWNX!FUNCS!SETLASTHOSTILEACTOR", "" + oActor.getObjectId());
    }


    public static NWObject GetItemByPosition (NWObject oCreature, int nPos) {
        NWScript.setLocalString(oCreature, "NWNX!FUNCS!GETITEMBYPOSITIONREQUEST", NWScript.intToString(nPos));
        return NWScript.getLocalObject(oCreature, "NWNX!FUNCS!GETITEMBYPOSITION");
    }

    public static int GetItemCount (NWObject oCreature) {
        return NWNXFuncsZero(oCreature, "NWNX!FUNCS!GETITEMCOUNT");
    }


    public static int SetMovementRate (NWObject oCreature, int nRate) {
        if (NWScript.getIsDM(oCreature))
            nRate = MovementRate.DMFast;
        return NWNXFuncsOne(oCreature, "NWNX!FUNCS!SETMOVEMENTRATE", nRate);
    }


    public static int SetRacialType (NWObject oCreature, int nRacialType) {
        return NWNXFuncsOne(oCreature, "NWNX!FUNCS!SETRACIALTYPE", nRacialType);
    }


    public static String SetTag (NWObject oObject, String sTag) {
        NWScript.setLocalString(oObject, "NWNX!FUNCS!SETTAG", sTag);
        return NWScript.getLocalString(oObject, "NWNX!FUNCS!SETTAG");
    }


    public static String GetPCFileName (NWObject oPC) {
        if (!NWScript.getIsPC(oPC))
            return "";

        NWScript.setLocalString(oPC, "NWNX!FUNCS!GETPCFILENAME", "                    ");
        return NWScript.getLocalString(oPC, "NWNX!FUNCS!GETPCFILENAME");
    }


    public static void JumpToLimbo (NWObject oCreature) {
        NWScript.setLocalString(oCreature, "NWNX!FUNCS!JUMPTOLIMBO", "          ");
    }


    public static int BroadcastProjectileToObject (NWObject oSource, NWObject oTarget, int nSpellId, int nDelay) {
        if (!NWScript.getIsObjectValid(oTarget))
            return 0;

        NWLocation lTarget = NWScript.getLocation(oTarget);
        NWVector vTarget   = NWScript.getPositionFromLocation(lTarget);

        if (nDelay < 0) {
            float fDelay = NWScript.getDistanceBetween(oSource, oTarget) / 20;
            nDelay = NWScript.floatToInt(fDelay * 1000);
        }

        NWScript.setLocalString(oSource, "NWNX!FUNCS!BROADCASTPROJECTILE",
                oTarget.getObjectId() + " " +
                        NWScript.floatToString(vTarget.getX(), 1, 4) + " " +
                        NWScript.floatToString(vTarget.getY(), 1, 4) + " " +
                        NWScript.floatToString(vTarget.getZ(), 1, 4) + " " +
                        NWScript.intToString(nSpellId) + " " + NWScript.intToString(nDelay));
        return NWScript.stringToInt(NWScript.getLocalString(oSource, "NWNX!FUNCS!BROADCASTPROJECTILE"));
    }

    public static int BroadcastProjectileToLocation (NWObject oSource, NWLocation lTarget, int nSpellId, int nDelay) {
        NWVector vTarget = NWScript.getPositionFromLocation(lTarget);

        if (nDelay < 0) {
            float fDelay = NWScript.getDistanceBetweenLocations(NWScript.getLocation(oSource), lTarget) / 20;
            nDelay = NWScript.floatToInt(fDelay * 1000);
        }

        NWScript.setLocalString(oSource, "NWNX!FUNCS!BROADCASTPROJECTILE", "0 " +
                NWScript.floatToString(vTarget.getX(), 1, 4) + " " +
                NWScript.floatToString(vTarget.getY(), 1, 4) + " " +
                NWScript.floatToString(vTarget.getZ(), 1, 4) + " " +
                NWScript.intToString(nSpellId) + " " + NWScript.intToString(nDelay));
        return NWScript.stringToInt(NWScript.getLocalString(oSource, "NWNX!FUNCS!BROADCASTPROJECTILE"));
    }


    public static int SetIsCreatureDisarmable(NWObject oCreature, int bDisarmable)
    {
        int nRet = NWNXFuncsOne(oCreature, "NWNX!FUNCS!SETISCREATUREDISARMABLE", (bDisarmable != 0) ? 1 : 0);
        NWScript.deleteLocalString(oCreature, "NWNX!FUNCS!SETISCREATUREDISARMABLE");
        return nRet;
    }

    public static int SetCorpseDecayTime(NWObject oCreature, int nTime)
    {
        int nRet = NWNXFuncsOne(oCreature, "NWNX!FUNCS!SETCORPSEDECAYTIME", nTime);
        NWScript.deleteLocalString(oCreature, "NWNX!FUNCS!SETCORPSEDECAYTIME");
        return nRet;
    }

    public static int GetCorpseDecayTime(NWObject oCreature)
    {
        int nRet = NWNXFuncsZero(oCreature, "NWNX!FUNCS!GETCORPSEDECAYTIME");
        NWScript.deleteLocalString(oCreature, "NWNX!FUNCS!GETCORPSEDECAYTIME");
        return nRet;
    }

    public static NWObject IntToObject (int nObjectId) {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!FUNCS!INTTOOBJECTREQUEST", NWScript.intToString(nObjectId));
        return NWScript.getLocalObject(NWObject.MODULE, "NWNX!FUNCS!INTTOOBJECT");
    }

    public static NWObject StringToObject (String sObjectId) {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!FUNCS!STRINGTOOBJECTREQUEST", sObjectId);
        return NWScript.getLocalObject(NWObject.MODULE, "NWNX!FUNCS!INTTOOBJECT");
    }


    public static void DumpObject (NWObject oObject) {
        NWScript.setLocalString(oObject, "NWNX!FUNCS!DUMPOBJECT", "          ");
    }


}
