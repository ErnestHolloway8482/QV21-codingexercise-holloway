package qv21.codingexercise.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import qv21.codingexercise.application.QV21Application;
import qv21.codingexercise.databinding.WellDataListItemBinding;
import qv21.codingexercise.facades.WellDataFacade;
import qv21.codingexercise.managers.NavigationManager;
import qv21.codingexercise.models.databasemodels.WellDataDM;
import qv21.codingexercise.models.domainmodels.WellDataItemDOM;
import qv21.codingexercise.models.viewmodels.WellDataListItemVM;
import qv21.codingexercise.viewholders.WellDataItemViewHolder;

/**
 * A {@link RecyclerView.Adapter} for the list of Well Data Items.
 */
public class WellDataListRecyclerAdapter extends RecyclerView.Adapter<WellDataItemViewHolder> {
    private List<WellDataDM> wellDataList;

    @Inject
    WellDataFacade wellDataFacade;

    @Inject
    NavigationManager navigationManager;

    public WellDataListRecyclerAdapter() {
        QV21Application.getAppComponent().inject(this);
    }

    /**
     * Sets the list of well data items to display for this recylcerViewAdapter and assigns the appropriate data change listener to it.
     *
     * @param items is the {@link List} cached in the database to support endless scrolling.
     */
    public void setData(final List<WellDataDM> items) {
        wellDataList = items;

        notifyDataSetChanged();
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
        WellDataListItemBinding binding = WellDataListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new WellDataItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final WellDataItemViewHolder viewHolder, final int position) {
        if (wellDataList == null || wellDataList.isEmpty()) {
            return;
        }

        convert(viewHolder, position);
    }

    @Override
    public int getItemCount() {
        return wellDataList == null ? 0 : wellDataList.size();
    }

    /**
     * This method will read the item from the database and convert it to a view model and then
     * bindViewModel the result to a view.
     *
     * @param viewHolder is the view contents to bindViewModel the data to.
     * @param position   is the current scroll position which represents the data corresponding data index to grab from the database.
     */
    private void convert(@NonNull final WellDataItemViewHolder viewHolder, final int position) {
        WellDataListItemVM vm = new WellDataListItemVM(wellDataFacade, navigationManager);
        vm.wellData.set(wellDataList.get(position));
        vm.wellDataDom.set(WellDataItemDOM.create(wellDataList.get(position)));
        viewHolder.bindViewModel(vm);
    }
}
