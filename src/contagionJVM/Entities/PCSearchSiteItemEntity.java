package contagionJVM.Entities;

import javax.persistence.*;

@Entity
@Table(name="pc_search_site_items")
public class PCSearchSiteItemEntity {

    @Id
    @Column(name = "PCSearchSiteItemID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int searchItemID;

    @Column(name="PlayerID")
    private String pcID;

    @Column(name = "SearchSiteID")
    private int searchSiteID;

    @Lob
    @Column(name = "SearchItem")
    private byte[] searchItem;

    public int getSearchItemID() {
        return searchItemID;
    }

    public void setSearchItemID(int searchItemID) {
        this.searchItemID = searchItemID;
    }

    public String getPcID() {
        return pcID;
    }

    public void setPcID(String pcID) {
        this.pcID = pcID;
    }

    public int getSearchSiteID() {
        return searchSiteID;
    }

    public void setSearchSiteID(int searchSiteID) {
        this.searchSiteID = searchSiteID;
    }

    public byte[] getSearchItem() {
        return searchItem;
    }

    public void setSearchItem(byte[] searchItem) {
        this.searchItem = searchItem;
    }
}
