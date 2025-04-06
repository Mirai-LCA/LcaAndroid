package com.lca.lcaandroid.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.lca.lcaandroid.data.model.CulturalEvent
import com.lca.lcaandroid.data.paging.EventPagingSource
import com.lca.lcaandroid.service.SeoulApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 *  서울시 문화 행사 Repo
 */

class CulturalEventInfoRepository @Inject constructor(
    private val seoulApiService: SeoulApiService // Hilt가 자동으로 주입
) {
    fun getSeoulCulturalEvent(pageStart: Int, pageEnd: Int): Flow<List<CulturalEvent>> = flow {
        val response = seoulApiService.getCulturalEventInfo(pageStart, pageEnd)

        if (response.isSuccessful) {
            val events = response.body()?.culturalEventInfo?.events
                ?: throw RuntimeException("데이터 오류가 발생하였습니다.")
            emit(events)
        } else {
            throw RuntimeException("서버 오류가 발생하였습니다.")
        }
    }

    fun getSeoulCulturalEvents(): Flow<PagingData<CulturalEvent>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { EventPagingSource(seoulApiService) }
        ).flow

    }
}