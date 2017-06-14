package com.benreily.zzzonked.repositories;

import com.benreily.zzzonked.model.pojo.DribbbleShotModel;

import java.util.List;

import io.reactivex.Single;

public interface DatabaseRepository {

    Single<List<DribbbleShotModel>> loadShotsFromDb();

    void saveShotsToDb(List<DribbbleShotModel> shotsFromNetwork);

}
