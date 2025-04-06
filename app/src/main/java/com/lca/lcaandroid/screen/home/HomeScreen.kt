package com.lca.lcaandroid.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.lca.lcaandroid.data.model.CulturalEvent
import com.lca.lcaandroid.viewmodel.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    HomeComponent(viewModel)
}

@Composable
fun CulturalEventComponent(viewModel: HomeViewModel) {
    // val uiState = viewModel.uiState.collectAsState().value with flow
    val events = viewModel.culturalEvents.collectAsLazyPagingItems()
    when (events.loadState.refresh) {
        is LoadState.Loading -> {
            CircularProgressIndicator() // 로딩 중일 때
        }

        is LoadState.Error -> {
            val e = events.loadState.refresh as LoadState.Error
            Text(text = "에러 발생: ${e.error.message}", color = Color.Red) // 에러 처리
        }

        is LoadState.NotLoading -> {
            EventList(events) // 데이터 로딩이 끝난 후 리스트 출력
        }
    }
}

@Composable
fun HomeComponent(viewModel: HomeViewModel) {
    val selectedTabIndex = viewModel.selectedTabIndex
    val tabTitles = listOf("문화행사", "야경장소") // 컴포넌트에서 관리

    Column {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { viewModel.onTabSelected(index) },
                    text = { Text(title) }
                )
            }
        }

        // 탭에 따른 컨텐츠 보여주기
        when (selectedTabIndex) {
            0 -> CulturalEventComponent(viewModel)
            1 -> Text(text = "야경장소")
        }
    }
}


@Composable
fun EventList(events: LazyPagingItems<CulturalEvent>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(8.dp),
        contentPadding = PaddingValues(8.dp),
    ) {
        items(events.itemCount) { index ->
            events[index]?.let { event ->
                HomeCardList(event)
            }
        }
        // 하단 로딩
        if (events.loadState.append is LoadState.Loading) {
            item {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeCardList(event: CulturalEvent) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier.height(100.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(event.imageUrl)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = "Sample Image",
                modifier = Modifier
                    .clip(RoundedCornerShape(3.dp)), // 이미지 자체를 둥글게
            )
        }
        Column(
            modifier = Modifier
        ) {
            Text(
                text = event.title,
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 3.dp),
                color = Color.Black,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
            )
            Text(
                text = event.category,
                modifier = Modifier
                    .padding(2.dp),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start,
            )
            Text(
                text = event.place,
                modifier = Modifier
                    .padding(2.dp),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start,
            )
        }
    }
}


