package qv21.codingexercise.models.viewmodels;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.v7.widget.RecyclerView;

import io.objectbox.query.LazyList;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import qv21.codingexercise.facades.WellDataFacade;
import qv21.codingexercise.models.database.WellData;
import qv21.codingexercise.utilities.LoggerUtils;

public class WellDataListVM {
    private final WellDataFacade wellDataFacade;

    private Disposable subscriber;

    public ObservableField<RecyclerView.Adapter> adapter = new ObservableField<>();
    public ObservableBoolean isListEmpty = new ObservableBoolean();

    public WellDataListVM(final WellDataFacade wellDataFacade) {
        this.wellDataFacade = wellDataFacade;

        getWellDataFromDatabase();
    }

    private void getWellDataFromDatabase() {
        cleanupSubscribers();

        subscriber = Single.fromCallable(() -> wellDataFacade.getAllWellDataItems())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateRecyclerAdapter, throwable -> LoggerUtils.log(throwable.getMessage()));
    }

    private void updateRecyclerAdapter(final LazyList<WellData> wellDataList) {
        if (wellDataList == null || wellDataList.isEmpty()) {
            isListEmpty.set(true);
        } else {
            isListEmpty.set(false);

            //TOOD add code here to setup up the RecyclerAdapter.
        }

        cleanupSubscribers();
    }

    private void cleanupSubscribers() {
        if (subscriber != null) {
            if (!subscriber.isDisposed()) {
                subscriber.dispose();
                subscriber = null;
            }
        }
    }
}
