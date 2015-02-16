package contagionJVM.Dialog;

@SuppressWarnings("UnusedDeclaration")
public class DialogResponse {
    private String text;
    private boolean isActive;

    public DialogResponse(String text)
    {
        this.text = text;
        this.isActive = true;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
