package qv21.codingexercise.models.viewmodels;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.v7.widget.LinearLayoutManager;

import java.util.List;

import io.objectbox.android.AndroidScheduler;
import io.objectbox.reactive.DataSubscriptionList;
import qv21.codingexercise.adapters.WellDataListRecyclerAdapter;
import qv21.codingexercise.facades.WellDataFacade;
import qv21.codingexercise.managers.MainActivityProviderManager;
import qv21.codingexercise.managers.NavigationManager;
import qv21.codingexercise.managers.ScreenManager;
import qv21.codingexercise.models.databasemodels.WellDataDM;

/**
 * @link android.arch.lifecycle.ViewModel} that serves as the controller logic for the {@link qv21.codingexercise.views.WellDataListScreen}
 */
public class WellDataListVM extends BaseVM {
    private static final String SCREEN_NAME = "Well Entries";

    private final WellDataFacade wellDataFacade;
    private final NavigationManager navigationManager;
    private final MainActivityProviderManager mainActivityProviderManager;
    private final ScreenManager screenManager;

    private DataSubscriptionList subscriber = new DataSubscriptionList();

    public final ObservableField<WellDataListRecyclerAdapter> recyclerViewAdapter = new ObservableField<>();

    public final ObservableField<LinearLayoutManager> linearLayoutManager = new ObservableField<>();

    public ObservableBoolean isListEmpty = new ObservableBoolean();

    public WellDataListVM(final WellDataFacade wellDataFacade,
                          final NavigationManager navigationManager,
                          final MainActivityProviderManager mainActivityProviderManager,
                          final ScreenManager screenManager) {
        this.wellDataFacade = wellDataFacade;
        this.navigationManager = navigationManager;
        this.mainActivityProviderManager = mainActivityProviderManager;
        this.screenManager = screenManager;

        setupToolBar();
        setupRecyclerViewAdapter();
        getWellDataFromDatabase();
    }

    @Override
    public void setupToolBar() {
        mainActivityProviderManager.provideMainActivity().getViewModel().displayToolBar(false, SCREEN_NAME);
    }

    private void setupRecyclerViewAdapter() {
        linearLayoutManager.set(setupLinearLayoutManager());
        recyclerViewAdapter.set(setupWellDataListRecyclerAdapter());
    }

    private void getWellDataFromDatabase() {
        wellDataFacade.getAllWellDataQuery()
                .subscribe(subscriber)
                .on(AndroidScheduler.mainThread()).observer(this::updateRecyclerAdapter);
    }

    private void updateRecyclerAdapter(final List<WellDataDM> wellDataList) {
        if (wellDataList == null || wellDataList.isEmpty()) {
            isListEmpty.set(true);
        } else {
            isListEmpty.set(false);

            recyclerViewAdapter.get().setData(wellDataList);
        }
    }

    private void cleanupSubscribers() {
        if (subscriber != null) {
            if (!subscriber.isCanceled()) {
                subscriber.cancel();
                subscriber = null;
            }
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mainActivityProviderManager.provideMainActivity().getViewModel().dismissToolbar();
        cleanupSubscribers();
    }

    private LinearLayoutManager setupLinearLayoutManager() {
        return new LinearLayoutManager(mainActivityProviderManager.provideMainActivity());
    }

    private WellDataListRecyclerAdapter setupWellDataListRecyclerAdapter() {
        return new WellDataListRecyclerAdapter(wellDataFacade,
                navigationManager,
                mainActivityProviderManager,
                screenManager);
    }
}
