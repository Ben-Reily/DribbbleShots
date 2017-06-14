package com.benreily.zzzonked.view;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.benreily.zzzonked.MainPresenter;
import com.benreily.zzzonked.R;
import com.benreily.zzzonked.model.adapter.ShotListItem;
import com.benreily.zzzonked.model.pojo.DribbbleShotModel;
import com.benreily.zzzonked.repositories.impl.DatabaseShotsRepo;
import com.benreily.zzzonked.repositories.impl.NetworkShotsRepo;
import com.mikepenz.fastadapter.IItem;
import com.mikepenz.fastadapter.adapters.FooterAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter_extensions.items.ProgressItem;
import com.mikepenz.fastadapter_extensions.scroll.EndlessRecyclerOnScrollListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.benreily.zzzonked.utils.InternetConnection.isNetworkAvailable;

public class MainActivity extends AppCompatActivity implements MainView {

    private Unbinder unbinder;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.rv_shots_list)
    RecyclerView rvShotsList;

    @BindView(R.id.iv_empty_img)
    ImageView ivEmpty;

    @BindView(R.id.tv_empty_msg)
    TextView tvEmpty;

    private MainPresenter presenter;

    private Toast toast;

    private FastItemAdapter<IItem> adapter;
    private FooterAdapter<ProgressItem> footerAdapter;
    private ScrollListener scrollListener;

    private int startingPage = 1;
    private int perPage = 30;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        setupToolbar(toolbar);
        setupRecyclerView(rvShotsList);

        presenter = new MainPresenter(this, new DatabaseShotsRepo(), new NetworkShotsRepo());
        presenter.loadShotsFromRealm();

        setupSwipeRefreshLayout(swipeRefreshLayout);
    }


    private void setupToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
        }
        toolbar.setTitle(R.string.app_name);
    }

    private void setupSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (isNetworkAvailable(getApplicationContext())) {
                swipeRefreshLayout.setRefreshing(true);
                presenter.loadShotsFromNetwork(startingPage, perPage);
            } else {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void setupRecyclerView(RecyclerView rvShotsList) {
        adapter = new FastItemAdapter<>();
        footerAdapter = new FooterAdapter<>();
        rvShotsList.setAdapter(footerAdapter.wrap(adapter));
        scrollListener = new ScrollListener(footerAdapter);
        rvShotsList.addOnScrollListener(scrollListener);
        rvShotsList.setLayoutManager(new LinearLayoutManager(this));
        rvShotsList.setItemAnimator(new DefaultItemAnimator());
    }



    private class ScrollListener extends EndlessRecyclerOnScrollListener {

        ScrollListener(FooterAdapter adapter) {
            super(adapter);
        }

        @Override
        public void onLoadMore(int currentPage) {
            int itemsLimit = 50;
            int shotsLeft = itemsLimit - adapter.getAdapterItemCount();
            boolean shouldLoadMore = !(adapter.getAdapterItemCount() == 0 || shotsLeft == 0);

            if (isNetworkAvailable(getApplicationContext()) && shouldLoadMore) {
                rvShotsList.post(() -> {
                    footerAdapter.clear();
                    footerAdapter.add(new ProgressItem().withEnabled(false));
                });
                if (shotsLeft <= perPage) {
                    presenter.loadShotsFromNetwork(currentPage, shotsLeft);
                } else if (shotsLeft > perPage) {
                    presenter.loadShotsFromNetwork(currentPage, perPage);
                }
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_exit:
                exit();
                break;
        }
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    @Override
    public void displayShots(List<DribbbleShotModel> shotsList) {
        if (swipeRefreshLayout.isRefreshing()) {
            adapter.clear();
            reattachScrollListener();
            swipeRefreshLayout.setRefreshing(false);
        }
        footerAdapter.clear();

        for (DribbbleShotModel shotModel : shotsList) {
            adapter.add(new ShotListItem(shotModel));
        }
        hideEmptyMessage();
    }

    @Override
    public void displaySuccessMessage() {
        showToast("So success!");
    }


    private void hideEmptyMessage() {
        ivEmpty.setVisibility(View.GONE);
        tvEmpty.setVisibility(View.GONE);
    }

    @Override
    public void displayNoShots() {
        swipeRefreshLayout.setRefreshing(false);
        footerAdapter.clear();

        if (adapter.getAdapterItemCount() == 0) {
            showEmptyMessage();
        }
    }

    private void showEmptyMessage() {
        ivEmpty.setVisibility(View.VISIBLE);
        tvEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayError() {
        showToast("Much unexpected!");
        swipeRefreshLayout.setRefreshing(false);
        footerAdapter.clear();
    }

    private void showToast(String message) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void removeAllPreviousShots() {
        adapter.clear();
    }

    @Override
    public void reattachScrollListener() {
        rvShotsList.removeOnScrollListener(scrollListener);
        rvShotsList.clearOnScrollListeners();
        scrollListener = new ScrollListener(footerAdapter);
        rvShotsList.addOnScrollListener(scrollListener);
    }

    private void exit() {
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
