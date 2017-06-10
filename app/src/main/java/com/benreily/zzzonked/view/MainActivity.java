package com.benreily.zzzonked.view;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.benreily.zzzonked.MainPresenter;
import com.benreily.zzzonked.R;
import com.benreily.zzzonked.model.pojo.DribbbleShot;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;
import io.reactivex.Observable;

public class MainActivity extends BaseActivity implements MainView {

    private static final String TAG = "MainActivity";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.rv_shots_list)
    RecyclerView rvShotsList;

    private MainPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this, null, null);

        setupToolbar(toolbar);
    }

    private void setupToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }
        toolbar.setTitle(R.string.app_name);

        Logger.i("Toolbar set up");
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
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void displayShots(Observable<List<DribbbleShot>> shotsList) {

    }

    @Override
    public void displayNoShots() {

    }
}
