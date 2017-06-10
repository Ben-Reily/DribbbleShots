package com.benreily.zzzonked.repositories;

import com.benreily.zzzonked.model.pojo.DribbbleShot;

import java.util.List;

import io.reactivex.Observable;

public interface NetworkRepository {

    Observable<List<DribbbleShot>> fetchShots(int page);

    Observable<List<DribbbleShot>> fetchShots(int page, int perPage, String list, String sort);

    Observable<DribbbleShot> getShot(long shotId);
}
