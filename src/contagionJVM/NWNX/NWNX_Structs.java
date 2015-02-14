package contagionJVM.NWNX;

import org.nwnx.nwnx2.jvm.*;
import org.nwnx.nwnx2.jvm.NWObject;

@SuppressWarnings("UnusedDeclaration")
public class NWNX_Structs {
    
    /* interface functions for nwnx_structs plugin */

/* NWEffect icon public finalants */
    public static final int EFFECT_ICON_DAMAGE_RESISTANCE                         =   1;
    public static final int EFFECT_ICON_REGENERATE                                =   2;
    public static final int EFFECT_ICON_DAMAGE_REDUCTION                          =   3;
    public static final int EFFECT_ICON_TEMPORARY_HITPOINTS                       =   4;
    public static final int EFFECT_ICON_ENTANGLE                                  =   5;
    public static final int EFFECT_ICON_INVULNERABLE                              =   6;
    public static final int EFFECT_ICON_DEAF                                      =   7;
    public static final int EFFECT_ICON_FATIGUE                                   =   8;
    public static final int EFFECT_ICON_IMMUNITY                                  =   9;
    public static final int EFFECT_ICON_BLIND                                     =  10;
    public static final int EFFECT_ICON_ENEMY_ATTACK_BONUS                        =  11;
    public static final int EFFECT_ICON_CHARMED                                   =  12;
    public static final int EFFECT_ICON_CONFUSED                                  =  13;
    public static final int EFFECT_ICON_FRIGHTENED                                =  14;
    public static final int EFFECT_ICON_DOMINATED                                 =  15;
    public static final int EFFECT_ICON_PARALYZE                                  =  16;
    public static final int EFFECT_ICON_DAZED                                     =  17;
    public static final int EFFECT_ICON_STUNNED                                   =  18;
    public static final int EFFECT_ICON_SLEEP                                     =  19;
    public static final int EFFECT_ICON_POISON                                    =  20;
    public static final int EFFECT_ICON_DISEASE                                   =  21;
    public static final int EFFECT_ICON_CURSE                                     =  22;
    public static final int EFFECT_ICON_SILENCE                                   =  23;
    public static final int EFFECT_ICON_TURNED                                    =  24;
    public static final int EFFECT_ICON_HASTE                                     =  25;
    public static final int EFFECT_ICON_SLOW                                      =  26;
    public static final int EFFECT_ICON_ABILITY_INCREASE_STR                      =  27;
    public static final int EFFECT_ICON_ABILITY_DECREASE_STR                      =  28;
    public static final int EFFECT_ICON_ATTACK_INCREASE                           =  29;
    public static final int EFFECT_ICON_ATTACK_DECREASE                           =  30;
    public static final int EFFECT_ICON_DAMAGE_INCREASE                           =  31;
    public static final int EFFECT_ICON_DAMAGE_DECREASE                           =  32;
    public static final int EFFECT_ICON_DAMAGE_IMMUNITY_INCREASE                  =  33;
    public static final int EFFECT_ICON_DAMAGE_IMMUNITY_DECREASE                  =  34;
    public static final int EFFECT_ICON_AC_INCREASE                               =  35;
    public static final int EFFECT_ICON_AC_DECREASE                               =  36;
    public static final int EFFECT_ICON_MOVEMENT_SPEED_INCREASE                   =  37;
    public static final int EFFECT_ICON_MOVEMENT_SPEED_DECREASE                   =  38;
    public static final int EFFECT_ICON_SAVING_THROW_INCREASE                     =  39;
    public static final int EFFECT_ICON_SAVING_THROW_DECREASE                     =  40;
    public static final int EFFECT_ICON_SPELL_RESISTANCE_INCREASE                 =  41;
    public static final int EFFECT_ICON_SPELL_RESISTANCE_DECREASE                 =  42;
    public static final int EFFECT_ICON_SKILL_INCREASE                            =  43;
    public static final int EFFECT_ICON_SKILL_DECREASE                            =  44;
    public static final int EFFECT_ICON_INVISIBILITY                              =  45;
    public static final int EFFECT_ICON_IMPROVEDINVISIBILITY                      =  46;
    public static final int EFFECT_ICON_DARKNESS                                  =  47;
    public static final int EFFECT_ICON_DISPELMAGICALL                            =  48;
    public static final int EFFECT_ICON_ELEMENTALSHIELD                           =  49;
    public static final int EFFECT_ICON_LEVELDRAIN                                =  50;
    public static final int EFFECT_ICON_POLYMORPH                                 =  51;
    public static final int EFFECT_ICON_SANCTUARY                                 =  52;
    public static final int EFFECT_ICON_TRUESEEING                                =  53;
    public static final int EFFECT_ICON_SEEINVISIBILITY                           =  54;
    public static final int EFFECT_ICON_TIMESTOP                                  =  55;
    public static final int EFFECT_ICON_BLINDNESS                                 =  56;
    public static final int EFFECT_ICON_SPELLLEVELABSORPTION                      =  57;
    public static final int EFFECT_ICON_DISPELMAGICBEST                           =  58;
    public static final int EFFECT_ICON_ABILITY_INCREASE_DEX                      =  59;
    public static final int EFFECT_ICON_ABILITY_DECREASE_DEX                      =  60;
    public static final int EFFECT_ICON_ABILITY_INCREASE_CON                      =  61;
    public static final int EFFECT_ICON_ABILITY_DECREASE_CON                      =  62;
    public static final int EFFECT_ICON_ABILITY_INCREASE_INT                      =  63;
    public static final int EFFECT_ICON_ABILITY_DECREASE_INT                      =  64;
    public static final int EFFECT_ICON_ABILITY_INCREASE_WIS                      =  65;
    public static final int EFFECT_ICON_ABILITY_DECREASE_WIS                      =  66;
    public static final int EFFECT_ICON_ABILITY_INCREASE_CHA                      =  67;
    public static final int EFFECT_ICON_ABILITY_DECREASE_CHA                      =  68;
    public static final int EFFECT_ICON_IMMUNITY_ALL                              =  69;
    public static final int EFFECT_ICON_IMMUNITY_MIND                             =  70;
    public static final int EFFECT_ICON_IMMUNITY_POISON                           =  71;
    public static final int EFFECT_ICON_IMMUNITY_DISEASE                          =  72;
    public static final int EFFECT_ICON_IMMUNITY_FEAR                             =  73;
    public static final int EFFECT_ICON_IMMUNITY_TRAP                             =  74;
    public static final int EFFECT_ICON_IMMUNITY_PARALYSIS                        =  75;
    public static final int EFFECT_ICON_IMMUNITY_BLINDNESS                        =  76;
    public static final int EFFECT_ICON_IMMUNITY_DEAFNESS                         =  77;
    public static final int EFFECT_ICON_IMMUNITY_SLOW                             =  78;
    public static final int EFFECT_ICON_IMMUNITY_ENTANGLE                         =  79;
    public static final int EFFECT_ICON_IMMUNITY_SILENCE                          =  80;
    public static final int EFFECT_ICON_IMMUNITY_STUN                             =  81;
    public static final int EFFECT_ICON_IMMUNITY_SLEEP                            =  82;
    public static final int EFFECT_ICON_IMMUNITY_CHARM                            =  83;
    public static final int EFFECT_ICON_IMMUNITY_DOMINATE                         =  84;
    public static final int EFFECT_ICON_IMMUNITY_CONFUSE                          =  85;
    public static final int EFFECT_ICON_IMMUNITY_CURSE                            =  86;
    public static final int EFFECT_ICON_IMMUNITY_DAZED                            =  87;
    public static final int EFFECT_ICON_IMMUNITY_ABILITY_DECREASE                 =  88;
    public static final int EFFECT_ICON_IMMUNITY_ATTACK_DECREASE                  =  89;
    public static final int EFFECT_ICON_IMMUNITY_DAMAGE_DECREASE                  =  90;
    public static final int EFFECT_ICON_IMMUNITY_DAMAGE_IMMUNITY_DECREASE         =  91;
    public static final int EFFECT_ICON_IMMUNITY_AC_DECREASE                      =  92;
    public static final int EFFECT_ICON_IMMUNITY_MOVEMENT_SPEED_DECREASE          =  93;
    public static final int EFFECT_ICON_IMMUNITY_SAVING_THROW_DECREASE            =  94;
    public static final int EFFECT_ICON_IMMUNITY_SPELL_RESISTANCE_DECREASE        =  95;
    public static final int EFFECT_ICON_IMMUNITY_SKILL_DECREASE                   =  96;
    public static final int EFFECT_ICON_IMMUNITY_KNOCKDOWN                        =  97;
    public static final int EFFECT_ICON_IMMUNITY_NEGATIVE_LEVEL                   =  98;
    public static final int EFFECT_ICON_IMMUNITY_SNEAK_ATTACK                     =  99;
    public static final int EFFECT_ICON_IMMUNITY_CRITICAL_HIT                     = 100;
    public static final int EFFECT_ICON_IMMUNITY_DEATH_MAGIC                      = 101;
    public static final int EFFECT_ICON_REFLEX_SAVE_INCREASED                     = 102;
    public static final int EFFECT_ICON_FORT_SAVE_INCREASED                       = 103;
    public static final int EFFECT_ICON_WILL_SAVE_INCREASED                       = 104;
    public static final int EFFECT_ICON_TAUNTED                                   = 105;
    public static final int EFFECT_ICON_SPELLIMMUNITY                             = 106;
    public static final int EFFECT_ICON_ETHEREALNESS                              = 107;
    public static final int EFFECT_ICON_CONCEALMENT                               = 108;
    public static final int EFFECT_ICON_PETRIFIED                                 = 109;
    public static final int EFFECT_ICON_SPELL_FAILURE                             = 110;
    public static final int EFFECT_ICON_DAMAGE_IMMUNITY_MAGIC                     = 111;
    public static final int EFFECT_ICON_DAMAGE_IMMUNITY_ACID                      = 112;
    public static final int EFFECT_ICON_DAMAGE_IMMUNITY_COLD                      = 113;
    public static final int EFFECT_ICON_DAMAGE_IMMUNITY_DIVINE                    = 114;
    public static final int EFFECT_ICON_DAMAGE_IMMUNITY_ELECTRICAL                = 115;
    public static final int EFFECT_ICON_DAMAGE_IMMUNITY_FIRE                      = 116;
    public static final int EFFECT_ICON_DAMAGE_IMMUNITY_NEGATIVE                  = 117;
    public static final int EFFECT_ICON_DAMAGE_IMMUNITY_POSITIVE                  = 118;
    public static final int EFFECT_ICON_DAMAGE_IMMUNITY_SONIC                     = 119;
    public static final int EFFECT_ICON_DAMAGE_IMMUNITY_MAGIC_DECREASE            = 120;
    public static final int EFFECT_ICON_DAMAGE_IMMUNITY_ACID_DECREASE             = 121;
    public static final int EFFECT_ICON_DAMAGE_IMMUNITY_COLD_DECREASE             = 122;
    public static final int EFFECT_ICON_DAMAGE_IMMUNITY_DIVINE_DECREASE           = 123;
    public static final int EFFECT_ICON_DAMAGE_IMMUNITY_ELECTRICAL_DECREASE       = 124;
    public static final int EFFECT_ICON_DAMAGE_IMMUNITY_FIRE_DECREASE             = 125;
    public static final int EFFECT_ICON_DAMAGE_IMMUNITY_NEGATIVE_DECREASE         = 126;
    public static final int EFFECT_ICON_DAMAGE_IMMUNITY_POSITIVE_DECREASE         = 127;
    public static final int EFFECT_ICON_DAMAGE_IMMUNITY_SONIC_DECREASE            = 128;
    public static final int EFFECT_ICON_WOUNDING                                  = 129;

/* NWEffect state public finalants */
    public static final int EFFECT_STATE_CHARMED                                  =   1;
    public static final int EFFECT_STATE_CONFUSED                                 =   2;
    public static final int EFFECT_STATE_FRIGHTENED                               =   3;
    public static final int EFFECT_STATE_TURNED                                   =   4;
    public static final int EFFECT_STATE_DAZED                                    =   5;
    public static final int EFFECT_STATE_STUNNED                                  =   6;
    public static final int EFFECT_STATE_DOMINATED                                =   7;
    public static final int EFFECT_STATE_PARALYZE                                 =   8;
    public static final int EFFECT_STATE_SLEEP                                    =   9;

/* NWEffect true type public finalants */
    public static final int EFFECT_TRUETYPE_INVALIDNWEffect                         =   0;
    public static final int EFFECT_TRUETYPE_HASTE                                 =   1;
    public static final int EFFECT_TRUETYPE_DAMAGE_RESISTANCE                     =   2;
    public static final int EFFECT_TRUETYPE_SLOW                                  =   3;
    public static final int EFFECT_TRUETYPE_RESURRECTION                          =   4;
    public static final int EFFECT_TRUETYPE_DISEASE                               =   5;
    public static final int EFFECT_TRUETYPE_SUMMON_CREATURE                       =   6;
    public static final int EFFECT_TRUETYPE_REGENERATE                            =   7;
    public static final int EFFECT_TRUETYPE_SETSTATE                              =   8;
    public static final int EFFECT_TRUETYPE_SETSTATE_INTERNAL                     =   9;
    public static final int EFFECT_TRUETYPE_ATTACK_INCREASE                       =  10;
    public static final int EFFECT_TRUETYPE_ATTACK_DECREASE                       =  11;
    public static final int EFFECT_TRUETYPE_DAMAGE_REDUCTION                      =  12;
    public static final int EFFECT_TRUETYPE_DAMAGE_INCREASE                       =  13;
    public static final int EFFECT_TRUETYPE_DAMAGE_DECREASE                       =  14;
    public static final int EFFECT_TRUETYPE_TEMPORARY_HITPOINTS                   =  15;
    public static final int EFFECT_TRUETYPE_DAMAGE_IMMUNITY_INCREASE              =  16;
    public static final int EFFECT_TRUETYPE_DAMAGE_IMMUNITY_DECREASE              =  17;
    public static final int EFFECT_TRUETYPE_ENTANGLE                              =  18;
    public static final int EFFECT_TRUETYPE_DEATH                                 =  19;
    public static final int EFFECT_TRUETYPE_KNOCKDOWN                             =  20;
    public static final int EFFECT_TRUETYPE_DEAF                                  =  21;
    public static final int EFFECT_TRUETYPE_IMMUNITY                              =  22;
    public static final int EFFECT_TRUETYPE_SET_AI_STATE                          =  23;
    public static final int EFFECT_TRUETYPE_ENEMY_ATTACK_BONUS                    =  24;
    public static final int EFFECT_TRUETYPE_ARCANE_SPELL_FAILURE                  =  25;
    public static final int EFFECT_TRUETYPE_SAVING_THROW_INCREASE                 =  26;
    public static final int EFFECT_TRUETYPE_SAVING_THROW_DECREASE                 =  27;
    public static final int EFFECT_TRUETYPE_MOVEMENT_SPEED_INCREASE               =  28;
    public static final int EFFECT_TRUETYPE_MOVEMENT_SPEED_DECREASE               =  29;
    public static final int EFFECT_TRUETYPE_VISUALNWEffect                          =  30;
    public static final int EFFECT_TRUETYPE_AREA_OF_NWEffect                        =  31;
    public static final int EFFECT_TRUETYPE_BEAM                                  =  32;
    public static final int EFFECT_TRUETYPE_SPELL_RESISTANCE_INCREASE             =  33;
    public static final int EFFECT_TRUETYPE_SPELL_RESISTANCE_DECREASE             =  34;
    public static final int EFFECT_TRUETYPE_POISON                                =  35;
    public static final int EFFECT_TRUETYPE_ABILITY_INCREASE                      =  36;
    public static final int EFFECT_TRUETYPE_ABILITY_DECREASE                      =  37;
    public static final int EFFECT_TRUETYPE_DAMAGE                                =  38;
    public static final int EFFECT_TRUETYPE_HEAL                                  =  39;
    public static final int EFFECT_TRUETYPE_LINK                                  =  40;
    public static final int EFFECT_TRUETYPE_HASTE_INTERNAL                        =  41;
    public static final int EFFECT_TRUETYPE_SLOW_INTERNAL                         =  42;
    public static final int EFFECT_TRUETYPE_MODIFYNUMATTACKS                      =  44;
    public static final int EFFECT_TRUETYPE_CURSE                                 =  45;
    public static final int EFFECT_TRUETYPE_SILENCE                               =  46;
    public static final int EFFECT_TRUETYPE_INVISIBILITY                          =  47;
    public static final int EFFECT_TRUETYPE_AC_INCREASE                           =  48;
    public static final int EFFECT_TRUETYPE_AC_DECREASE                           =  49;
    public static final int EFFECT_TRUETYPE_SPELL_IMMUNITY                        =  50;
    public static final int EFFECT_TRUETYPE_DISPEL_ALL_MAGIC                      =  51;
    public static final int EFFECT_TRUETYPE_DISPEL_BEST_MAGIC                     =  52;
    public static final int EFFECT_TRUETYPE_TAUNT                                 =  53;
    public static final int EFFECT_TRUETYPE_LIGHT                                 =  54;
    public static final int EFFECT_TRUETYPE_SKILL_INCREASE                        =  55;
    public static final int EFFECT_TRUETYPE_SKILL_DECREASE                        =  56;
    public static final int EFFECT_TRUETYPE_HITPOINTCHANGEWHENDYING               =  57;
    public static final int EFFECT_TRUETYPE_SETWALKANIMATION                      =  58;
    public static final int EFFECT_TRUETYPE_LIMIT_MOVEMENT_SPEED                  =  59;
    public static final int EFFECT_TRUETYPE_DAMAGE_SHIELD                         =  61;
    public static final int EFFECT_TRUETYPE_POLYMORPH                             =  62;
    public static final int EFFECT_TRUETYPE_SANCTUARY                             =  63;
    public static final int EFFECT_TRUETYPE_TIMESTOP                              =  64;
    public static final int EFFECT_TRUETYPE_SPELL_LEVEL_ABSORPTION                =  65;
    public static final int EFFECT_TRUETYPE_ICON                                  =  67;
    public static final int EFFECT_TRUETYPE_RACIAL_TYPE                           =  68;
    public static final int EFFECT_TRUETYPE_VISION                                =  69;
    public static final int EFFECT_TRUETYPE_SEEINVISIBLE                          =  70;
    public static final int EFFECT_TRUETYPE_ULTRAVISION                           =  71;
    public static final int EFFECT_TRUETYPE_TRUESEEING                            =  72;
    public static final int EFFECT_TRUETYPE_BLINDNESS                             =  73;
    public static final int EFFECT_TRUETYPE_DARKNESS                              =  74;
    public static final int EFFECT_TRUETYPE_MISS_CHANCE                           =  75;
    public static final int EFFECT_TRUETYPE_CONCEALMENT                           =  76;
    public static final int EFFECT_TRUETYPE_TURN_RESISTANCE_INCREASE              =  77;
    public static final int EFFECT_TRUETYPE_BONUS_SPELL_OF_LEVEL                  =  78;
    public static final int EFFECT_TRUETYPE_DISAPPEARAPPEAR                       =  79;
    public static final int EFFECT_TRUETYPE_DISAPPEAR                             =  80;
    public static final int EFFECT_TRUETYPE_APPEAR                                =  81;
    public static final int EFFECT_TRUETYPE_NEGATIVE_LEVEL                        =  82;
    public static final int EFFECT_TRUETYPE_BONUS_FEAT                            =  83;
    public static final int EFFECT_TRUETYPE_WOUNDING                              =  84;
    public static final int EFFECT_TRUETYPE_SWARM                                 =  85;
    public static final int EFFECT_TRUETYPE_VAMPIRIC_REGENERATION                 =  86;
    public static final int EFFECT_TRUETYPE_DISARM                                =  87;
    public static final int EFFECT_TRUETYPE_TURN_RESISTANCE_DECREASE              =  88;
    public static final int EFFECT_TRUETYPE_BLINDNESS_INACTIVE                    =  89;
    public static final int EFFECT_TRUETYPE_PETRIFY                               =  90;
    public static final int EFFECT_TRUETYPE_ITEMPROPERTY                          =  91;
    public static final int EFFECT_TRUETYPE_SPELL_FAILURE                         =  92;
    public static final int EFFECT_TRUETYPE_CUTSCENEGHOST                         =  93;
    public static final int EFFECT_TRUETYPE_CUTSCENEIMMOBILE                      =  94;
    public static final int EFFECT_TRUETYPE_DEFENSIVESTANCE                       =  95;


