package contagionJVM.Entities;

import javax.persistence.*;

@Entity
@Table(name = "pc_territory_flags_permissions")
public class PCTerritoryFlagPermissionEntity {

    @Id
    @Column(name = "PCTerritoryFlagPermissionID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int pcTerritoryFlagPermissionID;

    @Column(name = "PlayerID")
    private String playerID;

    @Column(name = "TerritoryFlagPermissionID")
    private int territoryFlagPermissionID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PCTerritoryFlagID")
    private PCCorpseEntity pcTerritoryFlag;

    public int getPcTerritoryFlagPermissionID() {
        return pcTerritoryFlagPermissionID;
    }

    public void setPcTerritoryFlagPermissionID(int pcTerritoryFlagPermissionID) {
        this.pcTerritoryFlagPermissionID = pcTerritoryFlagPermissionID;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public int getTerritoryFlagPermissionID() {
        return territoryFlagPermissionID;
    }

    public void setTerritoryFlagPermissionID(int territoryFlagPermissionID) {
        this.territoryFlagPermissionID = territoryFlagPermissionID;
    }

    public PCCorpseEntity getPcTerritoryFlag() {
        return pcTerritoryFlag;
    }

    public void setPcTerritoryFlag(PCCorpseEntity pcTerritoryFlag) {
        this.pcTerritoryFlag = pcTerritoryFlag;
    }
}
