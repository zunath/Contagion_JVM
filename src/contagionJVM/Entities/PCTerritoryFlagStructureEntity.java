package contagionJVM.Entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pc_territory_flags_structures")
public class PCTerritoryFlagStructureEntity {

    @Id
    @Column(name = "PCTerritoryFlagStructureID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int pcTerritoryFlagStructureID;

    @Column(name = "StructureBlueprintID")
    private int structureBlueprintID;

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

    @Column(name = "IsUseable")
    private boolean isUseable;

    @Column(name = "CreateDate", insertable = false)
    private Date createDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PCTerritoryFlagID")
    private PCTerritoryFlagEntity pcTerritoryFlag;

    public int getPcTerritoryFlagStructureID() {
        return pcTerritoryFlagStructureID;
    }

    public void setPcTerritoryFlagStructureID(int pcTerritoryFlagStructureID) {
        this.pcTerritoryFlagStructureID = pcTerritoryFlagStructureID;
    }

    public int getStructureBlueprintID() {
        return structureBlueprintID;
    }

    public void setStructureBlueprintID(int structureBlueprintID) {
        this.structureBlueprintID = structureBlueprintID;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public PCTerritoryFlagEntity getPcTerritoryFlag() {
        return pcTerritoryFlag;
    }

    public void setPcTerritoryFlag(PCTerritoryFlagEntity pcTerritoryFlag) {
        this.pcTerritoryFlag = pcTerritoryFlag;
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

    public boolean isUseable() {
        return isUseable;
    }

    public void setIsUseable(boolean isUseable) {
        this.isUseable = isUseable;
    }
}