    public static float GetEffectDuration (NWEffect eNWEffect) {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!STRUCTS!GETDURATION", "          ");
        return NWScript.stringToFloat(NWScript.getLocalString(NWObject.MODULE, "NWNX!STRUCTS!GETDURATION"));
    }

    public static float GetEffectDurationRemaining (NWEffect eNWEffect) {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!STRUCTS!GETDURATIONREMAINING", "          ");
        return NWScript.stringToFloat(NWScript.getLocalString(NWObject.MODULE, "NWNX!STRUCTS!GETDURATIONREMAINING"));
    }

    public static int GetEffectInteger (NWEffect eNWEffect, int nIndex) {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!STRUCTS!GETINTEGER", NWScript.intToString(nIndex) + "          ");
        return NWScript.stringToInt(NWScript.getLocalString(NWObject.MODULE, "NWNX!STRUCTS!GETINTEGER"));
    }

    public static void SetEffectInteger (NWEffect eNWEffect, int nIndex, int nValue) {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!STRUCTS!SETINTEGER", NWScript.intToString(nIndex) + " " + NWScript.intToString(nValue));
    }

    public static void SetEffectSpellId (NWEffect eNWEffect, int nSpellId) {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!STRUCTS!SETSPELLID", NWScript.intToString(nSpellId));
    }

