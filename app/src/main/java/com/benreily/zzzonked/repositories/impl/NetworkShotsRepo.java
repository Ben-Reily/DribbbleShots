package com.benreily.zzzonked.repositories.impl;

import com.benreily.zzzonked.model.api.ApiService;
import com.benreily.zzzonked.model.pojo.DribbbleShot;
import com.benreily.zzzonked.repositories.NetworkRepository;

import java.util.List;

import io.reactivex.Observable;

import static com.benreily.zzzonked.model.api.ApiService.CLIENT_ACCESS_TOKEN;

public class NetworkShotsRepo implements NetworkRepository {

    public NetworkShotsRepo() {
    }

    @Override
    public Observable<List<DribbbleShot>> fetchShots(int page) {
        return ApiService.getDribbleShotsService(CLIENT_ACCESS_TOKEN).fetchShots(page);
    }

    @Override
    public Observable<List<DribbbleShot>> fetchShots(int page, int perPage, String list, String sort) {
        return ApiService.getDribbleShotsService(CLIENT_ACCESS_TOKEN).fetchShots(page, perPage, list, sort);
    }

    @Override
    public Observable<DribbbleShot> getShot(long shotId) {
        return ApiService.getDribbleShotsService(CLIENT_ACCESS_TOKEN).getShot(shotId);
    }
}
