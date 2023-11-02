package com.syakirarif.aniiki.apiservice.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import com.syakirarif.aniiki.apiservice.BuildConfig
import com.syakirarif.aniiki.apiservice.api.AnimeEndpoints
import com.syakirarif.aniiki.apiservice.utils.NoConnectionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiServiceModule {
    @Provides
    @Singleton
    fun context(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        noConnectionInterceptor: NoConnectionInterceptor
    ): OkHttpClient {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val interceptor = Interceptor { chain ->
            val request: Request =
                chain.request().newBuilder().addHeader("Content-Type", "application/json")
                    .build()
            chain.proceed(request)
        }

        val chuckerInterceptor = ChuckerInterceptor.Builder(context)
            .collector(ChuckerCollector(context))
            .maxContentLength(250000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .build()

        return if (BuildConfig.DEBUG) {
            OkHttpClient.Builder()
                .addInterceptor(noConnectionInterceptor)
                .addInterceptor(interceptor)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(chuckerInterceptor)
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build()
        } else {
            OkHttpClient.Builder()
                .addInterceptor(noConnectionInterceptor)
                .addInterceptor(interceptor)
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build()
        }

    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
//            .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory.create())
            .baseUrl(BuildConfig.GRADLE_API_BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): AnimeEndpoints {
        return retrofit.create(AnimeEndpoints::class.java)
    }
}