    public static int GetEffectTrueType (NWEffect eNWEffect) {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!STRUCTS!GETTRUETYPE", "          ");
        return NWScript.stringToInt(NWScript.getLocalString(NWObject.MODULE, "NWNX!STRUCTS!GETTRUETYPE"));
    }

    public static void SetEffectTrueType (NWEffect eNWEffect, int nTrueType) {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!STRUCTS!SETTRUETYPE", NWScript.intToString(nTrueType));
    }

    public static void SetEffectCreator (NWEffect eNWEffect, NWObject oCreator) {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!STRUCTS!SETCREATOR", "" + oCreator.getObjectId());
    }


    public static int GetHasMatchingEffect (NWObject oNWObject, NWObject oCreator, int nSpellId, int nTrueType, int nInt0) {
        NWScript.setLocalString(oNWObject, "NWNX!STRUCTS!GETHASNWEffect", oCreator.getObjectId() + " " +
                NWScript.intToString(nSpellId) + " " + NWScript.intToString(nTrueType) + " " + NWScript.intToString(nInt0));
        return NWScript.stringToInt(NWScript.getLocalString(oNWObject, "NWNX!STRUCTS!GETHASNWEffect"));
    }

    public static int GetHasEffectByCreator (NWObject oCreator, NWObject oNWObject, int nSpellId) {
        return GetHasMatchingEffect(oNWObject, oCreator, nSpellId, -1, 0);
    }

