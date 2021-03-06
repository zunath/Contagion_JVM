package contagionJVM.GameObject;

import contagionJVM.Constants;
import contagionJVM.Entities.PlayerEntity;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.nwnx.nwnx2.jvm.*;
import org.nwnx.nwnx2.jvm.constants.Inventory;
import java.util.Date;
import java.util.UUID;

public class PlayerGO {
    private NWObject _pc;

    public PlayerGO(NWObject pc)
    {
        _pc = pc;
    }

    public NWObject GetDatabaseItem()
    {
        return NWScript.getItemPossessedBy(_pc, Constants.PCDatabaseTag);
    }

    public String getUUID()
    {
        String uuid = NWScript.getLocalString(_pc, Constants.PCIDNumberVariable);

        if(NWScript.getIsDM(_pc))
        {
            if(uuid.equals(""))
            {
                uuid = UUID.randomUUID().toString();
                NWScript.setLocalString(_pc, Constants.PCIDNumberVariable, uuid);
            }
        }
        else
        {
            NWObject oDatabase = GetDatabaseItem();
            if(uuid.equals(""))
            {
                uuid = NWScript.getLocalString(oDatabase, Constants.PCIDNumberVariable);
            }
            if(uuid.equals(""))
            {
                uuid = UUID.randomUUID().toString();
            }

            NWScript.setLocalString(oDatabase, Constants.PCIDNumberVariable, uuid);
            NWScript.setLocalString(_pc, Constants.PCIDNumberVariable, uuid);
        }

        return uuid;
    }

    public PlayerEntity createEntity()
    {
        NWLocation location = NWScript.getLocation(_pc);

        PlayerEntity entity = new PlayerEntity();
        entity.setPCID(getUUID());
        entity.setCharacterName(NWScript.getName(_pc, false));
        entity.setHitPoints(NWScript.getMaxHitPoints(_pc));
        entity.setLocationAreaTag(NWScript.getTag(NWScript.getArea(_pc)));
        entity.setLocationOrientation(NWScript.getFacing(_pc));
        entity.setLocationX(location.getX());
        entity.setLocationY(location.getY());
        entity.setLocationZ(location.getZ());
        entity.setInfectionCap(100);
        entity.setMaxHunger(100);
        entity.setCurrentHunger(100);
        entity.setInfectionRemovalTick(600);
        entity.setCreateTimestamp(new Date());
        entity.setUnallocatedSP(10);
        entity.setLevel(1);
        entity.setExperience(0);
        entity.setNextSPResetDate(null);
        entity.setNumberOfSPResets(0);
        entity.setVersionNumber(Constants.PlayerVersionNumber);
        entity.setResetTokens(3);

        return entity;
    }

    public void destroyAllInventoryItems(boolean destroyDatabaseItem)
    {
        for(NWObject item : NWScript.getItemsInInventory(_pc))
        {
            if(NWScript.getResRef(item).equals(Constants.PCDatabaseTag))
            {
                if(destroyDatabaseItem)
                {
                    NWScript.destroyObject(item, 0.0f);
                }
            }
            else
            {
                NWScript.destroyObject(item, 0.0f);
            }
        }
    }

