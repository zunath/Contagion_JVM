package contagionJVM.Entities;

import javax.persistence.*;

@SuppressWarnings("UnusedDeclaration")
@Entity
@Table(name="pc_corpse_items")
public class PCCorpseItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="PCCorpseItemID")
    private int pcCorpseItemID;

    @Column(name="PCCorpseID")
    private int pcCorpseID;

    @Lob
    @Column(name="NWItemObject")
    private byte[] item;

    public int getPcCorpseItemID() {
        return pcCorpseItemID;
    }

    public void setPcCorpseItemID(int pcCorpseItemID) {
        this.pcCorpseItemID = pcCorpseItemID;
    }

    public int getPcCorpseID() {
        return pcCorpseID;
    }

    public void setPcCorpseID(int pcCorpseID) {
        this.pcCorpseID = pcCorpseID;
    }

    public byte[] getItem() {
        return item;
    }

    public void setItem(byte[] item) {
        this.item = item;
    }
}
