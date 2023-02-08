package com.cerdure.bookshelf.service.interfaces;

import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.dto.BookmarkDto;

public interface BookmarkService {
    BookmarkDto marking(String bookName, Member member);
}
