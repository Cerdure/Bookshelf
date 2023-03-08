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
- 등급별 포인트 적립 및 쿠폰 지급
- 연속 7일 출석 시 포인트 지급
- 구매 책 첫 리뷰 시 포인트 지급
- 특가 도서 조회
 
#### 내책장
- 북마크한 책 표시
- 구매한 책 리뷰 작성 여부 표시
- 주문 내역 보기 및 주문 취소
- 조회 기간, 주문 상태, 상품명으로 주문 내역 필터링

#### 게시판
- 리뷰 책 비동기 검색, 사진 첨부
- 문의글 공개 여부 및 암호 설정
- 추가 댓글, 댓글 level별 깊이 적용
- 본인의 글, 댓글만 수정,삭제 버튼 활성화
- Admin 계정 답변 시 문의글 답변 완료 처리 
- 모든 글 CRUD 및 페이징




<br/>

## 트러블슈팅 및 리팩토링

<!---------------------------------------------------------------------------->

<details>
<summary><h4>휴대폰 인증 번호 보안 문제</h4></summary>   

> 휴대폰 인증을 구현하면서 보안과 직결되는 인증번호를 어디에 보관할지가 고민이었습니다.<br/>
처음에는 HTML의 input 태그에 hidden 속성을 주어서 보관하다가 data 속성을 이용하는 방법으로 개선했지만, 가장 쉽게 노출되는 HTML의 특성상 인증번호를 보관하기에 적합하지 않다는 생각이 들어 JavaScript의 변수로 위치를 옮겼습니다.<br/>
그러나 사용자가 JavaScript의 데이터에도 접근할 수 있겠다는 생각이 들어 브라우저의 콘솔 창에서 확인해본 결과 예상대로 데이터에 접근이 가능했고, 서버측에 보관해야겠다는 결론을 내리고 최종적으로 다음과 같이 수정하였습니다.

```java
    // Service
    @Override
    @Transactional
    public Map<String, Object> verifyCodeSend(String phone) {
        Map<String, Object> map = new HashMap<>();
        try {
            validateDuplicateMember(phone);  // 휴대폰 번호 중복 검증
            
            StringBuilder code = new StringBuilder();  // 인증번호 생성
            for (int i = 0; i < 4; i++) {
                String ran = Integer.toString(new Random().nextInt(10));
                code.append(ran);
            }
            
            HashMap<String, String> params = new HashMap<>();  // 메시지 내용 생성
            params.put("to", phone);
            params.put("from", "01032100575");
            params.put("type", "SMS");
            params.put("text", "BOOKSHELF 휴대폰인증 메시지 : 인증번호는 " + "[" + code + "]" + "입니다.");
            params.put("app_version", "test app 1.2");

            Message message = new Message(smsApiKey, smsApiSecret);  
            message.send(params);  // 메시지 발송
            
            VerifyCode verifyCode = verifyCodeRepository.findByPhone(phone)  // 재발송인지 확인
                    .orElseGet(() -> VerifyCode.builder()  // 처음 발송일 경우 새로 생성
                            .phone(phone)
                            .build());
            verifyCode.changeCode(code.toString());  // 위의 두 가지 경우를 상성하여 인증번호는 별도 주입
            verifyCode.changeVerified(false);  // 인증 상태 초기화
            verifyCodeRepository.save(verifyCode);  // DB에 인서트

        } catch (Exception e) {  // 예외 발생 시 클라이언트에 상태 전달
            map.put("error", e.getMessage());
            map.put("success", false);
            return map;
        }
        map.put("success", true); 
        return map;
    }
```

</details>

<!---------------------------------------------------------------------------->

<details>
<summary><h4>쿠폰 지급과 초기화 문제</h4></summary>   

> 회원이 쿠폰을 받을 수 있도록 초기화하는 주기를 한 달로 설정하니 어떻게 초기화를 하는지가 문제였습니다. 가장 먼저 떠오른 것은 매월 1일에 일괄적으로 초기화를 하는 것이었지만, 쿠폰의 수령 여부가 계정별로 저장되어있기 때문에, 사용자가 많아질수록 한꺼번에 모든 회원의 데이터를 수정하는 것은 바람직하지 않겠다는 생각이 들었습니다.<br/>
따라서 개별적으로 초기화를 진행하고 초기화의 트리거를 만드는 방식으로 구현하게 되었습니다. 쿠폰을 지급받는 곳은 한 곳이기 때문에 해당 페이지의 요청을 트리거로 삼고, 관련 테이블에 컬럼을 추가하여 초기화 여부와 날짜를 저장해서 중복 실행을 방지하였습니다.

