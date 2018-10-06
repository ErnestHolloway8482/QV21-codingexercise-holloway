package qv21.codingexercise.models.viewmodels;

import android.databinding.ObservableField;

import qv21.codingexercise.managers.MemoryCacheManager;
import qv21.codingexercise.models.database.WellData;

public class WellDataDetailsVM {
    private final MemoryCacheManager memoryCacheManager;

    public ObservableField<WellData> wellData = new ObservableField<>();

    public WellDataDetailsVM(final MemoryCacheManager memoryCacheManager){
        this.memoryCacheManager = memoryCacheManager;
    }

    public void navigateToWellDataListScreen(){
        memoryCacheManager.setSelectedWellDataUuid(null);
    }

    public void navigateToWellDataEditScreen(){

    }


}
