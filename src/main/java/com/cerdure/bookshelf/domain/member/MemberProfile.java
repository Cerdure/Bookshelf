package com.cerdure.bookshelf.domain.member;

import lombok.*;
import org.apache.logging.log4j.message.LoggerNameAwareMessage;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberProfile {
    @GeneratedValue @Id
    @Column(name = "memberProfile_id")
    private Long id;
    private String profileDir;
    private String originalProfileName;
    private String storeProfileName;
    private String profileFullPath;

    @PrePersist
    public void prePersist() {
        this.profileDir = this.profileDir == null ? "/upload-img/" : this.profileDir;
        this.originalProfileName = this.originalProfileName == null ? this.storeProfileName : this.originalProfileName;
        this.profileFullPath = this.profileFullPath == null ? this.profileDir + this.storeProfileName : this.profileFullPath;
    }

    public MemberProfile changeProfile(String storeFileName, String originalFilename, String fullPath) {
        this.originalProfileName=originalFilename;
        this.profileFullPath=fullPath;
        this.storeProfileName=storeFileName;
        return this;
    }
}