```java
    // Service
    @Override
    public void syncEventState(Member member) {  // 초기화 메서드
        EventState eventState = member.getEventState();
        if (eventState == null) {  // 최초 접속 시 EventState 생성
            eventState = EventState.builder().member(member).build();
            eventStateRepository.save(eventState);
            return;
        }
        
        // 마지막 초기화 달이 현재 달과 일치하지 않을 경우 초기화
        if (eventState.getResetDate().getMonth() != LocalDateTime.now().getMonth()) {  
            eventState.reset();
            eventStateRepository.save(eventState);
        }
    }

    @Override
    public void giveMonthlyCoupon(Authentication authentication) {  // 쿠폰 지급 메서드
        Member member = memberService.findMember(authentication);
        EventState eventState = eventStateRepository.findByMember(member);
        List<MemberCoupon> memberCoupons = new ArrayList<>();

        if (eventState.getTakeCoupon()) {  // 이미 쿠폰을 받았을 경우
            throw new IllegalStateException("이번 달 쿠폰을 이미 받으셨습니다.");
        }
        switch (member.getGrade()) {  // 회원 등급별 쿠폰 차등 지급, 쿠폰은 테이블을 별도 생성해서 참조
            case NEW:
                memberCoupons.add(MemberCoupon.builder().member(member).coupon(coupon(2000)).build());
                break;
            case SILVER:
                memberCoupons.add(MemberCoupon.builder().member(member).coupon(coupon(2000)).build());
                memberCoupons.add(MemberCoupon.builder().member(member).coupon(coupon(3000)).build());
                break;
            case GOLD:
                memberCoupons.add(MemberCoupon.builder().member(member).coupon(coupon(2000)).build());
                memberCoupons.add(MemberCoupon.builder().member(member).coupon(coupon(3000)).build());
                memberCoupons.add(MemberCoupon.builder().member(member).coupon(coupon(5000)).build());
                break;
            case VIP:
                memberCoupons.add(MemberCoupon.builder().member(member).coupon(coupon(2000)).build());
                memberCoupons.add(MemberCoupon.builder().member(member).coupon(coupon(2000)).build());
                memberCoupons.add(MemberCoupon.builder().member(member).coupon(coupon(3000)).build());
                memberCoupons.add(MemberCoupon.builder().member(member).coupon(coupon(5000)).build());
                break;
        }
        memberCouponRepository.saveAll(memberCoupons);
        eventState.changeTakeCoupon(true);  // 지급 받았음을 설정
        eventStateRepository.save(eventState);
    }
```
</details>

<!---------------------------------------------------------------------------->

<details>
<summary><h4>출석 체크 관련 문제</h4></summary>   

> 출석 체크를 구현하는 과정에서 크게 두 가지 문제가 있었습니다.<br/>
첫 번째 문제는 스크롤 높이를 기준으로 자동 출석이 되는 방식을 적용했더니 화면 크기에 따라서 적용 높이가 달라지는 문제였습니다. 이 문제는 해당 요소의 절대 위치를 기준으로 한 것이 원인이었고, 다음과 같이 기준을 상대 위치로 변경하는 것으로 해결했습니다. 

```javascript
  // JavaScript
  $(window).scroll(async () => {
    if (checked) return  // 한 번만 실행
    if (dom('.check-wrapper').getBoundingClientRect().top < 100) {  // 해당 요소가 화면에 중앙에 오면 실행
      const atdResult = await fetch('/event/attendance').then((res) => res.text()).catch(console.log)  // 출석 체크
      switch (atdResult) {
        case 'error':
          alert('요청이 실패하였습니다.')
          break
        case 'point':  //  7일 연속 출석 시
          alert('1000포인트가 지급되었습니다.')
        default:
          // ... 출석 애니메이션
          atdCount++  // 화면의 출석일 증가
          checkFill(atdCount)
          setTimeout(() => {
            $('.check-box-top-current')
              .text(atdCount + '일')
              .css('animation', 'jello-horizontal 0.9s both')
          }, 500)
      }
      checked = true
    }
  })
```

