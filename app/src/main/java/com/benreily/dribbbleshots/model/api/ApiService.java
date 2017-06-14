package com.benreily.zzzonked.model.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    private final static String DRIBBBLE_URL = "https://api.dribbble.com/v1/";
    private final static String CLIENT_ACCESS_TOKEN = "82df85e3556a29d979dc8531d293944cb2f2ee64163a7bfd17138b4d5655b1cc";

    private static final boolean LOGGING_ENABLED = true;

    private ApiService() {

    }

    public static DribbbleShotsService getDribbleShotsService() {
        return getShotsService(DRIBBBLE_URL, CLIENT_ACCESS_TOKEN);
    }

    private static DribbbleShotsService getShotsService(String url, String accessToken) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

        if (LOGGING_ENABLED) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.addInterceptor(interceptor);
        }

        Interceptor authInterceptor = chain -> {
            Request original = chain.request();
            Request authRequest = original.newBuilder().addHeader("Authorization", "Bearer " + accessToken).build();
            return chain.proceed(authRequest);
        };

        clientBuilder.addInterceptor(authInterceptor);

        Retrofit.Builder serviceBuilder = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(clientBuilder.build());

        return serviceBuilder.build().create(DribbbleShotsService.class);
    }
}
