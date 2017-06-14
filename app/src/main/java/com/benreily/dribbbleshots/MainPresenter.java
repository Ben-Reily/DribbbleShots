package com.benreily.zzzonked;

import com.benreily.zzzonked.model.pojo.DribbbleShotModel;
import com.benreily.zzzonked.repositories.DatabaseRepository;
import com.benreily.zzzonked.repositories.NetworkRepository;
import com.benreily.zzzonked.view.MainView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


public class MainPresenter {


    private MainView view;

    private DatabaseRepository databaseRepo;
    private NetworkRepository networkRepo;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private NetworkShotsObserver netDisposable;
    private RealmShotsObserver dbDisposable;

    public MainPresenter(MainView view, DatabaseRepository databaseRepo, NetworkRepository networkRepo) {
        this.view = view;
        this.databaseRepo = databaseRepo;
        this.networkRepo = networkRepo;
    }

    public void loadShotsFromRealm() {
        dbDisposable = new RealmShotsObserver();
        compositeDisposable.add(databaseRepo.loadShotsFromDb()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(dbDisposable));
    }


    public void loadShotsFromNetwork(int page, int perPage) {
        netDisposable = new NetworkShotsObserver();
        compositeDisposable.add(networkRepo.fetchShots(page, perPage)
                .subscribeOn(Schedulers.io())
                .flatMapIterable(dribbbleShotModels -> dribbbleShotModels)
                .filter(dribbbleShotModel -> !dribbbleShotModel.isAnimated())
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(netDisposable));
    }



    private class NetworkShotsObserver extends DisposableSingleObserver<List<DribbbleShotModel>> {


        public NetworkShotsObserver() {
        }

        @Override
        public void onSuccess(@NonNull List<DribbbleShotModel> shotsList) {
            if (shotsList.isEmpty()) {
                view.displayNoShots();
            } else {
                saveShotsToRealm(shotsList);
                view.displayShots(shotsList);
            }
        }

        @Override
        public void onError(@NonNull Throwable e) {
            view.displayError();
            e.printStackTrace();
        }
    }

    private class RealmShotsObserver extends DisposableSingleObserver<List<DribbbleShotModel>> {
        @Override
        public void onSuccess(@NonNull List<DribbbleShotModel> shotsList) {
            if (shotsList.isEmpty()) {
                view.displayNoShots();
            } else {
                view.displayShots(shotsList);
            }
        }

        @Override
        public void onError(@NonNull Throwable e) {
            view.displayError();
            e.printStackTrace();
        }
    }


    private void saveShotsToRealm(List<DribbbleShotModel> shotsFromNetwork) {
        databaseRepo.saveShotsToDb(shotsFromNetwork);
    }

    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
