package contagionJVM.Entities;

import javax.persistence.*;

@Entity
@Table(name = "structures")
public class StructureEntity {

    @Id
    @Column(name = "StructureID")
    private int structureID;

    @Column(name = "StructureCategoryID")
    private int structureCategoryID;

    @Column(name = "Name")
    private String name;

    @Column(name = "Description")
    private String description;

    @Column(name = "Resref")
    private String resref;

    @Column(name = "IsActive")
    private boolean isActive;

    @Column(name = "WoodRequired")
    private int woodRequired;

    @Column(name = "MetalRequired")
    private int metalRequired;

    @Column(name = "NailsRequired")
    private int nailsRequired;

    @Column(name = "ClothRequired")
    private int clothRequired;

    @Column(name = "LeatherRequired")
    private int leatherRequired;

    @Column(name = "ItemStorageCount")
    private int itemStorageCount;

    public int getStructureID() {
        return structureID;
    }

    public void setStructureID(int structureID) {
        this.structureID = structureID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getWoodRequired() {
        return woodRequired;
    }

    public void setWoodRequired(int woodRequired) {
        this.woodRequired = woodRequired;
    }

    public int getNailsRequired() {
        return nailsRequired;
    }

    public void setNailsRequired(int nailsRequired) {
        this.nailsRequired = nailsRequired;
    }

    public int getClothRequired() {
        return clothRequired;
    }

    public void setClothRequired(int clothRequired) {
        this.clothRequired = clothRequired;
    }

    public int getLeatherRequired() {
        return leatherRequired;
    }

    public void setLeatherRequired(int leatherRequired) {
        this.leatherRequired = leatherRequired;
    }

    public int getItemStorageCount() {
        return itemStorageCount;
    }

    public void setItemStorageCount(int itemStorageCount) {
        this.itemStorageCount = itemStorageCount;
    }

    public String getResref() {
        return resref;
    }

    public void setResref(String resref) {
        this.resref = resref;
    }

    public int getMetalRequired() {
        return metalRequired;
    }

    public void setMetalRequired(int metalRequired) {
        this.metalRequired = metalRequired;
    }
}
