package contagionJVM.System;

import contagionJVM.Constants;
import contagionJVM.Entities.PlayerEntity;
import contagionJVM.Enumerations.CustomItemProperty;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.Helper.ColorToken;
import contagionJVM.Repository.PlayerRepository;
import org.nwnx.nwnx2.jvm.*;
import org.nwnx.nwnx2.jvm.constants.DurationType;
import org.nwnx.nwnx2.jvm.constants.EffectType;

import java.util.Objects;

public class InventorySystem {

    private static final int BaseInventoryLimit = 15;
    private static final int NumberOfSystemItems = 5;


    public static void OnModuleAcquireItem()
    {
        NWObject oPC = NWScript.getModuleItemAcquiredBy();
        NWObject oItem = NWScript.getModuleItemAcquired();
        RunItemLimitCheck(oPC, oItem);
    }

    public static void OnModuleEquipItem()
    {
        NWObject oPC = NWScript.getPCItemLastEquippedBy();
        NWObject oItem = NWScript.getPCItemLastEquipped();
        RunItemLimitCheck(oPC, oItem);
    }

    public static void OnModuleUnAcquireItem()
    {
        NWObject oPC = NWScript.getModuleItemLostBy();
        NWObject oItem = NWScript.getModuleItemLost();
        RunItemLimitCheck(oPC, oItem);
    }

    public static void OnModuleUnEquipItem()
    {
        final NWObject oPC = NWScript.getPCItemLastUnequippedBy();
        final NWObject oItem = NWScript.getPCItemLastUnequipped();
        Scheduler.delay(oPC, 100, new Runnable() {
            @Override
            public void run() {
                RunItemLimitCheck(oPC, oItem);
            }
        });
    }

    private static int GetItemCount(NWObject oPC)
    {
        return NWScript.getItemsInInventory(oPC).length;
    }

    private static boolean IsItemExempt(NWObject oItem)
    {
        String sResref = NWScript.getResRef(oItem);
        String sTag = NWScript.getTag(oItem);
        String sName = NWScript.getName(oItem, false);

        // Specific items
        if(Objects.equals(sResref, Constants.PCDatabaseTag) ||  // Database item
                Objects.equals(sResref, "dmfi_dicebag") ||  // DMFI Dicebag
                Objects.equals(sResref, "dmfi_pc_emote") ||  // DMFI PC Emote Wand
                Objects.equals(sResref, "fky_chat_target") ||  // SimTools Command Targeter
                Objects.equals(sResref, "firearm_magazine") ||  // Combat system firearm magazine
                Objects.equals(sResref, "e_gun_mag") ||  // Combat system enhanced firearm magazine
                Objects.equals(sResref, "i_gun_mag") ||  // Combat system incendiary firearm magazine
                Objects.equals(sResref, "nw_it_gold001") ||  // Gold
                Objects.equals(sResref, "rhs_furn_tool") ||  // Furniture Tool
                Objects.equals(sResref, "") ||  // Item doesn't have a resref
                NWScript.getLocalInt(oItem, "ZEP_CR_TEMPITEM") != 0 ||  // CEP Crafting System - Prevents a bug when PC tries to craft armor appearance on full inventory
                Objects.equals(sName, "PC Properties") ||               // Patch 1.69 PC properties skin. Can't get the tag of this for whatever reason so I use its name.
                Objects.equals(NWScript.getStringLeft(sTag, 8), "KEYITEM_")) // Key items
            return true;



        return false;
    }

    private static int GetPlayerInventoryLimit(NWObject oPC)
    {
        PlayerGO pcGO = new PlayerGO(oPC);
        PlayerEntity entity = new PlayerRepository().getByUUID(pcGO.getUUID());
        int slots = BaseInventoryLimit;

        if(entity != null)
        {
            slots += entity.getInventorySpaceBonus();
        }

        int equipBonusSlots = 0;
        for(int invSlot = 0; invSlot < Constants.NumberOfInventorySlots; invSlot++)
        {
            NWObject oItem = NWScript.getItemInSlot(invSlot, oPC);
            if(!oItem.equals(NWObject.INVALID))
            {
                int itemBonusCount = 0;
                for(NWItemProperty ip : NWScript.getItemProperties(oItem))
                {
                    if(NWScript.getItemPropertyType(ip) == CustomItemProperty.InventorySpaceBonus)
                    {
                        int count = NWScript.getItemPropertyCostTableValue(ip);
                        if(count > itemBonusCount)
                        {
                            itemBonusCount = count;
                        }
                    }
                }

                equipBonusSlots += itemBonusCount;
            }
        }

        return slots + equipBonusSlots;
    }

    private static void RunItemLimitCheck(NWObject oPC, NWObject oItem)
    {
        if(!NWScript.getIsPC(oPC) || NWScript.getIsDM(oPC) || IsItemExempt(oItem)) return;

        int numberOfItems = GetItemCount(oPC) - NumberOfSystemItems;
        int limit = GetPlayerInventoryLimit(oPC);

        NWEffect[] effects = NWScript.getEffects(oPC);
        for(NWEffect effect : effects)
        {
            if(effect.getEffectId() == EffectType.MOVEMENT_SPEED_DECREASE ||
                    effect.getEffectId() == EffectType.CUTSCENEIMMOBILIZE)
            {
                NWScript.removeEffect(oPC, effect);
            }
        }

        if(numberOfItems > limit)
        {
            NWScript.sendMessageToPC(oPC, ColorToken.Gray() + "Inventory: " + ColorToken.End() +
                    ColorToken.Red() + numberOfItems + ColorToken.End() +
                    ColorToken.Gray() + " / " + limit + ColorToken.End() +
                    ColorToken.Red() + " (ENCUMBERED)" + ColorToken.End());

            int speedDecreaseAmount = (numberOfItems - limit) * 15;

            if(speedDecreaseAmount > 99)
            {
                NWScript.applyEffectToObject(DurationType.PERMANENT, NWScript.effectCutsceneImmobilize(), oPC, 0.0f);
            }
            else
            {
                NWScript.applyEffectToObject(DurationType.PERMANENT, NWScript.effectMovementSpeedDecrease(speedDecreaseAmount), oPC, 0.0f);
            }
        }
        else
        {
            String color = numberOfItems > limit ? ColorToken.Red() : ColorToken.Gray();
            NWScript.sendMessageToPC(oPC, ColorToken.Gray() + "Inventory: " + numberOfItems + " / " + color + limit + ColorToken.End());

        }
    }


}

