# BOOKSHELF  [![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2FCerdure%2FBookshelf&count_bg=%23E7AB05&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=hits&edge_flat=false)](https://hits.seeyoufarm.com)

<p align="left">
<img width="600" alt="banner" src="https://user-images.githubusercontent.com/110950838/223159866-0c2b8227-389b-4dee-980b-ed207dc1ffcf.png">
</p>

**BOOKSHELF(북셸프)는** **온라인 서점 사이트**입니다.</br>
책을 할인된 가격으로 구매하거나 읽어본 책의 리뷰를 누구나 자유롭게 남길 수 있습니다. 실제 사이트와 비슷한 규모로 제작하였으며 회원 등급에 따라 쿠폰과 포인트 적립을 차등 적용하는 것으로 차별화를 꾀했습니다.<br/><br/>
> 개발 기간: 2022.11 ~ 2022.12 (6주)<br/>
> 개발 인원: 1명 (개인 프로젝트)

<br/>

## 배포 주소
| URL | **[https://bookshelf.run](https://bookshelf.run)** |
| :-: | :-: |


<br/>

## 목차
- **[사용 기술](#사용-기술)**
- **[구조 및 설계](#구조-및-설계)**
- **[서비스 화면](#서비스-화면)**
- **[주요 기능](#주요-기능)**
- **[트러블슈팅 및 리팩토링](#트러블슈팅-및-리팩토링)**
- **[보완 사항](#보완-사항)**
- **[회고](#회고)**


<br/>

## 사용 기술

#### Backend
- Java 11
- SpringBoot 2.7.7
- JPA(Spring Data JPA)
- Spring Security
- Gradle 7.6
- MySQL 8.0.31

#### Frontend
- HTML/CSS
- JavaScript
- Ajax
- JQuery 3.3.1


<br/>

## 구조 및 설계

#### 디렉토리 구조

<details>
  
<summary>java</summary>   

```bash
java
 ┣ client
 ┃ ┣ google
 ┃ ┃ ┣ GoogleLoginClient.java
 ┃ ┃ ┗ GoogleMemberInfoClient.java
 ┃ ┣ kakao
 ┃ ┃ ┣ KakaoLoginClient.java
 ┃ ┃ ┣ KakaoLogoutAllClient.java
 ┃ ┃ ┣ KakaoLogoutClient.java
 ┃ ┃ ┗ KakaoMembernfoClient.java
 ┃ ┗ naver
 ┃ ┃ ┣ NaverLoginClient.java
 ┃ ┃ ┗ NaverMemberInfoClient.java
 ┣ controller
 ┃ ┣ book
 ┃ ┃ ┗ BookController.java
 ┃ ┣ borad
 ┃ ┃ ┣ InquireController.java
 ┃ ┃ ┣ NoticeController.java
 ┃ ┃ ┗ ReviewController.java
 ┃ ┣ event
 ┃ ┃ ┗ EventController.java
 ┃ ┣ home
 ┃ ┃ ┗ HomeController.java
 ┃ ┣ join
 ┃ ┃ ┗ JoinController.java
 ┃ ┣ login
 ┃ ┃ ┣ api
 ┃ ┃ ┃ ┣ GoogleApiLoginController.java
 ┃ ┃ ┃ ┣ KakaoApiLoginController.java
 ┃ ┃ ┃ ┣ LoginApiSimpleJoinController.java
 ┃ ┃ ┃ ┗ NaverApiLoginController.java
 ┃ ┃ ┗ LoginController.java
 ┃ ┣ myshelf
 ┃ ┃ ┗ MyshelfController.java
 ┃ ┣ order
 ┃ ┃ ┗ OrderController.java
 ┃ ┗ search
 ┃ ┃ ┗ SearchController.java
 ┣ domain
 ┃ ┣ board
 ┃ ┃ ┣ Inquire.java
 ┃ ┃ ┣ Notice.java
 ┃ ┃ ┣ Reply.java
 ┃ ┃ ┗ Review.java
 ┃ ┣ book
 ┃ ┃ ┣ Book.java
 ┃ ┃ ┣ Bookmark.java
 ┃ ┃ ┣ Category.java
 ┃ ┃ ┗ Trend.java
 ┃ ┣ enums
 ┃ ┃ ┣ MemberGrade.java
 ┃ ┃ ┣ MemberJoinType.java
 ┃ ┃ ┣ MemberRole.java
 ┃ ┃ ┗ OrderState.java
 ┃ ┣ event
 ┃ ┃ ┣ Attendance.java
 ┃ ┃ ┣ Coupon.java
 ┃ ┃ ┗ EventState.java
 ┃ ┣ file
 ┃ ┃ ┗ UploadFile.java
 ┃ ┣ member
 ┃ ┃ ┣ Address.java
 ┃ ┃ ┣ Member.java
 ┃ ┃ ┣ MemberCoupon.java
 ┃ ┃ ┣ PersistentLogins.java
 ┃ ┃ ┗ VerifyCode.java
 ┃ ┗ order
 ┃ ┃ ┣ Cart.java
 ┃ ┃ ┣ OrderItem.java
 ┃ ┃ ┗ Orders.java
 ┣ dto
 ┃ ┣ board
 ┃ ┃ ┣ InquireDto.java
 ┃ ┃ ┣ NoticeDto.java
 ┃ ┃ ┣ ReplyDto.java
 ┃ ┃ ┗ ReviewDto.java
 ┃ ┣ book
 ┃ ┃ ┗ BookDto.java
 ┃ ┣ loginApi
 ┃ ┃ ┣ ApiJoinDto.java
 ┃ ┃ ┣ AuthResponseDto.java
 ┃ ┃ ┣ GoogleLoginInfoResponseDto.java
 ┃ ┃ ┣ GoogleTokenDto.java
 ┃ ┃ ┣ KakaoLoginInfoResponseDto.java
 ┃ ┃ ┣ LoginApiSessionDto.java
 ┃ ┃ ┣ LogOutDto.java
 ┃ ┃ ┣ MemberApiLoginInfoDto.java
 ┃ ┃ ┣ MemberResponseDto.java
 ┃ ┃ ┗ NaverLoginInfoResponseDto.java
 ┃ ┣ member
 ┃ ┃ ┣ AttendanceDto.java
 ┃ ┃ ┗ MemberDto.java
 ┃ ┗ order
 ┃ ┃ ┣ CartDto.java
 ┃ ┃ ┣ CartRemoveDto.java
 ┃ ┃ ┣ OrderDto.java
 ┃ ┃ ┣ OrderItemDto.java
 ┃ ┃ ┗ OrderSearchDto.java
 ┣ repository
 ┃ ┣ board
 ┃ ┃ ┣ InquireRepository.java
 ┃ ┃ ┣ NoticeRepository.java
 ┃ ┃ ┣ ReplyRepository.java
 ┃ ┃ ┗ ReviewRepository.java
 ┃ ┣ book
 ┃ ┃ ┣ BookmarkRepository.java
 ┃ ┃ ┣ BookRepository.java
 ┃ ┃ ┣ CategoryRepository.java
 ┃ ┃ ┗ TrendRepository.java
 ┃ ┣ event
 ┃ ┃ ┣ AttendanceRepository.java
 ┃ ┃ ┣ CouponRepository.java
 ┃ ┃ ┗ EventStateRepository.java
 ┃ ┣ file
 ┃ ┃ ┗ FileRepository.java
 ┃ ┣ member
 ┃ ┃ ┣ MemberCouponRepository.java
 ┃ ┃ ┣ MemberRepository.java
 ┃ ┃ ┗ VerifyCodeRepository.java
 ┃ ┗ order
 ┃ ┃ ┣ CartRepository.java
 ┃ ┃ ┣ OrderItemRepository.java
 ┃ ┃ ┗ OrdersRepository.java
 ┣ service
 ┃ ┗ classes
 ┃ ┃ ┣ board
 ┃ ┃ ┃ ┣ interfaces
 ┃ ┃ ┃ ┃ ┣ InquireService.java
 ┃ ┃ ┃ ┃ ┣ NoticeService.java
 ┃ ┃ ┃ ┃ ┣ ReplyService.java
 ┃ ┃ ┃ ┃ ┗ ReviewService.java
 ┃ ┃ ┃ ┣ InquireServiceImpl.java
 ┃ ┃ ┃ ┣ NoticeServiceImpl.java
 ┃ ┃ ┃ ┣ ReplyServiceImpl.java
 ┃ ┃ ┃ ┗ ReviewServiceImpl.java
 ┃ ┃ ┣ book
 ┃ ┃ ┃ ┣ interfaces
 ┃ ┃ ┃ ┃ ┣ BookService.java
 ┃ ┃ ┃ ┃ ┣ CategoryService.java
 ┃ ┃ ┃ ┃ ┗ TrendService.java
 ┃ ┃ ┃ ┣ BookServiceImpl.java
 ┃ ┃ ┃ ┣ CategoryServiceImpl.java
 ┃ ┃ ┃ ┗ TrendServiceImpl.java
 ┃ ┃ ┣ event
 ┃ ┃ ┃ ┣ interfaces
 ┃ ┃ ┃ ┃ ┣ AttendanceService.java
 ┃ ┃ ┃ ┃ ┗ EventService.java
 ┃ ┃ ┃ ┣ AttendanceServiceImpl.java
 ┃ ┃ ┃ ┗ EventServiceImpl.java
 ┃ ┃ ┣ file
 ┃ ┃ ┃ ┣ interfaces
 ┃ ┃ ┃ ┃ ┗ UploadFileService.java
 ┃ ┃ ┃ ┗ UploadFileServiceImpl.java
 ┃ ┃ ┣ login
 ┃ ┃ ┃ ┣ api
 ┃ ┃ ┃ ┃ ┣ GoogleApiImpl.java
 ┃ ┃ ┃ ┃ ┣ KakaoApiLoginImpl.java
 ┃ ┃ ┃ ┃ ┣ LoginAllApiService.java
 ┃ ┃ ┃ ┃ ┣ LoginApiService.java
 ┃ ┃ ┃ ┃ ┗ NaverApiLoginImpl.java
 ┃ ┃ ┃ ┗ LoginServiceImpl.java
 ┃ ┃ ┣ member
 ┃ ┃ ┃ ┣ interfaces
 ┃ ┃ ┃ ┃ ┗ MemberService.java
 ┃ ┃ ┃ ┗ MemberServiceImpl.java
 ┃ ┃ ┗ order
 ┃ ┃ ┃ ┣ interfaces
 ┃ ┃ ┃ ┃ ┗ OrderService.java
 ┃ ┃ ┃ ┗ OrderServiceImpl.java
 ┣ utils
 ┃ ┣ DataUtils.java
 ┃ ┗ DayUtils.java
 ┣ web
 ┃ ┣ security
 ┃ ┃ ┣ LoginFailureHandler.java
 ┃ ┃ ┣ MemberAdapter.java
 ┃ ┃ ┗ SecurityConfig.java
 ┃ ┣ session
 ┃ ┃ ┣ ApiSession.java
 ┃ ┃ ┗ SessionInfoController.java
 ┃ ┣ LoggerInterceptor.java
 ┃ ┗ WebMvcConfiguration.java
 ┣ BookshelfApplication.java
 ┗ InitDb.java
````

</details>

<details>
  
<summary>resources</summary>   

```bash
resources
 ┣ static
 ┃ ┣ css
 ┃ ┃ ┣ board-inquire.css
 ┃ ┃ ┣ board-notice.css
 ┃ ┃ ┣ board-review.css
 ┃ ┃ ┣ book-detail.css
 ┃ ┃ ┣ cart.css
 ┃ ┃ ┣ default.css
 ┃ ┃ ┣ event.css
 ┃ ┃ ┣ home.css
 ┃ ┃ ┣ inquire-detail.css
 ┃ ┃ ┣ join-api.css
 ┃ ┃ ┣ join.css
 ┃ ┃ ┣ login.css
 ┃ ┃ ┣ myshelf.css
 ┃ ┃ ┣ notice-detail.css
 ┃ ┃ ┣ order-success.css
 ┃ ┃ ┣ order.css
 ┃ ┃ ┣ search-result.css
 ┃ ┃ ┗ search.css
 ┃ ┣ img
 ┃ ┃ ┣ background
 ┃ ┃ ┣ book-cover
 ┃ ┃ ┗ icon
 ┃ ┣ js
 ┃ ┃ ┣ board-inquire.js
 ┃ ┃ ┣ board-notice.js
 ┃ ┃ ┣ board-review.js
 ┃ ┃ ┣ book-detail.js
 ┃ ┃ ┣ cart.js
 ┃ ┃ ┣ default.js
 ┃ ┃ ┣ event.js
 ┃ ┃ ┣ home.js
 ┃ ┃ ┣ inquire-detail.js
 ┃ ┃ ┣ join-api.js
 ┃ ┃ ┣ join.js
 ┃ ┃ ┣ login.js
 ┃ ┃ ┣ myshelf.js
 ┃ ┃ ┣ notice-detail.js
 ┃ ┃ ┣ order-success.js
 ┃ ┃ ┣ order.js
 ┃ ┃ ┣ search-result.js
 ┃ ┃ ┗ search.js
 ┃ ┗ upload-img
 ┣ templates
 ┃ ┣ fragments
 ┃ ┃ ┣ alerts.html
 ┃ ┃ ┣ footer.html
 ┃ ┃ ┗ header.html
 ┃ ┣ layout
 ┃ ┃ ┗ default.html
 ┃ ┣ board-inquire.html
 ┃ ┣ board-notice.html
 ┃ ┣ board-review.html
 ┃ ┣ book-detail.html
 ┃ ┣ cart.html
 ┃ ┣ event.html
 ┃ ┣ home.html
 ┃ ┣ inquire-detail.html
 ┃ ┣ join-api.html
 ┃ ┣ join.html
 ┃ ┣ login.html
 ┃ ┣ myshelf.html
 ┃ ┣ notice-detail.html
 ┃ ┣ order-success.html
 ┃ ┣ order.html
 ┃ ┣ search-result.html
 ┃ ┗ search.html
 ┗ application.yml
````

</details>

<br/>

#### DB 설계

![erd](https://user-images.githubusercontent.com/110950838/223445539-d067f8c9-d34a-4ae5-9f7e-212ac3cfe53a.png)

<br/>

## 서비스 화면
| 홈 | 책찾기 |
| :-: | :-: |
| <img width="789" alt="home" src="https://user-images.githubusercontent.com/110950838/223450898-8721d32f-9dc2-4318-a8fd-c5662834a3d2.PNG"> | <img width="789" alt="search-1" src="https://user-images.githubusercontent.com/110950838/223450486-72087272-d7ae-4adb-b7c0-a3d14d5574eb.PNG"> |  
| 검색결과 | 책상세 |  
| <img width="789" alt="search-2" src="https://user-images.githubusercontent.com/110950838/223450500-885f033b-08b2-494f-9520-a7727187688a.PNG"> | <img width="789" alt="2023-03-07 23;14;05" src="https://user-images.githubusercontent.com/110950838/223450749-57dd20cb-c325-4bca-8e29-73185f2b07b6.PNG"> | 
| 장바구니 | 주문하기 |  
| <img width="789" alt="cart" src="https://user-images.githubusercontent.com/110950838/223451776-8b140ac4-9c63-4a13-8ff3-cfd44fc28987.PNG"> | <img width="789" alt="order" src="https://user-images.githubusercontent.com/110950838/223451791-f0f80b51-1cb8-4a85-9c09-2a31464e10c3.PNG"> |  
| 이벤트 | 회원등급 |  
| <img width="789" alt="event-1" src="https://user-images.githubusercontent.com/110950838/223450320-15c59a0a-0685-4c04-a5ad-10efbfd96381.PNG"> | <img width="789" alt="event-2" src="https://user-images.githubusercontent.com/110950838/223451913-058a6023-e953-49fa-8871-3fba7cb5cb6b.PNG"> |  
| 구매내역 | 회원정보 |  
| <img width="789" alt="list" src="https://user-images.githubusercontent.com/110950838/223452106-2f7e4a7a-61d6-42dd-b37a-8132f68e678e.PNG"> | <img width="789" alt="my" src="https://user-images.githubusercontent.com/110950838/223452129-f176969e-db5b-4fa6-814d-5fbe01be0750.PNG"> |  
| 책리뷰 | 상품문의 |  
| <img width="789" alt="review" src="https://user-images.githubusercontent.com/110950838/223452203-3f2de7c4-dc0b-4dba-87b9-fbfd9cb8e166.PNG"> | <img width="789" alt="inquire" src="https://user-images.githubusercontent.com/110950838/223452258-f3040729-8c9e-4e0a-9bb6-0568779168c3.PNG"> |



<br/>

## 주요 기능

#### 회원
- Spring Security를 이용한 비밀번호 암호화, 자동 로그인 
- CoolSMS를 이용한 회원가입, 비밀번호 재설정 시 휴대폰 인증
- 클라이언트 측과 서버 측에서의 데이터 교차 검증 
- 프로필을 비롯한 회원정보 수정 및 탈퇴

#### 책찾기
- 검색어 입력 시 비동기 자동완성
- 검색 결과를 카테고리와 출간일로 필터링
- 검색 결과 책이름, 판매량, 출간일, 상품평순으로 정렬

#### 책구매
- I'mport API를 이용한 카드결제, 토스페이, 카카오페이
- 장바구니 담기 및 재고 범위 내 수량 변경, 삭제
- 배송정보 주문자 정보, 최근 배송지, 직접 입력 선택
- 쿠폰 및 포인트 사용

#### 혜택
- 회원 등급에 따른 포인트 적립 및 쿠폰 지급
- 연속 7일 출석 체크 시 포인트 지급
- 구매한 책 첫 리뷰 작성 시 포인트 지급
- 특가 도서 조회
 
#### 내책장
- 북마크한 책 표시
- 구매한 책 리뷰 작성 여부 표시
- 주문 내역 보기 및 주문 취소
- 조회 기간, 주문 상태, 상품명으로 주문 내역 필터링

#### 게시판
- 비동기 상품 검색을 통한 리뷰 작성, 사진 첨부
- 상품 문의글 공개 여부 및 암호 설정
- 댓글과 덧글 작성, 덧글 level 깊이 적용
- 본인 글, 댓글만 수정,삭제 버튼 표시
- Admin 계정 답변 시 문의글 답변 완료 처리 
- 모든 글 CRUD 및 페이징




<br/>

## 트러블슈팅 및 리팩토링

<!---------------------------------------------------------------------------->

<details>
<summary><h4>출석 체크 관련 문제</h4></summary>   

>
</details>

<!---------------------------------------------------------------------------->

<details>
<summary><h4>자주 쓰는 JavaScript 기능 전역화</h4></summary>   

>
</details>

<!---------------------------------------------------------------------------->








<br/>

## 보완 사항
#### 사이트 관리를 위한 대시보드 추가
#### 계정별 최근 소식 표시
#### 결제 대금 환불 기능 추가


<br/>

## 회고
저의 첫 프로젝트로, 처음에는 독학으로 얻은 Spring 지식들을 적용해보고 프론트를 공부해보기 위한 목적으로 시작했습니다.<br/>
사이트도 지식도 백지 상태에서부터 시작해서, UI를 제작하고 직접 애니메이션을 만들거나 기능을 구현해보면서 다양한 문제 상황에 직면했습니다. 그럴 때마다 이리저리 검색해보고 인과관계를 파악하며 하나씩 해결해나갔고 차츰 스스로 문제를 해결하는 것이 익숙해지게 되었습니다. 아쉽고 부족한 부분이 많지만 팀프로젝트 기간에 여러 동기들을 도움을 줄 수 있었던 것도 이 프로젝트 덕분이라고 생각합니다.
