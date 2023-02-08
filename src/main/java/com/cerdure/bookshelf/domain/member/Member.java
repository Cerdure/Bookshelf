package com.cerdure.bookshelf.domain.member;

import com.cerdure.bookshelf.domain.enums.MemberJoinType;
import com.cerdure.bookshelf.domain.order.Cart;
import com.cerdure.bookshelf.domain.enums.MemberGrade;
import com.cerdure.bookshelf.domain.enums.MemberRole;
import com.cerdure.bookshelf.domain.order.Orders;
import com.cerdure.bookshelf.dto.member.NewAddressDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member implements UserDetails {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String pw;

    private String name;

    private String nickname;

    private String phone;

    private String email;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private MemberGrade grade;

    private Integer point;

    private LocalDate regDate = LocalDate.now();

    private Integer delflag;

    private LocalDate delDate;

    @OneToOne(fetch = LAZY, orphanRemoval = true)
    private MemberProfile memberProfile;
    
    @OneToMany(mappedBy = "member", orphanRemoval = true)
    @JsonIgnore
    private List<Cart> carts;

    @OneToMany(mappedBy = "orderer", orphanRemoval = true)
    @JsonIgnore
    private List<Orders> ordersList;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    private MemberJoinType memberJoinType;

    @OneToOne(mappedBy = "member", orphanRemoval = true)
    @JsonIgnore
    private EventState eventState;

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    @JsonIgnore
    private List<MemberCoupon> memberCoupons;

    @PrePersist
    public void prePersist() {
        this.grade = this.grade == null ? MemberGrade.NEW : this.grade;
        this.delflag = this.delflag == null ? 0 : this.delflag;
        this.point = this.point == null ? 0 : this.point;
        this.regDate = this.regDate == null ? LocalDate.now() : this.regDate;
    }


    public void changePoint(int point){
        this.point = point;
    }

    public void changeGrade(MemberGrade grade){
        this.grade = grade;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(role.toString()));
        return authorities;
    }

    public Member apiJoin(String phone,Address address,String pw,String name){
        this.phone=phone;
        this.address=address;
        this.pw=pw;
        this.name=name;
        return this;
    }
    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public Member changePhoneNumber(String phone) {
        this.phone=phone;
        return this;
    }
    public Member changeEmail(String email) {
        this.email=email;
        return this;
    }
    public Member changeNames(String newName, String newNickName) {
        this.name=newName;
        this.nickname=newNickName;
        return this;
    }

    public Address changeAddress(Address address) {
        this.address=address;

        return this.address;
    }

    public String changePassword(String newPassword) {
        this.pw=newPassword;
        return  this.pw;
    }
}
