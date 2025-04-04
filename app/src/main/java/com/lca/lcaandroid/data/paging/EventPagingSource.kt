package com.lca.lcaandroid.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lca.lcaandroid.data.model.CulturalEvent
import com.lca.lcaandroid.service.SeoulApiService
import javax.inject.Inject


/**
 *   페이징 클래스
 *
 */
class EventPagingSource @Inject constructor(
    private val seoulApiService: SeoulApiService
) :
    PagingSource<Int, CulturalEvent>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CulturalEvent> {
        return try {
            val page = params.key ?: 1
            val start = (page - 1) * 10 + 1
            val end = start + 9
            val results =
                seoulApiService.getCulturalEventInfo(start, end).body()?.culturalEventInfo?.events!!
            val nextPage = if (results.size == 10) page + 1 else null
            Log.d("EventPagingSource", "page : $page")
            LoadResult.Page(data = results, nextKey = nextPage, prevKey = null)
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CulturalEvent>): Int? {
        return state.anchorPosition?.let { state.closestPageToPosition(it)?.prevKey }
    }
}