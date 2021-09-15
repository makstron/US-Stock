package com.klim.us_stock.data.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.klim.us_stock.BuildConfig;
import com.klim.us_stock.data.retrofit.apis.SearchStockSymbolApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitProvider {
    public static final String BASE_URL = "https://api.polygon.io/";
    public static final String API_KEY = "pBTRT6y6rRicla11X0dQ4V3lRVg1O9cP";

    private static RetrofitProvider provider;

    private Retrofit retrofit;

    private SearchStockSymbolApi searchStockSymbolApi;

    public static RetrofitProvider get() {
        if (provider == null) {
            provider = new RetrofitProvider();
        }
        return provider;
    }

    public Retrofit getRetrofit() {
        if (retrofit == null) {

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build();

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .client(client)
                    .build();
        }
        return retrofit;
    }

    public SearchStockSymbolApi getSearchStockSymbolApi() {
        if (searchStockSymbolApi == null) {
            searchStockSymbolApi = getRetrofit().create(SearchStockSymbolApi.class);
        }
        return searchStockSymbolApi;
    }

}
