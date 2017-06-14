package com.benreily.zzzonked.repositories.impl;

import com.benreily.zzzonked.model.api.ApiService;
import com.benreily.zzzonked.model.pojo.DribbbleShotModel;
import com.benreily.zzzonked.repositories.NetworkRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public class NetworkShotsRepo implements NetworkRepository {

    public NetworkShotsRepo() {
    }

    @Override
    public Observable<List<DribbbleShotModel>> fetchShots(int page) {
        return ApiService.getDribbleShotsService().fetchShots(page);
    }

    @Override
    public Observable<List<DribbbleShotModel>> fetchShots(int page, int perPage) {
        return ApiService.getDribbleShotsService().fetchShots(page, perPage);
    }

}
