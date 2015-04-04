package contagionJVM.Entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "pc_territory_flags")
public class PCTerritoryFlagEntity {

    @Id
    @Column(name = "PCTerritoryFlagID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int pcTerritoryFlagID;

    @Column(name = "PlayerID")
    private String playerID;

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
    @JoinColumn(name = "StructureBlueprintID")
    private StructureBlueprintEntity blueprint;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pcTerritoryFlag", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<PCTerritoryFlagStructureEntity> structures;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pcTerritoryFlag", fetch = FetchType.EAGER, orphanRemoval = true)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<PCTerritoryFlagPermissionEntity> permissions;

    public int getPcTerritoryFlagID() {
        return pcTerritoryFlagID;
    }

    public void setPcTerritoryFlagID(int pcTerritoryFlagID) {
        this.pcTerritoryFlagID = pcTerritoryFlagID;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
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

    public List<PCTerritoryFlagStructureEntity> getStructures() {
        return structures;
    }

    public void setStructures(List<PCTerritoryFlagStructureEntity> structures) {
        this.structures = structures;
    }

    public List<PCTerritoryFlagPermissionEntity> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PCTerritoryFlagPermissionEntity> permissions) {
        this.permissions = permissions;
    }

    public StructureBlueprintEntity getBlueprint() {
        return blueprint;
    }

    public void setBlueprint(StructureBlueprintEntity blueprint) {
        this.blueprint = blueprint;
    }
}
