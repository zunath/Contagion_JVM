package contagionJVM.Entities;

import javax.persistence.*;

@Entity
@Table(name = "territory_flag_permissions")
public class TerritoryFlagPermissionEntity {

    @Id
    @Column(name = "TerritoryFlagPermissionID")
    private int territoryFlagPermissionID;

    @Column(name = "Name")
    private String name;


    public int getTerritoryFlagPermissionID() {
        return territoryFlagPermissionID;
    }

    public void setTerritoryFlagPermissionID(int territoryFlagPermissionID) {
        this.territoryFlagPermissionID = territoryFlagPermissionID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
