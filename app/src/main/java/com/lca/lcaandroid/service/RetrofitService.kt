package com.lca.lcaandroid.service

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitService {

    private const val BASE_URL =
        "http://openapi.seoul.go.kr:8088/6c5245716b696e67383054737a6f55/json/" // 실제 API 주소로 변경

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // JSON 변환기 추가
            .build()
    }

    @Provides
    @Singleton
    fun provideSeoulApi(retrofit: Retrofit): SeoulApiService {
        return retrofit.create(SeoulApiService::class.java)
    }
}