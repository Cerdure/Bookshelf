package com.cerdure.bookshelf.dto.member;

import com.cerdure.bookshelf.domain.member.Member;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttendanceDto {
    private Long id;
    private Member member;
    private LocalDate regDate;
}
