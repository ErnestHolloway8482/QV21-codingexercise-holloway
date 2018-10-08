package qv21.codingexercise.models.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import io.objectbox.android.AndroidScheduler;
import io.objectbox.reactive.DataObserver;
import io.objectbox.reactive.DataSubscriptionList;
import qv21.codingexercise.adapters.WellDataListRecyclerAdapter;
import qv21.codingexercise.facades.WellDataFacade;
import qv21.codingexercise.models.databasemodels.WellDataDM;

public class WellDataListVM extends ViewModel {
    private final WellDataFacade wellDataFacade;

    private DataSubscriptionList subscriber;

    public ObservableField<RecyclerView.Adapter> adapter = new ObservableField<>();
    public ObservableBoolean isListEmpty = new ObservableBoolean();

    public WellDataListVM(final WellDataFacade wellDataFacade) {
        this.wellDataFacade = wellDataFacade;

        setupRecyclerViewAdapater();

        getWellDataFromDatabase();
    }

    private void setupRecyclerViewAdapater() {
        WellDataListRecyclerAdapter wellDataListRecyclerAdapter = new WellDataListRecyclerAdapter();
        adapter.set(wellDataListRecyclerAdapter);
    }

    private void getWellDataFromDatabase() {
        cleanupSubscribers();

        subscriber = new DataSubscriptionList();

        wellDataFacade.getAllWellDataQuery()
                .subscribe(subscriber)
                .on(AndroidScheduler.mainThread()).observer(new DataObserver<List<WellDataDM>>() {
            @Override
            public void onData(final List<WellDataDM> data) {
                updateRecyclerAdapter(data);
            }
        });
    }

    private void updateRecyclerAdapter(final List<WellDataDM> wellDataList) {
        if (wellDataList == null || wellDataList.isEmpty()) {
            isListEmpty.set(true);
        } else {
            isListEmpty.set(false);

            ((WellDataListRecyclerAdapter) adapter.get()).setData(wellDataList);
        }

        cleanupSubscribers();
    }

    private void cleanupSubscribers() {
        if (subscriber != null) {
            if (!subscriber.isCanceled()) {
                subscriber.cancel();
                subscriber = null;
            }
        }
    }
}
