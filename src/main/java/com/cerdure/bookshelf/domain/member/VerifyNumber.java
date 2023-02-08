package com.cerdure.bookshelf.domain.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PhoneVerifyNumber {

    @Id @GeneratedValue
    @Column(name = "phone_verify_number_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String number;

    private LocalDate regDate;

    @PrePersist
    public void prePersist() {
        this.regDate = this.regDate == null ? LocalDate.now() : this.regDate;
    }

    @Builder
    public PhoneVerifyNumber(Long id, Member member, String number, LocalDate regDate) {
        this.id = id;
        this.member = member;
        this.number = number;
        this.regDate = regDate;
    }
}
