package contagionJVM.GameObject;

import contagionJVM.Constants;
import contagionJVM.Entities.PlayerEntity;
import org.nwnx.nwnx2.jvm.NWLocation;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.constants.Inventory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

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
        NWObject oDatabase = NWScript.getItemPossessedBy(_pc, Constants.PCDatabaseTag);
        return NWScript.getLocalString(oDatabase, Constants.PCIDNumberVariable);
    }

    public PlayerEntity createEntity()
    {
        NWObject database = NWScript.getItemPossessedBy(_pc, Constants.PCDatabaseTag);
        String uuid = NWScript.getLocalString(database, Constants.PCIDNumberVariable);
        NWLocation location = NWScript.getLocation(_pc);

        PlayerEntity entity = new PlayerEntity();
        entity.setPCID(uuid);
        entity.setCharacterName(NWScript.getName(_pc, false));
        entity.setHitPoints(NWScript.getCurrentHitPoints(_pc));
        entity.setLocationAreaTag(NWScript.getTag(NWScript.getArea(_pc)));
        entity.setLocationOrientation(NWScript.getFacing(_pc));
        entity.setLocationX(location.getX());
        entity.setLocationY(location.getY());
        entity.setLocationZ(location.getZ());
        entity.setInfectionCap(100);
        entity.setMaxHunger(100);
        entity.setCurrentHunger(100);
        entity.setMaxThirst(100);
        entity.setCurrentThirst(100);
        entity.setInfectionRemovalTick(600);
        entity.setCreateTimestamp(new Date());
        entity.setUnallocatedSP(10);
        entity.setLevel(1);
        entity.setExperience(0);
        entity.setLastSPResetDate(null);
        entity.setNumberOfSPResets(0);

        return entity;
    }

    public void destroyAllInventoryItems()
    {
        for(NWObject item : NWScript.getItemsInInventory(_pc))
        {
            NWScript.destroyObject(item, 0.0f);
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

    public Date getCreateDate()
    {
        NWObject database = GetDatabaseItem();
        String dateString = NWScript.getLocalString(database, "PC_CREATE_DATE");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));

        try
        {
            return format.parse(dateString);
        }
        catch (ParseException e) {
            return null;
        }
    }

    public void setCreateDate(Date value)
    {
        NWObject database = GetDatabaseItem();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        String dateString = format.format(value);

        NWScript.setLocalString(database, "PC_CREATE_DATE", dateString);
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

        Date createDate = getCreateDate();
        boolean hasOverride = getHasPVPSanctuaryOverride();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(createDate);
        calendar.add(Calendar.HOUR_OF_DAY, 72);
        Date sanctuaryCutOffDate = calendar.getTime();
        Date currentDate = new Date();


        if(hasOverride || currentDate.after(sanctuaryCutOffDate))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

}
