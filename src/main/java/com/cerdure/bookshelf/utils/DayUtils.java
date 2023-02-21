package com.cerdure.bookshelf.utils;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DayUtils {
    private LocalDate localDate;
    private Integer day;
    private boolean checked;

    @Builder
    public DayUtils(LocalDate localDate, Integer day, boolean checked) {
        this.localDate = localDate;
        this.day = day;
        this.checked = checked;
    }
}
