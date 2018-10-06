package qv21.codingexercise.models.viewmodels;

import android.databinding.ObservableField;

import qv21.codingexercise.managers.MemoryCacheManager;
import qv21.codingexercise.models.database.WellData;

public class WellDataItemVM {
    private final MemoryCacheManager memoryCacheManager;

    public ObservableField<WellData> wellData = new ObservableField<>();

    public WellDataItemVM(final MemoryCacheManager memoryCacheManager){
        this.memoryCacheManager = memoryCacheManager;
    }

    public void navigateToWellDataDetailsScreen(){
        memoryCacheManager.setSelectedWellDataUuid(wellData.get().getUuid());
    }


}
