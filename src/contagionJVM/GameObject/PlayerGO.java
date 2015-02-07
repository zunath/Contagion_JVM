package contagionJVM.GameObject;

import contagionJVM.Constants;
import contagionJVM.Entities.PlayerEntity;
import org.nwnx.nwnx2.jvm.NWLocation;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.constants.Inventory;

import java.util.Date;

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


}
