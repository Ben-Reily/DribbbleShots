package com.benreily.zzzonked.repositories;

import com.benreily.zzzonked.model.pojo.DribbbleShotModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface NetworkRepository {

    Observable<List<DribbbleShotModel>> fetchShots(int page);

    Observable<List<DribbbleShotModel>> fetchShots(int page, int perPage);

}
