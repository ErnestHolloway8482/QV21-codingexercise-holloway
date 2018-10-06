package qv21.codingexercise.managers;

import javax.inject.Singleton;

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
