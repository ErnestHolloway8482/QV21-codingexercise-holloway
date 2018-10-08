package qv21.codingexercise.models.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.v7.widget.LinearLayoutManager;

import java.util.List;

import io.objectbox.android.AndroidScheduler;
import io.objectbox.reactive.DataSubscriptionList;
import qv21.codingexercise.activities.MainActivity;
import qv21.codingexercise.adapters.WellDataListRecyclerAdapter;
import qv21.codingexercise.facades.WellDataFacade;
import qv21.codingexercise.models.databasemodels.WellDataDM;

public class WellDataListVM extends ViewModel {
    private final WellDataFacade wellDataFacade;

    private DataSubscriptionList subscriber;

    public ObservableField<WellDataListRecyclerAdapter> recylcerViewAdapter = new ObservableField<>();
    public final ObservableField<LinearLayoutManager> linearLayoutManager = new ObservableField<>();

    public ObservableBoolean isListEmpty = new ObservableBoolean();

    public WellDataListVM(final WellDataFacade wellDataFacade) {
        this.wellDataFacade = wellDataFacade;

        setupRecyclerViewAdapater();

        getWellDataFromDatabase();
    }

    private void setupRecyclerViewAdapater() {
        linearLayoutManager.set(new LinearLayoutManager(MainActivity.getInstance()));
        recylcerViewAdapter.set(new WellDataListRecyclerAdapter());
    }

    private void getWellDataFromDatabase() {
        cleanupSubscribers();

        subscriber = new DataSubscriptionList();

        wellDataFacade.getAllWellDataQuery()
                .subscribe(subscriber)
                .on(AndroidScheduler.mainThread()).observer(this::updateRecyclerAdapter);

//        wellDataFacade.getAllWellDataQuery()
//                .subscribe(subscriber)
//                .on(AndroidScheduler.mainThread()).observer(new DataObserver<List<WellDataDM>>() {
//            @Override
//            public void onData(final List<WellDataDM> data) {
//                updateRecyclerAdapter(data);
//            }
//        });
    }

    private void updateRecyclerAdapter(final List<WellDataDM> wellDataList) {
        if (wellDataList == null || wellDataList.isEmpty()) {
            isListEmpty.set(true);
        } else {
            isListEmpty.set(false);

            recylcerViewAdapter.get().setData(wellDataList);
            recylcerViewAdapter.get().notifyDataSetChanged();;
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
