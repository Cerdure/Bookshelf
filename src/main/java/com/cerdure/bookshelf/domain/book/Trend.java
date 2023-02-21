package com.cerdure.bookshelf.domain.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static java.util.Optional.ofNullable;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Trend {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "trend_id")
    private Long id;
    private String searchData;
    private Integer count;

    @PrePersist
    private void prePersist() {
        this.count = ofNullable(this.count).orElse(1);
    }

    public void countPlus() {
        this.count++;
    }
}