    public void destroyAllEquippedItems()
    {
        NWObject oInventory = NWScript.getItemInSlot(Inventory.SLOT_ARMS, _pc);
        NWScript.destroyObject(oInventory, 0.0f);
        oInventory = NWScript.getItemInSlot(Inventory.SLOT_ARROWS, _pc);
        NWScript.destroyObject(oInventory, 0.0f);
        oInventory = NWScript.getItemInSlot(Inventory.SLOT_BELT, _pc);
        NWScript.destroyObject(oInventory, 0.0f);
        oInventory = NWScript.getItemInSlot(Inventory.SLOT_BOLTS, _pc);
        NWScript.destroyObject(oInventory, 0.0f);
        oInventory = NWScript.getItemInSlot(Inventory.SLOT_BOOTS, _pc);
        NWScript.destroyObject(oInventory, 0.0f);
        oInventory = NWScript.getItemInSlot(Inventory.SLOT_BULLETS, _pc);
        NWScript.destroyObject(oInventory, 0.0f);
        oInventory = NWScript.getItemInSlot(Inventory.SLOT_CARMOUR, _pc);
        NWScript.destroyObject(oInventory, 0.0f);
        oInventory = NWScript.getItemInSlot(Inventory.SLOT_CHEST, _pc);
        NWScript.destroyObject(oInventory, 0.0f);
        oInventory = NWScript.getItemInSlot(Inventory.SLOT_CLOAK, _pc);
        NWScript.destroyObject(oInventory, 0.0f);
        oInventory = NWScript.getItemInSlot(Inventory.SLOT_CWEAPON_B, _pc);
        NWScript.destroyObject(oInventory, 0.0f);
        oInventory = NWScript.getItemInSlot(Inventory.SLOT_CWEAPON_L, _pc);
        NWScript.destroyObject(oInventory, 0.0f);
        oInventory = NWScript.getItemInSlot(Inventory.SLOT_CWEAPON_R, _pc);
        NWScript.destroyObject(oInventory, 0.0f);
        oInventory = NWScript.getItemInSlot(Inventory.SLOT_HEAD, _pc);
        NWScript.destroyObject(oInventory, 0.0f);
        oInventory = NWScript.getItemInSlot(Inventory.SLOT_LEFTHAND, _pc);
        NWScript.destroyObject(oInventory, 0.0f);
        oInventory = NWScript.getItemInSlot(Inventory.SLOT_LEFTRING, _pc);
        NWScript.destroyObject(oInventory, 0.0f);
        oInventory = NWScript.getItemInSlot(Inventory.SLOT_NECK, _pc);
        NWScript.destroyObject(oInventory, 0.0f);
        oInventory = NWScript.getItemInSlot(Inventory.SLOT_RIGHTHAND, _pc);
        NWScript.destroyObject(oInventory, 0.0f);
        oInventory = NWScript.getItemInSlot(Inventory.SLOT_RIGHTRING, _pc);
        NWScript.destroyObject(oInventory, 0.0f);
    }

    public void UnequipAllItems()
    {
        Scheduler.assign(_pc, new Runnable() {
            @Override
            public void run() {
                for(int slot = 0; slot < 14; slot++)
                {
                    NWScript.actionUnequipItem(NWScript.getItemInSlot(slot, _pc));
                }
            }
        });
    }

    public DateTime getCreateDate()
    {
        NWObject database = GetDatabaseItem();
        String dateString = NWScript.getLocalString(database, "PC_CREATE_DATE");
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssz");
        return formatter.withZone(DateTimeZone.UTC).parseDateTime(dateString);
    }

    public void setCreateDate()
    {
        NWObject database = GetDatabaseItem();
        setCreateDate(database);
    }

    public void setCreateDate(NWObject databaseItem)
    {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssz");
        DateTime dt = new DateTime(DateTimeZone.UTC);
        String timestamp = dt.toString(formatter);
        NWScript.setLocalString(databaseItem, "PC_CREATE_DATE", timestamp);
    }

    public void setHasPVPSanctuaryOverride(boolean value)
    {
        NWObject database = GetDatabaseItem();
        int isEnabled = value ? 1 : 0;
        NWScript.setLocalInt(database, "PVP_SANCTUARY_OVERRIDE_ENABLED", isEnabled);
    }

    public boolean getHasPVPSanctuaryOverride()
    {
        NWObject database = GetDatabaseItem();
        int isEnabled = NWScript.getLocalInt(database, "PVP_SANCTUARY_OVERRIDE_ENABLED");

        return isEnabled == 1;
    }

    public boolean hasPVPSanctuary()
    {
        if(NWScript.getIsDM(_pc) || !NWScript.getIsPC(_pc)) return false;

        DateTime createDate = getCreateDate();
        boolean hasOverride = getHasPVPSanctuaryOverride();
        DateTime currentTime = new DateTime(DateTimeZone.UTC);

        return !(hasOverride || createDate.isAfter(currentTime));
    }

    public void setIsBusy(boolean isBusy)
    {
        NWScript.setLocalInt(_pc, "IS_BUSY", isBusy ? 1 : 0);
    }

    public boolean isBusy()
    {
        return NWScript.getLocalInt(_pc, "IS_BUSY") == 1;
    }

    public void removeEffect(int effectType)
    {
        for(NWEffect effect : NWScript.getEffects(_pc))
        {
            if(NWScript.getEffectType(effect) == effectType)
            {
                NWScript.removeEffect(_pc, effect);
            }
        }
    }
}
