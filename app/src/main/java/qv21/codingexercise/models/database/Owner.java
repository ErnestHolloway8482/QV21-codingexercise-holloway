package qv21.codingexercise.models.database;

import java.util.List;

public class Owner {
    private String name;
    private List<Well> wells;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<Well> getWells() {
        return wells;
    }

    public void setWells(final List<Well> wells) {
        this.wells = wells;
    }
}
