package com.cerdure.bookshelf.service.classes.file;

import com.cerdure.bookshelf.domain.board.Review;
import com.cerdure.bookshelf.domain.file.UploadFile;
import com.cerdure.bookshelf.dto.board.ReviewDto;
import com.cerdure.bookshelf.repository.board.ReviewRepository;
import com.cerdure.bookshelf.repository.file.FileRepository;
import com.cerdure.bookshelf.service.classes.file.interfaces.UploadFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadFileServiceImpl implements UploadFileService {

    private final FileRepository fileRepository;
    private final ReviewRepository reviewRepository;
    private final String fileDir = System.getProperty("user.dir") + "/src/main/resources/static/upload-img/";

    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }

    public String getSimplePath(String fileName){
        return "/upload-img/" + fileName;
    }

    @Override
    public void fileSaveResolver(ReviewDto reviewDto, Long reviewId) throws IOException {
        if (reviewDto.getImageFiles().get(0).getOriginalFilename() == "") return;
        for (MultipartFile file : reviewDto.getImageFiles()) {
            saveFile(reviewId, file);
        }
    }

    @Override
    public void saveFile(Long reviewId, MultipartFile file) throws IOException {
        String storeFileName = createStoreFileName(file.getOriginalFilename());
        Review review = reviewRepository.findById(reviewId).get();

        UploadFile uploadFile = UploadFile.builder()
                .review(review)
                .originalFilename(file.getOriginalFilename())
                .storeFileName(storeFileName)
                .build();

        fileRepository.save(uploadFile);
        file.transferTo(new File(getFullPath(storeFileName)));
    }

    @Override
    public String saveFile(MultipartFile file) throws IOException {
        String storeFileName = createStoreFileName(file.getOriginalFilename());
        UploadFile uploadFile = UploadFile.builder()
                .originalFilename(file.getOriginalFilename())
                .storeFileName(storeFileName)
                .build();

        fileRepository.save(uploadFile);
        file.transferTo(new File(getFullPath(storeFileName)));
        return getSimplePath(storeFileName);
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        if (ext.equals("")) {
            return null;
        } else {
            String uuid = UUID.randomUUID().toString();
            return uuid + "." + ext;
        }
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    @Override
    public void deleteFilesByReviewId(Long reviewId, ReviewDto reviewDto) {
        if (reviewDto.getImageFiles().get(0).getOriginalFilename() == "") return;
        List<UploadFile> uploadFiles = fileRepository.findAllByReviewId(reviewId);
        uploadFiles.forEach(uploadFile -> new File(uploadFile.getFullPath()).delete());
        fileRepository.deleteAll(uploadFiles);
    }
}
