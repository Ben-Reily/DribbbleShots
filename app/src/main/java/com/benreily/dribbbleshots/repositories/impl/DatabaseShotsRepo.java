package com.benreily.zzzonked.repositories.impl;

import android.util.Log;

import com.benreily.zzzonked.model.pojo.DribbbleShotModel;
import com.benreily.zzzonked.repositories.DatabaseRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.realm.Realm;
import io.realm.RealmResults;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class DatabaseShotsRepo implements DatabaseRepository {


    private Realm realm;

    public DatabaseShotsRepo() {
        realm = Realm.getDefaultInstance();
    }

    @Override
    public Single<List<DribbbleShotModel>> loadShotsFromDb() {

        RealmResults<DribbbleShotModel> result = realm.where(DribbbleShotModel.class).findAll();

        List<DribbbleShotModel> shotsFromRealm = new ArrayList<>();
        shotsFromRealm.addAll(result);

        return Single.just(shotsFromRealm);
    }

    @Override
    public void saveShotsToDb(List<DribbbleShotModel> shotModels) {
        realm.executeTransaction(realm1 -> {
            for (DribbbleShotModel model : shotModels) {
                realm1.copyToRealmOrUpdate(model);
            }
        });
    }
}
