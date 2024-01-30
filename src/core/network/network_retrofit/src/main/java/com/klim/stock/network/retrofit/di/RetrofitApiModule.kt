package com.klim.stock.network.retrofit.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.klim.stock.network.Constants
import com.klim.stock.network.api.AuthApi
import com.klim.stock.network.api.ChartApi
import com.klim.stock.network.api.SearchApi
import com.klim.stock.network.api.StockSymbolApi
import com.klim.stock.network.api.SymbolDetailsApi
import com.klim.stock.network.models.ChartResponse
import com.klim.stock.network.models.details.SymbolDetailsResponse
import com.klim.stock.network.models.details.SymbolDetailsSummaryResponse
import com.klim.stock.network.models.details.SymbolRecommendationResponse
import com.klim.stock.network.retrofit.BuildConfig
import com.klim.stock.network.retrofit.auth.AuthApiProvider
import com.klim.stock.network.retrofit.interceptors.AuthInterceptor
import com.klim.stock.network.retrofit.interceptors.CookieJarI
import com.klim.stock.network.retrofit.interceptors.CrumbInterceptor
import com.klim.stock.network.retrofit.interceptors.HeaderInterceptor
import com.klim.stock.network.retrofit.interceptors.LoggingInterceptor
import com.klim.stock.network.retrofit.interceptors.StockCookieJar
import com.klim.stock.network.retrofit.typeAdapters.ChartResponseDeserializer
import com.klim.stock.network.retrofit.typeAdapters.SymbolDetailsDeserializer
import com.klim.stock.network.retrofit.typeAdapters.SymbolDetailsSummaryDeserializer
import com.klim.stock.network.retrofit.typeAdapters.SymbolRecommendationDeserializer
import com.klim.stock.storage.api.StorageKeys
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [RetrofitModule::class])
class RetrofitApiModule {


    @Provides
    @Singleton
    fun provideAuthApi(
        loggingInterceptor: LoggingInterceptor,
        headerInterceptor: HeaderInterceptor,
        cookieJar: CookieJarI,
        gsonConverterFactory: GsonConverterFactory,
    ): AuthApi {
        return AuthApiProvider(loggingInterceptor, headerInterceptor, cookieJar, gsonConverterFactory).getAuthApi()
    }

    @Provides
    @Singleton
    fun provideSearchApi(retrofit: Retrofit): SearchApi {
        return retrofit.create<SearchApi>(SearchApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSymbolDetailsApi(retrofit: Retrofit): SymbolDetailsApi {
        return retrofit.create<SymbolDetailsApi>(SymbolDetailsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideStockSymbolApi(retrofit: Retrofit): StockSymbolApi {
        return retrofit.create<StockSymbolApi>(StockSymbolApi::class.java)
    }

    @Provides
    @Singleton
    fun provideChartApi(retrofit: Retrofit): ChartApi {
        return retrofit.create<ChartApi>(ChartApi::class.java)
    }

}

@Module
class RetrofitModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .registerTypeAdapter(SymbolDetailsSummaryResponse::class.java, SymbolDetailsSummaryDeserializer())
            .registerTypeAdapter(SymbolDetailsResponse::class.java, SymbolDetailsDeserializer())
            .registerTypeAdapter(ChartResponse::class.java, ChartResponseDeserializer())
            .registerTypeAdapter(SymbolRecommendationResponse::class.java, SymbolRecommendationDeserializer())
            .create()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    /////

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): LoggingInterceptor {
        val interceptor = LoggingInterceptor()
        interceptor.setLevel(if (BuildConfig.DEBUG) LoggingInterceptor.Level.BODY else LoggingInterceptor.Level.NONE)//todo modules where take BuildConfig.DEBUG
        return interceptor
    }

    @Provides
    @Singleton
    fun provideHeaderInterceptor(storageKeys: StorageKeys): HeaderInterceptor {
        return HeaderInterceptor(storageKeys)
    }

    @Provides
    @Singleton
    fun provideCrumbInterceptor(storageKeys: StorageKeys): CrumbInterceptor {
        return CrumbInterceptor(storageKeys)
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(storageKeys: StorageKeys, authApi: AuthApi): AuthInterceptor {
        return AuthInterceptor(storageKeys, authApi)
    }

    /////

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: LoggingInterceptor,
        authInterceptor: AuthInterceptor,
        headerInterceptor: HeaderInterceptor,
        crumbInterceptor: CrumbInterceptor,
        cookieJar: CookieJarI,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .cookieJar(cookieJar)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .addInterceptor(headerInterceptor)
            .addInterceptor(crumbInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        converterFactory: GsonConverterFactory,
        httpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.SERVER_URL)
            .addConverterFactory(converterFactory)
            .client(httpClient)
            .build()
    }


    @Provides
    @Singleton
    fun provideCookieJar(storageKeys: StorageKeys): CookieJarI {
        return StockCookieJar(storageKeys)
    }


}