package com.cerdure.bookshelf.dto.member;


import com.cerdure.bookshelf.domain.order.Cart;
import com.cerdure.bookshelf.domain.enums.MemberGrade;
import com.cerdure.bookshelf.domain.enums.MemberRole;
import com.cerdure.bookshelf.domain.member.Address;
import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.domain.order.Orders;
import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class MemberDto {

    private Long id;
    private String pw;
    private String name;
    private String nickname;
    private String phone;
    private String zipcode;
    private String city;
    private Address address;
    private String street;

    @Enumerated(EnumType.STRING)
    private MemberGrade grade;

    private Integer point;
    private LocalDate regDate;
    private Integer delflag;
    private LocalDate delDate;
    private List<Orders> orders = new ArrayList<>();

    @Builder
    public MemberDto(Long id, String pw, String name, String nickname, String phone, String zipcode, String city, Address address, String street, MemberGrade grade, Integer point, LocalDate regDate, Integer delflag, LocalDate delDate, List<Orders> orders) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
        this.zipcode = zipcode;
        this.city = city;
        this.address = address;
        this.street = street;
        this.grade = grade;
        this.point = point;
        this.regDate = regDate;
        this.delflag = delflag;
        this.delDate = delDate;
        this.orders = orders;
    }

    public Member createMember(PasswordEncoder passwordEncoder){
        return Member.builder()
                .pw(passwordEncoder.encode(this.pw))
                .name(this.name)
                .nickname(this.nickname)
                .phone(this.phone)
                .address(new Address(this.city, this.street, this.zipcode))
                .grade(this.grade)
                .point(this.point)
                .regDate(this.regDate)
                .delflag(this.delflag)
                .delDate(this.delDate)
                .role(MemberRole.USER)
                .build();
    }
}
