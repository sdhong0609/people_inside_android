# 💡 Topic
- **MBTI 기반 영화 및 드라마 콘텐츠 추천 및 평가 서비스**
- **팀 프로젝트 플랫폼 [비사이드](https://bside.best/)에서 팀원을 구성하여 프로젝트 진행**

<br>

# 📝 Summary
기존 OTT 서비스에서 다양한 콘텐츠를 추천해주지만, 막상 시청하려고 하면 너무 많은 선택지 사이에서 어떤 콘텐츠를 봐야할지 고르기가 어렵습니다. 그리고 어떤 기준을 통해 콘텐츠를 추천해주는지 사용자들이 알 수 없어서, 명확한 기준에 따른 콘텐츠 추천 니즈가 있었습니다. 따라서 사용자들이 명확하게 알 수 있는 MBTI라는 기준에 따라서 콘텐츠를 추천해주는 서비스를 개발하였습니다. 또한 자유롭게 의견을 작성할 수 있는 커뮤니티 기능도 구축하였습니다.

<br>

# ⭐️ Key Function
- **MBTI에 따른 콘텐츠 추천**
    - 사용자의 MBTI에 따라 영화 및 드라마 콘텐츠 추천
    - 콘텐츠 검색 및 콘텐츠에 대한 설명이 담긴 상세 페이지를 볼 수 있음
- **콘텐츠 검색**
    - 콘텐츠를 검색하고 상세 페이지를 볼 수 있음
    - 내가 둘러본 콘텐츠 목록과 인기 급상승 콘텐츠 목록을 볼 수 있음
- **콘텐츠 별점 및 감상 작성**
    - 콘텐츠 상세페이지에서 콘텐츠 북마크, 별점 및 리뷰를 작성/수정
    - 상세페이지의 다른 사용자들의 리뷰에 좋아요 및 신고
- **마이페이지 및 설정**
    - 마이페이지에서 사용자가 북마크한 콘텐츠와 별점 및 리뷰를 작성한 콘텐츠를 볼 수 있음
    - 북마크 해제 가능, 별점 및 리뷰 수정
- **커뮤니티**
    - 글을 작성하여 다른 사용자들과 의견 공유
    - 다른 사용자들의 글에 댓글 남기기
    - 키워드를 검색하여 사용자들의 글을 필터링
- **회원가입 및** **로그인**
    - 카카오 로그인 기능 구현
    - 카카오 로그인 성공 시 회원가입 후, 자신이 재밌게 본 콘텐츠를 선택하는 화면 구현
    - 회원가입을 하지 않고 MBTI만 선택하여 둘러보는 것도 가능하지만 회원들만 할 수 있는 기능은 제한

 <br>

# 🛠 Tech Stack
`Kotlin`, `RecyclerView`, `DataBinding`, `AAC ViewModel`, `LiveData`, `Retrofit`, `Coroutines`, `Hilt`, `Glide`, `OkHttp`, `Timber`, `Navigation Component`, `Firebase Analytics`, `CustomDialog`, `Flexbox`, `Kakao Login Api`

<br>

# ⚙️ Architecture
`MVVM`

<br>

# 🧑🏻‍💻 Team
- 안드로이드 개발자 1명
- iOS 개발자 1명
- 백엔드 개발자 2명
- 기획자 2명
- 디자이너 2명

<br>

# 🤚🏻 Part
- **안드로이드 앱 전체 개발**

<br>

# 🤔 Learned
- 개발 환경 설정을 하며 `ktlint`, `detekt` 를 적용해보았고, build configuration을 **Groovy에서 Kotlin으로 마이그레이션**하는 방법을 알게 되었습니다.
- 화면 전체를 `ScrollView` 로 감싸는 대신, `RecyclerView` 로 구현하여 **스크롤 성능을 향상**시켰습니다.
- `Retrofit` 함수들을 suspend 함수로 만들어서 `Coroutines` 을 이용해 효율적으로 사용하는 방법을 알게 되었습니다.
- `Hilt` 를 사용하여 **의존성 주입**을 하는 방법을 알게 되었습니다.
- `BaseActivity`, `BaseViewModel` 등의 패턴을 적용하여 **DRY 원칙**을 지키고 **재사용성**을 높이기 위한 고민을 해보았습니다.
- `OkHttp Interceptor` 를 이용하여 Header에 일괄적으로 토큰을 넣고, 네트워크 통신 오류 시 서버에서 전송하는 오류 메시지를 받는 방법을 알게 되었습니다.
- **Jira** 협업 툴을 사용하여 생성된 이슈 티켓을 효율적으로 관리하였습니다.
- 구글 Play Console에서 내부 테스트를 하는 방법을 알게 되었습니다.
- 개발 초기에는 앱의 설계와 클린 코드에 집중하였지만, 기획 변경, 디자인 변경에 따라 한정된 시간 안에 기능을 구현해야 했습니다. 클린 코드와 즉각적인 기능 구현 사이에서 어느 것에 더 무게 중심을 두어야 할지 고민을 해보았습니다.

<br>

# ✅ Problem Solving
📌 **앱 축소 및 난독화 시에 에러 발생**
- 앱 축소 및 난독화 적용 시 지속적인 에러 발생하여 파악 시도
- Retrofit 같은 외부 라이브러리를 참조한 경우에는 난독화를 하지 않고 유지해야 한다는 것을 발견
- `proguard-rules.pro` 파일에 Retrofit, Kakao sdk 등을 난독화 예외 처리하는 코드를 추가하여 해결
- `buildTypes` 를 debug와 release로 나눠서 debug 앱에도 난독화를 적용하여 에러를 사전에 방지

<br>

# 📷 Screenshot

|![Screenshot_20231016-220140](https://github.com/beside153/people_inside_android/assets/78577085/8bb7b5dc-8243-4596-b3a2-eccfdef80744) |![Screenshot_20231016-220530](https://github.com/beside153/people_inside_android/assets/78577085/dde9fd2c-b7ef-4301-8337-516b665670d1) |![Screenshot_20231016-220539](https://github.com/beside153/people_inside_android/assets/78577085/c22bae2f-ad5c-47d2-9240-2709fbe6cf22) |
|-|-|-|
|![Screenshot_20231016-220604](https://github.com/beside153/people_inside_android/assets/78577085/e7c043d1-ffc7-4843-93d5-0b4f0cbe48a6) |![Screenshot_20231016-220200](https://github.com/beside153/people_inside_android/assets/78577085/f7d076bf-32da-44c6-9c07-da2976afc2f8) |![Screenshot_20231016-220209](https://github.com/beside153/people_inside_android/assets/78577085/9acffcc0-c2fd-49b5-a0b9-ce0017a3a269) |
|![Screenshot_20231016-220256](https://github.com/beside153/people_inside_android/assets/78577085/96435b1c-2689-4c58-9f75-836af9ba3359) |![Screenshot_20231016-220224](https://github.com/beside153/people_inside_android/assets/78577085/8cc16593-7843-48d3-b8eb-fbb6216dbd6b) |![Screenshot_20231016-220230](https://github.com/beside153/people_inside_android/assets/78577085/fac62ab1-caac-479e-a4cc-23d23b99c856) |
|![Screenshot_20231016-220243](https://github.com/beside153/people_inside_android/assets/78577085/a2fbcb19-6d57-4db2-9a4f-5ae7b6470219) |![Screenshot_20231016-220355](https://github.com/beside153/people_inside_android/assets/78577085/097d9021-24ed-4d97-b60b-5a6116940be7) |![Screenshot_20231016-221637](https://github.com/beside153/people_inside_android/assets/78577085/b5a4f2f1-bf38-456c-80ce-b532d29f0bbc) |
|![Screenshot_20231016-220403](https://github.com/beside153/people_inside_android/assets/78577085/d5fb7e50-4e8e-4e43-af7e-0d3f564f8152) |![Screenshot_20231016-220422](https://github.com/beside153/people_inside_android/assets/78577085/9de3b557-82b4-42db-849d-4613c17bf986) |![Screenshot_20231016-220446](https://github.com/beside153/people_inside_android/assets/78577085/2cac2ca2-f629-445c-949f-06f5061b1555) |

<br>

# 📺 Demo Video

👇 **회원가입 및 로그인 과정입니다**
<div align="center">
  <video src="https://github.com/beside153/people_inside_android/assets/78577085/8ea1822e-8911-482e-b735-d6dfa99f1331" />
</div>

<br>
<br>


👇 **앱의 전반적인 소개를 위한 영상입니다**
<div align="center">
  <video src="https://github.com/beside153/people_inside_android/assets/78577085/0bb49f52-1136-4af5-92a0-7c454739b84e" />
</div>
