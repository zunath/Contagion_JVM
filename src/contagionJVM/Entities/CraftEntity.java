package contagionJVM.Entities;

import javax.persistence.*;

@Entity
@Table(name = "crafts")
public class CraftEntity {

    @Id
    @Column(name = "CraftID")
    private int craftID;

    @Column(name = "Name")
    private String name;


    public int getCraftID() {
        return craftID;
    }

    public void setCraftID(int craftID) {
        this.craftID = craftID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
