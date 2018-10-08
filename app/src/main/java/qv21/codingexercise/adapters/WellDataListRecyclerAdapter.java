package qv21.codingexercise.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import javax.inject.Inject;

import io.objectbox.query.LazyList;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import qv21.codingexercise.databinding.WellDataListItemScreenBinding;
import qv21.codingexercise.facades.WellDataFacade;
import qv21.codingexercise.managers.NavigationManager;
import qv21.codingexercise.models.databasemodels.WellDataDM;
import qv21.codingexercise.models.viewmodels.WellDataListItemVM;
import qv21.codingexercise.utilities.LoggerUtils;
import qv21.codingexercise.viewholders.WellDataItemViewHolder;

/**
 * A {@link RecyclerView.Adapter} for the list of Articles.
 */
public class WellDataListRecyclerAdapter extends RecyclerView.Adapter<WellDataItemViewHolder> {
    private LazyList<WellDataDM> wellDataList;

    @Inject
    WellDataFacade wellDataFacade;

    @Inject
    NavigationManager navigationManager;

    /**
     * Sets the list of well data items to display for this adapter and assigns the appropriate data change listener to it.
     * This is only done initially since LazyList objects are live and will auto-update
     *
     * @param items is the {@link LazyList} cached in the database to support endless scrolling.
     */
    public void setData(final LazyList<WellDataDM> items) {
        //Only alow the RealmResults List to be set once and then add a corresponding listener to it.
        //Since Lazy objects are live objects there is no need to set a new reference for it.
        if (wellDataList == null) {
            wellDataList = items;

            //TODO add code to add a listener to the data set.
        }
    }

    @Override
    public int getItemViewType(final int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(final int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public WellDataItemViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        WellDataListItemScreenBinding binding = WellDataListItemScreenBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new WellDataItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final WellDataItemViewHolder viewHolder, final int position) {
        if (wellDataList == null || wellDataList.isEmpty()) {
            return;
        }

        Single.fromCallable(()->{
            convert(viewHolder, position);
            return null;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(__->{},throwable -> LoggerUtils.logError(throwable.getMessage()));

        convert(viewHolder, position);
    }

    @Override
    public int getItemCount() {
        return wellDataList == null ? 0 : wellDataList.size();
    }

    /**
     * This method will read the item from the database and convert it to a view model and then
     * bind the result to a view.
     *
     * @param viewHolder is the view contents to bind the data to.
     * @param position   is the current scroll position which represents the data corresponding data index to grab from the database.
     */
    private void convert(@NonNull final WellDataItemViewHolder viewHolder, final int position) {
        WellDataListItemVM vm = new WellDataListItemVM(wellDataFacade, navigationManager);
        vm.wellData.set(wellDataList.get(position));
        viewHolder.bind(vm);
    }
}
