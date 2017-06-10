package com.benreily.zzzonked.repositories.impl;

import com.benreily.zzzonked.model.pojo.DribbbleShot;
import com.benreily.zzzonked.repositories.DatabaseRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class DatabaseShotsRepo implements DatabaseRepository {

    public DatabaseShotsRepo() {

    }

    @Override
    public Observable<List<DribbbleShot>> loadShotsFromDb() throws EmptyDbException {
        Observable<List<DribbbleShot>> observable = null;

        if (observable == null) {
            throw new EmptyDbException();
        }

        return Observable.just(new ArrayList<DribbbleShot>());
    }

    @Override
    public void saveShotsToDb() {

    }
}