> 두 번째 문제는 연속 7일이 아니어도 7일만 출석하면 포인트가 지급되는 문제였습니다. 이에 출석 테이블에 별도로 연속일 수를 저장하는 컬럼을 추가하여, 연속이 아닐 시 초기화하는 방법으로 해결했습니다.

```java 
    // Service
    public void saveAttendance(Authentication authentication) {
        if (authentication == null) {
            throw new IllegalStateException("로그인 상태가 아닙니다.");
        } else {
            Member member = memberService.findMember(authentication);
            Attendance today = attendanceRepository.findByMemberAndRegDate(member, LocalDate.now());
            Attendance yesterday = attendanceRepository.findByMemberAndRegDate(member, LocalDate.now().minusDays(1));
            if (today == null) {  // 아직 출석이 되지 않았을 때 실행
                if (yesterday == null || yesterday.getPointed()) {  // 어제 출석을 하지 않았거나 포인트를 받았으면 초기화
                    member.changeAtdCount(1);
                } else {
                    member.changeAtdCount(member.getAtdCount() + 1);  // 연속출석일 증가
                }
                if (member.getAtdCount() == 7) {  //  연속 7일이면 포인트 지급
                    member.changePoint(member.getPoint() + 1000);
                    attendanceRepository.save(Attendance.builder().member(member).pointed(true).build());  // 지급 받았음을 표시
                }
                attendanceRepository.save(Attendance.builder().member(member).build());
            } else {
                throw new IllegalStateException("이미 출석이 되어있습니다.");
            }
        }
    }
```
</details>

<!---------------------------------------------------------------------------->

<details>
<summary><h4>회원 정보 수정 코드 개선</h4></summary>   

> 회원 정보를 각각 수정하도록 구현하다보니 중복되는 코드가 많아 유지보수에 어려움을 겪었습니다. 이에 개선의 필요성을 느끼고 리팩토링을 진행하였습니다.<br/>
javascript에서는 수정하려는 항목을 type으로 구분하여 하나의 함수로 만들고, 서버에서도 controller를 통일하여 수정하려는 값을 하나의 DTO로 받음으로써 중복되는 부분을 최대한 줄였습니다. 결과적으로 javascript, controller, service에서 각각 6번씩 반복되던 코드들을 회원 정보 수정이라는 하나 기능으로 통합하여 낭비를 줄이고 유지보수성을 향상시킬 수 있었습니다. 

```javascript
  // JavaScript
  $(document).on('click change', '.change-btn', async (e) => {  // 각 항목의 수정버튼 class 통일
    const info = $(e.target).closest('.info-group')
    const type = e.target.dataset.type
    let formData = new FormData()
    
    switch (type) {  // 수정하려는 정보를 type으로 구분
      case 'profile':
        formData.append('profileImg', e.target.files[0])
        break
      case 'address':
        formData.set('zipcode', info.find('.zipcode').val())
        formData.set('city', info.find('.city').val())
        formData.set('street', info.find('.street').val())
        break
      case 'phone':  // 전화번호 변경은 휴대폰 인증 후 진행
        const result = await fetch('/verify/check?phone=' + $('.new-phone').val() + '&code=' + $('.code').val())
          .then((res) => res.json())
          .catch(console.log)
        if (!result.success) {  // 인증에 실패하였을 때
          alert(result.error)  
          return
        }
      default:
        formData.set(type, info.find('.info-data').val())  // type을 함께 서버로 전달
    }

    const result = await fetch('/myshelf/info/update', {
      method: 'post',
      cache: 'no-cache',
      body: formData
    })
      .then((res) => res.json())
      .catch((err) => console.log(err))
    
    if(result.success) {  // 변경 완료 시
      alert('변경되었습니다.')
      switch (type) {
        case 'profile':
          // ... 프로필 이미지 변경
          break
        case 'phone':  // 전화번호 변경일 경우 아이디로 사용하기 때문에 재로그인
          alert('다시 로그인해주세요.')
          location.replace('/logout')
    } else {  // 변경 실패 시
      alert('요청이 실패하였습니다.')
      console.log(result.error)
    }
  })
```

