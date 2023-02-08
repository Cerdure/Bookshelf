package com.cerdure.bookshelf.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkDto {

    private Integer totalMark;
    private Integer memberBookmark;
}
