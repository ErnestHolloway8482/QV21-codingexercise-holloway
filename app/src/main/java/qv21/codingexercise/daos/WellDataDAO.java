package qv21.codingexercise.daos;

import java.util.List;

import qv21.codingexercise.models.database.Well;

public interface WellDataDAO {
    boolean createWell(final Well well);
    List<Well> getAllWells();
    Well getWellByName(final String wellName);
    boolean updateWell(final Well updatedWell);
    boolean deleteAllWells();
}
