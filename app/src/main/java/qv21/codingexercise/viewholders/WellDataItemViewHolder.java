package qv21.codingexercise.viewholders;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import com.android.databinding.library.baseAdapters.BR;

import qv21.codingexercise.models.viewmodels.WellDataListItemVM;

/**
 * This is a {@link RecyclerView.ViewHolder} for each of the well data items displayed in the list.
 */
public class WellDataItemViewHolder extends RecyclerView.ViewHolder {
    public ViewDataBinding binding;

    public WellDataItemViewHolder(final ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(WellDataListItemVM vm) {
        binding.setVariable(BR.vm, vm);
        binding.executePendingBindings();
    }
}
