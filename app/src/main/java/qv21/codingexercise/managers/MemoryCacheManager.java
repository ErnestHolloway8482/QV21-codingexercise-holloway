package qv21.codingexercise.managers;

import javax.inject.Singleton;

/**
 * A {@link Singleton} that stores data contents in memory. This is best used whenever temporary data needs to be passed in between screens but shouldn't be stored within a database.
 */
@Singleton
public class MemoryCacheManager {
    private String selectedWellDataUuid;

    public String getSelectedWellDataUuid() {
        return selectedWellDataUuid;
    }

    public void setSelectedWellDataUuid(final String selectedWellDataUuid) {
        this.selectedWellDataUuid = selectedWellDataUuid;
    }
}
