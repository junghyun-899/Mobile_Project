Mobile Project 여행 기록 관리 애플리케이션

-개발환경
- Android Studio
- Kotlin
- SQLite
- Google Maps API

1. 여행 기록 관리
- 여행지 이름
- 날짜 선택
- 메모 작성
- 평점 등록
- 사진 첨부

2. SQLite CRUD
- 여행 기록 추가(Create)
- 여행 기록 조회(Read)
- 여행 기록 수정(Update)
- 여행 기록 삭제(Delete)

3. RecyclerView
- 저장된 여행 기록 목록 출력
- 이미지 미리보기 제공

4. Fragment
- HomeFragment
- MapFragment

5. 옵션
- 여행 통계 확인
- 전체 기록 삭제

6. 컨텍스트 메뉴
- 길게 눌러 여행지별 저장된 메모 확인

7. 지도 기능
- Google Maps API 활용
- 여행지 마커 표시


-데이터베이스(SQLite)
- 여행지
- 날짜
- 메모
- 이미지 경로
- 위도
- 경도
- 평점

-프로젝트 구조
app
├─ manifests
│   └─ AndroidManifest.xml
│
├─ kotlin+java
│   └─ com.example.mobile_project
│       ├─ adapter
│       │   └─ TravelAdapter.kt
│       ├─ db
│       │   └─ DBHelper.kt
│       ├─ fragment
│       │   ├─ HomeFragment.kt
│       │   └─ MapFragment.kt
│       ├─ model
│       │   └─ TravelRecord.kt
│       ├─ AddEditActivity.kt
│       └─ MainActivity.kt
│
├─ res
│   ├─ layout
│   │   ├─ activity_add_edit.xml
│   │   ├─ activity_main.xml
│   │   ├─ fragment_home.xml
│   │   ├─ fragment_map.xml
│   │   └─ item_travel.xml
│   │
│   ├─ menu
│   │   ├─ bottom_menu.xml
│   │   └─ option_menu.xml
│
└─ Gradle Scripts
    ├─ build.gradle.kts (Project)
    └─ build.gradle.kts (Module : app)
