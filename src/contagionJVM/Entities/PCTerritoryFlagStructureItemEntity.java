package contagionJVM.Entities;


import javax.persistence.*;

@Entity
@Table(name = "pc_territory_flags_structures_items")
public class PCTerritoryFlagStructureItemEntity {

    @Id
    @Column(name = "PCStructureItemID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int pcStructureItemID;

    @Column(name = "PCStructureID")
    private int pcStructureID;

    @Column(name = "ItemObject")
    private byte[] itemObject;

    public int getPcStructureItemID() {
        return pcStructureItemID;
    }

    public void setPcStructureItemID(int pcStructureItemID) {
        this.pcStructureItemID = pcStructureItemID;
    }

    public int getPcStructureID() {
        return pcStructureID;
    }

    public void setPcStructureID(int pcStructureID) {
        this.pcStructureID = pcStructureID;
    }

    public byte[] getItemObject() {
        return itemObject;
    }

    public void setItemObject(byte[] itemObject) {
        this.itemObject = itemObject;
    }
}
