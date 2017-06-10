package com.benreily.zzzonked.model.api;

import com.benreily.zzzonked.model.pojo.DribbbleShot;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DribbbleShotsService {

    @GET("shots")
    Observable<List<DribbbleShot>> fetchShots(@Query("page") int page);

    @GET("shots")
    Observable<List<DribbbleShot>> fetchShots(@Query("page") int page, @Query("per_page") int perPage);

    @GET("shots")
    Observable<List<DribbbleShot>> fetchShots(@Query("page") int page, @Query("per_page") int perPage, @Query("list") String list, @Query("sort") String sort);

    @GET("shots/{id}")
    Observable<DribbbleShot> getShot(@Path("id") long shotId);
}
