package com.lca.lcaandroid.data.model

import com.google.gson.annotations.SerializedName

/**
 *  문화행사 Data
 */

data class CulturalEventResponse(
    @SerializedName("culturalEventInfo") val culturalEventInfo: CulturalEventInfo
)

data class CulturalEventInfo(
    @SerializedName("list_total_count") val listTotalCount: Int,
    @SerializedName("RESULT") val result: ResultInfo,
    @SerializedName("row") val events: List<CulturalEvent>
)

data class ResultInfo(
    @SerializedName("CODE") val code: String,
    @SerializedName("MESSAGE") val message: String
)

data class CulturalEvent(
    @SerializedName("CODENAME") val category: String, // 행사 카테고리 (전시/미술, 교육/체험 등)
    @SerializedName("GUNAME") val district: String, // 지역구 (강남구, 종로구 등)
    @SerializedName("TITLE") val title: String, // 행사 제목
    @SerializedName("DATE") val date: String, // 행사 기간 (YYYY-MM-DD~YYYY-MM-DD)
    @SerializedName("PLACE") val place: String, // 장소
    @SerializedName("ORG_NAME") val organizer: String, // 주최 기관
    @SerializedName("USE_TRGT") val targetAudience: String, // 대상
    @SerializedName("USE_FEE") val fee: String?, // 참가비 (무료/유료 여부)
    @SerializedName("PLAYER") val player: String?, // 참여 아티스트 (없을 수도 있음)
    @SerializedName("PROGRAM") val program: String?, // 프로그램 정보 (없을 수도 있음)
    @SerializedName("ETC_DESC") val etcDescription: String?, // 기타 설명
    @SerializedName("ORG_LINK") val organizerLink: String?, // 주최 기관 링크
    @SerializedName("MAIN_IMG") val imageUrl: String, // 행사 대표 이미지
    @SerializedName("RGSTDATE") val registrationDate: String, // 등록일
    @SerializedName("TICKET") val ticketType: String, // 티켓 종류 (기관/시민)
    @SerializedName("STRTDATE") val startDate: String, // 시작 날짜
    @SerializedName("END_DATE") val endDate: String, // 종료 날짜
    @SerializedName("THEMECODE") val themeCode: String, // 테마 코드
    @SerializedName("LOT") val longitude: String, // 경도
    @SerializedName("LAT") val latitude: String, // 위도
    @SerializedName("IS_FREE") val isFree: String, // 무료 여부 (무료/유료)
    @SerializedName("HMPG_ADDR") val homepageUrl: String? // 홈페이지 URL
)
