package com.lca.lcaandroid.service

import com.lca.lcaandroid.data.model.CulturalEventResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SeoulApiService {
    // 문화행사 정보
    @GET("culturalEventInfo/{pageStart}/{pageEnd}/")
    suspend fun getCulturalEventInfo(
        @Path("pageStart") pageStart: Int,
        @Path("pageEnd") pageEnd: Int
    ): Response<CulturalEventResponse>


}