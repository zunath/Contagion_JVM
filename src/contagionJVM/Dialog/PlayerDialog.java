package contagionJVM.Dialog;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnusedDeclaration")
public class PlayerDialog {
    private List<DialogPage> dialogPages;
    private int currentPageID;
    private int pageOffset;
    private String activeDialogName;

    public PlayerDialog()
    {
        dialogPages = new ArrayList<>();
        currentPageID = 0;
        pageOffset = 0;
    }

    public PlayerDialog(DialogPage page)
    {
        dialogPages = new ArrayList<>();
        dialogPages.add(page);
        currentPageID = 0;
        pageOffset = 0;
    }

    public PlayerDialog(ArrayList<DialogPage> pages)
    {
        dialogPages = pages;
        currentPageID = 0;
        pageOffset = 0;
    }

    public List<DialogPage> getDialogPages() {
        return dialogPages;
    }

    public void setDialogPages(List<DialogPage> dialogPages) {
        this.dialogPages = dialogPages;
    }

    public int getCurrentPageID() {
        return currentPageID;
    }

    public void setCurrentPageID(int currentPageID) {
        this.currentPageID = currentPageID;
    }

    public DialogPage getCurrentPage()
    {
        return dialogPages.get(currentPageID);
    }

    public int getPageOffset() {
        return pageOffset;
    }

    public void setPageOffset(int pageOffset) {

        if(pageOffset < 0) pageOffset = 0;

        this.pageOffset = pageOffset;
    }

    public String getActiveDialogName() {
        return activeDialogName;
    }

    public void setActiveDialogName(String activeDialogName) {
        this.activeDialogName = activeDialogName;
    }
}
