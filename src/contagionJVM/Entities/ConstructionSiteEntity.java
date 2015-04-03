package contagionJVM.Entities;

import javax.persistence.*;

@Entity
@Table(name = "construction_sites")
public class ConstructionSiteEntity {

    @Id
    @Column(name = "ConstructionSiteID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int constructionSiteID;

    @Column(name = "PCTerritoryFlagID")
    private int pcTerritoryFlagID;

    @Column(name = "WoodRequired")
    private int woodRequired;

    @Column(name = "NailsRequired")
    private int nailsRequired;

    @Column(name = "ClothRequired")
    private int clothRequired;

    @Column(name = "LeatherRequired")
    private int leatherRequired;

    @Column(name = "LocationAreaTag")
    private String locationAreaTag;

    @Column(name = "LocationX")
    private double locationX;

    @Column(name = "LocationY")
    private double locationY;

    @Column(name = "LocationZ")
    private double locationZ;

    @Column(name = "LocationOrientation")
    private double locationOrientation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TerritoryFlagID")
    private TerritoryFlagEntity territoryFlag;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "StructureID")
    private StructureEntity structure;

    public int getConstructionSiteID() {
        return constructionSiteID;
    }

    public void setConstructionSiteID(int constructionSiteID) {
        this.constructionSiteID = constructionSiteID;
    }

    public int getPcTerritoryFlagID() {
        return pcTerritoryFlagID;
    }

    public void setPcTerritoryFlagID(int pcTerritoryFlagID) {
        this.pcTerritoryFlagID = pcTerritoryFlagID;
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

    public String getLocationAreaTag() {
        return locationAreaTag;
    }

    public void setLocationAreaTag(String locationAreaTag) {
        this.locationAreaTag = locationAreaTag;
    }

    public double getLocationX() {
        return locationX;
    }

    public void setLocationX(double locationX) {
        this.locationX = locationX;
    }

    public double getLocationY() {
        return locationY;
    }

    public void setLocationY(double locationY) {
        this.locationY = locationY;
    }

    public double getLocationZ() {
        return locationZ;
    }

    public void setLocationZ(double locationZ) {
        this.locationZ = locationZ;
    }

    public double getLocationOrientation() {
        return locationOrientation;
    }

    public void setLocationOrientation(double locationOrientation) {
        this.locationOrientation = locationOrientation;
    }

    public TerritoryFlagEntity getTerritoryFlag() {
        return territoryFlag;
    }

    public void setTerritoryFlag(TerritoryFlagEntity territoryFlag) {
        this.territoryFlag = territoryFlag;
    }

    public StructureEntity getStructure() {
        return structure;
    }

    public void setStructure(StructureEntity structure) {
        this.structure = structure;
    }
}
