package com.cerdure.bookshelf.domain.event;

import com.cerdure.bookshelf.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Optional.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attendance {

    @Id
    @GeneratedValue
    @Column(name = "attendance_id")
    private Long id;
    private Boolean pointed;
    private LocalDate regDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @PrePersist
    private void prePersist() {
        this.regDate = ofNullable(this.regDate).orElse(LocalDate.now());
        this.pointed = ofNullable(this.pointed).orElse(false);
    }
}
