package contagionJVM.Dialog;

import org.nwnx.nwnx2.jvm.NWObject;

import java.util.HashMap;

@SuppressWarnings("UnusedDeclaration")
public class PlayerDialog {
    private HashMap<String, DialogPage> dialogPages;
    private String currentPageName;
    private int pageOffset;
    private String activeDialogName;
    private NWObject dialogTarget;
    private Object dialogCustomData;
    private boolean isEnding;

    public PlayerDialog()
    {
        dialogPages = new HashMap<>();
        currentPageName = "";
        pageOffset = 0;
    }

    public void addPage(String pageName, DialogPage page)
    {
        dialogPages.put(pageName, page);
        if(dialogPages.size() == 1)
        {
            currentPageName = pageName;
        }
    }

    public String getCurrentPageName() {
        return currentPageName;
    }

    public void setCurrentPageName(String currentPageName) {
        this.currentPageName = currentPageName;
    }

    public DialogPage getCurrentPage()
    {
        return dialogPages.get(currentPageName);
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

    public void resetPage()
    {
        currentPageName = (String)dialogPages.keySet().toArray()[0];
    }

    public NWObject getDialogTarget() {
        return dialogTarget;
    }

    public void setDialogTarget(NWObject dialogTarget) {
        this.dialogTarget = dialogTarget;
    }

    public Object getDialogCustomData() {
        return dialogCustomData;
    }

    public void setDialogCustomData(Object dialogData) {
        this.dialogCustomData = dialogData;
    }

    public DialogPage getPageByName(String pageName)
    {
        return dialogPages.get(pageName);
    }

    public boolean isEnding() {
        return isEnding;
    }

    public void setIsEnding(boolean isEnding) {
        this.isEnding = isEnding;
    }
}
