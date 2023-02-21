package com.cerdure.bookshelf.dto.book;

import com.cerdure.bookshelf.domain.book.Category;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookDto {

    private Long id;
    private String name;
    private String imgPath;
    private String author;
    private String publisher;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publishDate;
    private String ISBN;
    private Integer originPrice;
    private Integer discountRate;
    private Integer discountPrice;
    private Integer stock;
    private String intro;
    private String bookIndex;
    private String publisherReview;
    private String sortOrder;
    private List<Integer> categoryIds;
    private Category category;

    public void coincidenceHighlight(String input) {
        input = input.toUpperCase();
        int startIndex = this.name.toUpperCase().indexOf(input);
        int endIndex = startIndex + input.length();
        String coincidenceStr = "<strong>" + this.name.substring(startIndex, endIndex) + "</strong>";
        String prevStr = this.name.substring(0, startIndex);
        String nextStr = this.name.substring(endIndex);
        this.name = prevStr + coincidenceStr + nextStr;
    }
}
