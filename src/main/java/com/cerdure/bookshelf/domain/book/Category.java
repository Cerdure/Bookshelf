package com.cerdure.bookshelf.domain.book;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @Column(name = "category_id")
    private Integer id;
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Book> books = new ArrayList<>();

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