```java
    // Controller
    @PostMapping("/myshelf/info/update")
    @ResponseBody
    public Map<String, Object> updateInfo(@ModelAttribute MemberDto memberDto, Authentication authentication) {
        return memberService.updateInfo(memberDto, authentication);
    }
    
    // Service
    @Override
    @Transactional
    public Map<String, Object> updateInfo(MemberDto memberDto, Authentication authentication) {
        Map<String, Object> map = new HashMap<>();
        try {
            if (memberDto.getProfileImg() != null) {  // 프로필 이미지 변경일 경우
                String profilePath = uploadFileService.saveFile(memberDto.getProfileImg());
                memberDto.setProfilePath(profilePath);
                map.put("profile", profilePath);
            }
            Member member = findMember(authentication);
            member.update(memberDto, passwordEncoder);  // DTO를 전달하여 null이 아닌 부분 업데이트
            memberRepository.save(member);
        } catch (Exception e) {  // 예외 발생 시 상태 전달
            e.printStackTrace();
            map.put("success", false);
            map.put("error", e.getMessage());
            return map;
        }
        map.put("success", true);
        return map;
    }
```


</details>

<!---------------------------------------------------------------------------->

<details>
<summary><h4>자주 쓰는 JavaScript 기능 전역화</h4></summary>   

> 페이지별로 javscaript 파일을 분리하여 개발하다보니 동일한 기능이 있을 때마다 비슷한 코드를 반복해서 넣어줘야 했습니다. 이것이 너무 비효율적이라는 생각이 들어서 자주 사용하는 기능들을 전역으로 사용할 수 있도록 리팩토링하였습니다.<br/>
Thymeleaf layout의 default 템플릿에서 참조하는 default.js 파일에 자주 사용하는 기능들을 추가하여 여러 페이지에서 참조할 수 있게 변경하였고, 그 결과 반복 작업이 줄어들어 생산성이 크게 향상되었습니다. 다음은 대표적인 전역 기능입니다.

```javascript
// JavaScript
const allPassed = (...passed) => passed.every((e) => e)  // 모든 조건들 pass 체크

function it(_this) {  // Composite pattern 적용
  let _if = null
  return {
    if: function (boolean) {  // 함수들의 실행 조건
      _if = boolean
      return this
    },
    addClass: function (_class) {  // 조건이 true일 때만 class 유지
      if (_if) $(_this).addClass(_class)
      else $(_this).removeClass(_class)
      return this
    },
    removeClass: function (_class) {  // 조건이 false일 때만 class 유지
      if (_if) $(_this).removeClass(_class)
      else $(_this).addClass(_class)
    },
    activeClass: function (_class, other) {  // 동일한 요소 중 자신만 class 추가
      $(other).removeClass(_class)
      $(_this).addClass(_class)
      return this
    },
    toggleSelf: function (time) {  // 조건 true이면 자신을 활성화
      if (_if) $(_this).fadeIn(time)
      else $(_this).fadeOut(time)
      return this
    },
    allHasClass: function (_hasClass) {  // 동일한 요소 전부 hasClass 실행
      $(_this).get().every((e) => e.classList.contains(_hasClass))
    }
  }
}

function flexFadeIn_back(_this) {  // display:flex 상태로 background와 함께 fadeIn
  $('.modal-background').fadeIn(100)
  $(_this).css('display', 'flex')
  $(_this).animate({ opacity: '1' }, 500)
  $('body').css('overflow-y', 'hidden')
}
```
</details>

<!---------------------------------------------------------------------------->


<br/>

## 보완 사항
#### 사이트 관리를 위한 대시보드 추가
#### 계정별 최근 소식 표시
#### 결제 대금 환불 기능 추가


<br/>

## 회고
저의 첫 프로젝트로, 처음에는 독학으로 얻은 Spring 지식들을 적용해보고 프론트를 공부해보기 위한 목적으로 시작했습니다. 사이트도 지식도 대부분 백지 상태에서 시작하다보니, UI를 제작하고 직접 애니메이션을 만들거나 기능을 구현해보면서 다양한 문제 상황에 직면했습니다. 그럴 때마다 이리저리 검색해보고 인과관계를 파악하며 하나씩 해결해나갔고 차츰 문제를 해결하는 것에 익숙해졌습니다. 지금 보면 아쉽고 부족한 부분이 많지만 팀프로젝트 기간에 여러 동기들을 도움을 줄 수 있었던 것도 이 프로젝트 덕분이라는 생각이 듭니다.<br/><br/>
마지막까지 읽어주셔서 감사합니다.
