package com.cerdure.bookshelf.service.classes.file.interfaces;

import com.cerdure.bookshelf.dto.board.ReviewDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadFileService {
    String getFullPath(String name);
    void fileSaveResolver(ReviewDto reviewDto, Long reviewId) throws IOException;
    void saveFile(Long reviewId, MultipartFile file) throws IOException;
    String saveFile(MultipartFile file) throws IOException;
    private String createStoreFileName(String originalFilename) {
        return null;
    }
    private String extractExt(String originalFilename) {
        return null;
    }
    void deleteFilesByReviewId(Long reviewId, ReviewDto reviewDto);
}
