package com.cerdure.bookshelf.domain.member;

import com.cerdure.bookshelf.domain.book.Bookmark;
import com.cerdure.bookshelf.domain.enums.MemberGrade;
import com.cerdure.bookshelf.domain.enums.MemberJoinType;
import com.cerdure.bookshelf.domain.enums.MemberRole;
import com.cerdure.bookshelf.domain.event.EventState;
import com.cerdure.bookshelf.domain.order.Cart;
import com.cerdure.bookshelf.domain.order.Orders;
import com.cerdure.bookshelf.dto.member.MemberDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.cerdure.bookshelf.domain.enums.MemberGrade.NEW;
import static com.cerdure.bookshelf.domain.enums.MemberRole.USER;
import static java.util.Optional.ofNullable;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class Member implements UserDetails {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String pw;
    private String name;
    private String nickname;
    private String phone;
    private String email;
    private Integer point;
    private Integer atdCount;
    private String profilePath;
    private LocalDate regDate;
    private String delPhone;
    private LocalDateTime delDate;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private MemberGrade grade;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    private MemberJoinType joinType;

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    @JsonIgnore
    private List<Cart> carts;

    @OneToMany(mappedBy = "orderer", orphanRemoval = true)
    @JsonIgnore
    private List<Orders> ordersList;

    @OneToOne(mappedBy = "member", orphanRemoval = true)
    @JsonIgnore
    private EventState eventState;

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    @JsonIgnore
    private List<MemberCoupon> memberCoupons;

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    @JsonIgnore
    private List<Bookmark> bookmarks;

    @PrePersist
    @PreUpdate
    private void prePersist() {
        this.role = ofNullable(this.role).orElse(USER);
        this.grade = ofNullable(this.grade).orElse(NEW);
        this.point = ofNullable(this.point).orElse(0);
        this.atdCount = ofNullable(this.atdCount).orElse(0);
        this.regDate = ofNullable(this.regDate).orElse(LocalDate.now());
        this.profilePath = ofNullable(this.profilePath).orElse("/img/icon/default_profile.png");
    }

    public void update(MemberDto memberDto, PasswordEncoder passwordEncoder) {
        if (memberDto.getPw() != null) this.pw = passwordEncoder.encode(memberDto.getPw());
        this.phone = ofNullable(memberDto.getPhone()).orElse(this.phone);
        this.name = ofNullable(memberDto.getName()).orElse(this.name);
        this.nickname = ofNullable(memberDto.getNickname()).orElse(this.nickname);
        this.email = ofNullable(memberDto.getEmail()).orElse(this.email);
        this.address = ofNullable(memberDto.getAddress()).orElse(this.address);
        this.profilePath = ofNullable(memberDto.getProfilePath()).orElse(this.profilePath);
    }

    public void changeProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public void changePoint(int point) {
        this.point = point;
    }

    public void changeGrade(MemberGrade grade) {
        this.grade = grade;
    }

    public void changeAtdCount(int atdCount) {
        this.atdCount = atdCount;
    }

    public void leave() {
        this.delPhone = this.phone;
        this.phone = null;
        this.delDate = LocalDateTime.now();
    }

    public Member apiJoin(String phone, Address address, String pw, String name) {
        this.phone = phone;
        this.address = address;
        this.pw = pw;
        this.name = name;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.toString()));
        return authorities;
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
}