    public static int GetHasEffectOfTrueType (int nTrueType, NWObject oNWObject, int nSpellId) {
        return GetHasMatchingEffect(oNWObject, NWObject.INVALID, nSpellId, nTrueType, 0);
    }

    public static int GetHasEffectState (int nState, NWObject oNWObject, NWObject oCreator, int nSpellId) {
        return GetHasMatchingEffect(oNWObject, oCreator, nSpellId, EFFECT_TRUETYPE_SETSTATE, nState);
    }


    public static NWObject GetItemPropertyCreator (NWItemProperty ipProp) {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!STRUCTS!GETCREATORREQUEST", " ");
        return NWScript.getLocalObject(NWObject.MODULE, "NWNX!STRUCTS!GETCREATOR");
    }

    public static float GetItemPropertyDuration (NWItemProperty ipProp) {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!STRUCTS!GETDURATION", "          ");
        return NWScript.stringToFloat(NWScript.getLocalString(NWObject.MODULE, "NWNX!STRUCTS!GETDURATION"));
    }

    public static float GetItemPropertyDurationRemaining (NWItemProperty ipProp) {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!STRUCTS!GETDURATIONREMAINING", "          ");
        return NWScript.stringToFloat(NWScript.getLocalString(NWObject.MODULE, "NWNX!STRUCTS!GETDURATIONREMAINING"));
    }

