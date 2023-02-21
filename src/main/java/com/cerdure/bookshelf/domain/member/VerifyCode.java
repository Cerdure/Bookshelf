package com.cerdure.bookshelf.domain.member;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

import static java.util.Optional.ofNullable;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VerifyCode {

    @Id
    @GeneratedValue
    @Column(name = "verify_code_id")
    private Long id;
    private String phone;
    private String code;
    private Boolean verified;
    private LocalDate regDate;

    @PrePersist
    private void prePersist() {
        this.verified = ofNullable(this.verified).orElse(false);
        this.regDate = ofNullable(this.regDate).orElse(LocalDate.now());
    }

    public void changeCode(String code) {
        this.code = code;
    }

    public void changeVerified(boolean verified) {
        this.verified = verified;
    }
}
