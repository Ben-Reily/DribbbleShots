package com.benreily.zzzonked.model.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    public static String DRIBBBLE_URL = "https://api.dribbble.com/v1/";
    public static final String CLIENT_ACCESS_TOKEN = "82df85e3556a29d979dc8531d293944cb2f2ee64163a7bfd17138b4d5655b1cc";
    public static final String CLIENT_SECRET = "eed639f8a0e672be832c1253c72670d8bc9f107068c49e2291701e037a8a64d0";
    public static final String CLIENT_ID = "9dd0a67ea97d8846f85635e8b5ce1a990c3e7412c0db15d56ba7ca8d8076be48";

    private static final boolean LOGGING_ENABLED = true;

    private ApiService() {

    }

    public static DribbbleShotsService getDribbleShotsService(final String accessToken) {
        return getShotsService(DRIBBBLE_URL, CLIENT_ACCESS_TOKEN);
    }

    private static DribbbleShotsService getShotsService(String url, String accessToken) {
        OkHttpClient httpClient = new OkHttpClient();

        if (LOGGING_ENABLED) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.interceptors().add(interceptor);
        }

        Interceptor authInterceptor = chain -> {
            Request request = chain.request();
            Request authRequest = request.newBuilder().addHeader("Authorization", "Bearer " + accessToken).build();
            return chain.proceed(authRequest);
        };
        httpClient.interceptors().add(authInterceptor);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        builder.client(httpClient);

        DribbbleShotsService shotsService = builder.build().create(DribbbleShotsService.class);
        return shotsService;
    }
}
