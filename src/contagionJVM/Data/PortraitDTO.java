package contagionJVM.Data;

public class PortraitDTO {

    private int originalPortraitID;
    private int currentPortraitID;

    public PortraitDTO(int originalPortraitID)
    {
        this.originalPortraitID = originalPortraitID;
        this.currentPortraitID = originalPortraitID;
    }

    public int getOriginalPortraitID() {
        return originalPortraitID;
    }

    public void setOriginalPortraitID(int originalPortraitID) {
        this.originalPortraitID = originalPortraitID;
    }

    public int getCurrentPortraitID() {
        return currentPortraitID;
    }

    public void setCurrentPortraitID(int currentPortraitID) {
        this.currentPortraitID = currentPortraitID;
    }
}
