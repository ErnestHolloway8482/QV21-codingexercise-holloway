package qv21.codingexercise.models.database;

import java.util.List;

public class Owner {
    private String ownerName;
    private List<Well> wells;

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(final String ownerName) {
        this.ownerName = ownerName;
    }

    public List<Well> getWells() {
        return wells;
    }

    public void setWells(final List<Well> wells) {
        this.wells = wells;
    }
}
