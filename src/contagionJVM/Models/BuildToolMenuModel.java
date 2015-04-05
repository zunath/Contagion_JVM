package contagionJVM.Models;

import org.nwnx.nwnx2.jvm.NWLocation;
import org.nwnx.nwnx2.jvm.NWObject;

import java.util.ArrayList;
import java.util.List;

public class BuildToolMenuModel {

    private List<NWObject> nearbyStructures;
    private NWObject activeStructure;
    private boolean isConfirmingRaze;
    private NWLocation targetLocation;

    public BuildToolMenuModel()
    {
        nearbyStructures = new ArrayList<>();
        isConfirmingRaze = false;
    }

    public List<NWObject> getNearbyStructures() {
        return nearbyStructures;
    }

    public void setNearbyStructures(List<NWObject> nearbyStructures) {
        this.nearbyStructures = nearbyStructures;
    }

    public NWObject getActiveStructure() {
        return activeStructure;
    }

    public void setActiveStructure(NWObject activeStructure) {
        this.activeStructure = activeStructure;
    }

    public boolean isConfirmingRaze() {
        return isConfirmingRaze;
    }

    public void setIsConfirmingRaze(boolean isConfirmingRaze) {
        this.isConfirmingRaze = isConfirmingRaze;
    }

    public NWLocation getTargetLocation() {
        return targetLocation;
    }

    public void setTargetLocation(NWLocation targetLocation) {
        this.targetLocation = targetLocation;
    }
}
