package com.cerdure.bookshelf.domain.file;

import com.cerdure.bookshelf.domain.board.Review;
import lombok.*;

import javax.persistence.*;
import java.util.Optional;

import static java.util.Optional.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadFile {

    @Id
    @GeneratedValue
    @Column(name = "file_id")
    private Long id;
    private String originalFilename;
    private String storeFileName;
    private String fullPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @PrePersist
    private void prePersist() {
        this.originalFilename = ofNullable(this.originalFilename).orElse(this.storeFileName);
        this.fullPath = ofNullable(this.fullPath).orElse("/upload-img/" + this.storeFileName);
    }

    public void changeReview(Review review) {
        this.review = review;
    }

    public void changeStoreFileName(String storeFileName) {
        this.storeFileName = storeFileName;
    }

    public void changeFullPath(String fullPath) {
        this.fullPath = fullPath;
    }
}
