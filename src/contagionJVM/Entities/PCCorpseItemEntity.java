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

    @Lob
    @Column(name="NWItemObject")
    private byte[] item;

    @ManyToOne
    @JoinColumn(name = "PCCorpseID")
    private PCCorpseEntity corpse;

    public int getPcCorpseItemID() {
        return pcCorpseItemID;
    }

    public void setPcCorpseItemID(int pcCorpseItemID) {
        this.pcCorpseItemID = pcCorpseItemID;
    }

    public byte[] getItem() {
        return item;
    }

    public void setItem(byte[] item) {
        this.item = item;
    }

    public PCCorpseEntity getCorpse() {
        return corpse;
    }

    public void setCorpse(PCCorpseEntity corpse) {
        this.corpse = corpse;
    }
}
