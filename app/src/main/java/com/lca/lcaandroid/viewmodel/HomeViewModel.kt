package com.lca.lcaandroid.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.lca.lcaandroid.data.model.CulturalEvent
import com.lca.lcaandroid.data.repository.CulturalEventInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

// UiState - 로딩 체크
sealed interface UiState {
    object Loading : UiState

    data class Success(
        val events: List<CulturalEvent>
    ) : UiState

    data class Error(
        val message: String
    ) : UiState
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val culturalEventInfoRepository: CulturalEventInfoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    var selectedTabIndex by mutableStateOf(0)
        private set

    fun onTabSelected(index: Int) {
        selectedTabIndex = index
    }

    // flow -> paging으로 변환
//    init {
//        fetchArticleWithFlow()
//    }

//    private fun fetchArticleWithFlow() {
//        culturalEventInfoRepository.getSeoulCulturalEvent(1, 10)
//            .onStart {
//                _uiState.value = UiState.Loading
//            }
//            .catch { e ->
//                _uiState.value = UiState.Error(e.message ?: "요청에 실패했어요.")
//            }
//            .onEach { events ->
//                _uiState.value = UiState.Success(events)
//            }
//            .launchIn(viewModelScope)
//    }

    val culturalEvents: Flow<PagingData<CulturalEvent>> =
        culturalEventInfoRepository.getSeoulCulturalEvents().cachedIn(viewModelScope)

}
