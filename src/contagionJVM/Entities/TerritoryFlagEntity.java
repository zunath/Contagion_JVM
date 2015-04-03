package contagionJVM.Entities;

import javax.persistence.*;

@Entity
@Table(name = "territory_flags")
public class TerritoryFlagEntity {

    @Id
    @Column(name = "TerritoryFlagID")
    private int territoryFlagID;

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

    @Column(name = "NailsRequired")
    private int nailsRequired;

    @Column(name = "ClothRequired")
    private int clothRequired;

    @Column(name = "LeatherRequired")
    private int leatherRequired;

    @Column(name = "MaxStructuresCount")
    private int maxStructuresCount;

    @Column(name = "MaxBuildDistance")
    private double maxBuildDistance;

    public int getTerritoryFlagID() {
        return territoryFlagID;
    }

    public void setTerritoryFlagID(int territoryFlagID) {
        this.territoryFlagID = territoryFlagID;
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

    public int getMaxStructuresCount() {
        return maxStructuresCount;
    }

    public void setMaxStructuresCount(int maxStructuresCount) {
        this.maxStructuresCount = maxStructuresCount;
    }

    public String getResref() {
        return resref;
    }

    public void setResref(String resref) {
        this.resref = resref;
    }

    public double getMaxBuildDistance() {
        return maxBuildDistance;
    }

    public void setMaxBuildDistance(double maxBuildDistance) {
        this.maxBuildDistance = maxBuildDistance;
    }
}
