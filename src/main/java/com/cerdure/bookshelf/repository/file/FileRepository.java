package com.cerdure.bookshelf.repository.file;

import com.cerdure.bookshelf.domain.file.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<UploadFile, Long> {
    public List<UploadFile> findAllByReviewId(Long reviewId);
}
