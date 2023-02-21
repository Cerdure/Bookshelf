package com.cerdure.bookshelf.dto.member;


import com.cerdure.bookshelf.domain.enums.MemberGrade;
import com.cerdure.bookshelf.domain.enums.MemberJoinType;
import com.cerdure.bookshelf.domain.member.Address;
import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.domain.order.Orders;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.ArrayList;
import java.util.List;

import static com.cerdure.bookshelf.domain.enums.MemberJoinType.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDto {

    private Long id;
    private String pw;
    private String pwCheck;
    private String name;
    private String nickname;
    private String phone;
    private String zipcode;
    private String city;
    private String street;
    private String email;
    private Integer point;
    private MultipartFile profileImg;
    private String profilePath;
    private Long codeId;
    private List<Orders> orders = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private MemberGrade grade;

    @Enumerated(EnumType.STRING)
    private MemberJoinType joinType;

    public Member createMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .pw(passwordEncoder.encode(this.pw))
                .name(this.name)
                .nickname(this.nickname)
                .phone(this.phone)
                .address(getAddress())
                .email(this.email)
                .joinType(BOOKSHELF)
                .build();
    }

    public Address getAddress() {
        return this.zipcode == null ? null : new Address(this.city, this.street, this.zipcode);
    }
}
