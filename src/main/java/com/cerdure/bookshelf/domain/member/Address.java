package com.cerdure.bookshelf.domain.member;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
    private String city;
    private String street;
    private String zipcode;
}