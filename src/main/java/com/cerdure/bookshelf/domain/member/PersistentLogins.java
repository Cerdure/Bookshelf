package com.cerdure.bookshelf.domain.member;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class PersistentLogins {
    @Id
    private String series;
    private String username;
    private String token;
    private LocalDateTime lastUsed;
}