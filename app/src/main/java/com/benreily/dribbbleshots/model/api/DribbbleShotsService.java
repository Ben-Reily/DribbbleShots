package com.benreily.zzzonked.model.api;

import com.benreily.zzzonked.model.pojo.DribbbleShotModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DribbbleShotsService {

    @GET("shots")
    Observable<List<DribbbleShotModel>> fetchShots(@Query("page") int page);

    @GET("shots")
    Observable<List<DribbbleShotModel>> fetchShots(@Query("page") int page, @Query("per_page") int perPage);

}
