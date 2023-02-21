package com.cerdure.bookshelf.domain.book;

import com.cerdure.bookshelf.domain.board.Review;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.*;

import static java.lang.Math.random;
import static java.util.Optional.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {

    @Id
    @GeneratedValue
    @Column(name = "book_id")
    private Long id;
    private String name;
    private String imgPath;
    private String author;
    private String publisher;
    private LocalDate publishDate;
    private String ISBN;
    private Integer originPrice;
    private Integer discountRate;
    private Integer discountPrice;
    private Integer stock;
    private Integer sales;
    private Integer rating;
    @Size(max = 3000)
    private String intro;
    @Size(max = 3000)
    private String bookIndex;
    @Size(max = 3000)
    private String publisherReview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "book", orphanRemoval = true)
    private List<Bookmark> bookmarks;

    @OneToMany(mappedBy = "book", orphanRemoval = true)
    private List<Review> reviews;

    @PrePersist
    private void prePersist() {
        StringBuilder randomISBN = new StringBuilder("9");
        for (int i = 0; i < 12; i++) randomISBN.append((int) (random() * 9));
        LocalDate randomDate = LocalDate.now().minusDays((long) (random() * 1000));

        this.author = ofNullable(this.author).orElseGet(this::createName);
        this.ISBN = ofNullable(this.ISBN).orElse(randomISBN.toString());
        this.publishDate = ofNullable(this.publishDate).orElse(randomDate);
        this.publisher = ofNullable(this.publisher).orElseGet(this::createPublisher);
        this.originPrice = ofNullable(this.originPrice).orElse((int) (random() * 29 + 7) * 1000);
        this.discountRate = ofNullable(this.discountRate).orElse((int) (random() * 6) * 10);
        this.discountPrice = ofNullable(this.discountPrice).orElse(this.originPrice * (100 - this.discountRate) / 100);
        this.stock = ofNullable(this.stock).orElse(20);
        this.sales = ofNullable(this.sales).orElse((int) (random() * 100) + 1);
        this.rating = ofNullable(this.rating).orElse((int) (random() * 5) + 1);
        this.intro = ofNullable(this.intro).orElse(this.name + " 책 소개");
        this.bookIndex = ofNullable(this.bookIndex).orElse(this.name + " 목차");
        this.publisherReview = ofNullable(this.publisherReview).orElse(this.name + " 서평");
    }

    public void coincidenceHighlight(String input) {
        if (input == null) return;
        input = input.toUpperCase();
        int startIndex = this.name.toUpperCase().indexOf(input);
        if (startIndex < 0) return;
        int endIndex = startIndex + input.length();
        String coincidenceStr = "<strong>" + this.name.substring(startIndex, endIndex) + "</strong>";
        String prevStr = this.name.substring(0, startIndex);
        String nextStr = this.name.substring(endIndex);
        this.name = prevStr + coincidenceStr + nextStr;
    }

    public void changeStock(int stock) {
        this.stock = stock;
    }

    public void changeSales(int sales) {
        this.sales = sales;
    }

    private String createPublisher() {
        return this.name.length() > 6 ? this.name.substring(0, 5) + " 출판사" : this.name + " 출판사";
    }

    private String createName() {
        List<String> 성 = Arrays.asList("김", "이", "박", "최", "정", "강", "조", "윤", "장", "임", "한", "오", "서", "신", "권", "황");
        List<String> 이름 = Arrays.asList("경", "관", "규", "근", "기", "길", "나", "단", "달", "담", "대", "덕", "명", "문", "민",
                "백", "범", "병", "서", "석", "선", "설", "섭", "성", "수", "승", "시", "신", "아", "안",
                "애", "연", "영", "예", "오", "옥", "완", "원", "유", "윤", "이", "익", "인", "일", "재",
                "전", "정", "주", "준", "중", "지", "진", "찬", "창", "태", "하", "한", "해", "혁", "현",
                "형", "혜", "호", "홍", "효", "훈", "휘", "희");
        Collections.shuffle(성);
        Collections.shuffle(이름);
        return 성.get(0) + 이름.get(0) + 이름.get(1);
    }
}