    public static int GetItemPropertyInteger (NWItemProperty ipProp, int nIndex) {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!STRUCTS!GETINTEGER", NWScript.intToString(nIndex) + "          ");
        return NWScript.stringToInt(NWScript.getLocalString(NWObject.MODULE, "NWNX!STRUCTS!GETINTEGER"));
    }

    public static void SetItemPropertyInteger (final NWItemProperty ipProp, int nIndex, int nValue) {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!STRUCTS!SETINTEGER", NWScript.intToString(nIndex) + " " + NWScript.intToString(nValue));
    }

    public static int GetItemPropertySpellId (NWItemProperty ipProp) {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!STRUCTS!GETSPELLID", "          ");
        return NWScript.stringToInt(NWScript.getLocalString(NWObject.MODULE, "NWNX!STRUCTS!GETSPELLID"));
    }

    public static void SetItemPropertySpellId (NWItemProperty ipProp, int nSpellId) {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!STRUCTS!SETSPELLID", NWScript.intToString(nSpellId));
    }

    public static void SetItemPropertyCreator (NWItemProperty ipProp, NWObject oCreator) {
        NWScript.setLocalString(NWObject.MODULE, "NWNX!STRUCTS!SETCREATOR", "" + oCreator.getObjectId());
    }


    public static NWEffect EffectBonusFeat (int nFeat) {
        NWEffect eEff = NWScript.effectVisualEffect(nFeat, false);

        SetEffectTrueType(eEff, EFFECT_TRUETYPE_BONUS_FEAT);
        return eEff;
    }

    public static NWEffect EffectIcon (int nIcon) {
        NWEffect eEff = NWScript.effectVisualEffect(nIcon, false);

        SetEffectTrueType(eEff, EFFECT_TRUETYPE_ICON);
        return eEff;
    }

}
