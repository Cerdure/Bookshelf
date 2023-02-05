package com.cerdure.bookshelf.service;

import com.cerdure.bookshelf.domain.book.Book;
import com.cerdure.bookshelf.domain.book.Bookmark;
import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.dto.BookmarkDto;
import com.cerdure.bookshelf.repository.BookRepository;
import com.cerdure.bookshelf.repository.BookmarkRepository;
import com.cerdure.bookshelf.service.interfaces.BookmarkService;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final BookRepository bookRepository;
    @Override
    public BookmarkDto marking(String bookName, Member member) {
        Book book = bookRepository.findByName(bookName);
        if(bookmarkRepository.findByMemberAndBook(member,book)==null){
            Bookmark bookmark = Bookmark.builder()
                    .book(book)
                    .member(member)
                    .build();
            bookmarkRepository.save(bookmark);
            Book newTotal = book.addTotalMark(1);
            Book savedTotalMark = bookRepository.save(newTotal);
            return getBookMarkDto(savedTotalMark, 1);
        }else{
            bookmarkRepository.deleteByMemberAndBook(member,book);
            Book newTotal = book.minusTotalMark(1);
            return getBookMarkDto(newTotal, 0);
        }
    }

    private static BookmarkDto getBookMarkDto(Book savedTotalMark, int memberBookmark) {
        return BookmarkDto.builder()
                .totalMark(savedTotalMark.getTotalBookMark())
                .memberBookmark(memberBookmark)
                .build();
    }

}
