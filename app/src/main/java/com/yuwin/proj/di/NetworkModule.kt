package com.yuwin.proj.di

import com.yuwin.proj.data.remote.PoisonPlantApi
import com.yuwin.proj.data.repository.PoisonPlantPlantRepositoryImpl
import com.yuwin.proj.domain.repository.PoisonPlantRepository
import com.yuwin.proj.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .connectTimeout(300, TimeUnit.SECONDS)
            .readTimeout(300, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun providePoisonPlantApi(retrofit: Retrofit): PoisonPlantApi {
        return retrofit.create(PoisonPlantApi::class.java)
    }

    @Provides
    @Singleton
    fun providePoisonPlantRepository(api: PoisonPlantApi): PoisonPlantRepository {
        return PoisonPlantPlantRepositoryImpl(api)
    }
}