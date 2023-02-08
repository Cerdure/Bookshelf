package com.cerdure.bookshelf.service.interfaces;

import com.cerdure.bookshelf.dto.member.NewAddressDto;
import com.cerdure.bookshelf.dto.member.NewNamesDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MemberInfoUpdateService {
   Boolean memberPhoneCheck(String phoneNumber);
   String makeNum(String phoneNumber);
   String changeNumber(String originNum,String newNum);
   Boolean memberEmailChange(String email, String phone);
   NewNamesDto memberChangeNames(String name, String nickName, String phone);
   Boolean memberChangeAddress(NewAddressDto newAddressDto,String Phone);
   Boolean memberPasswordChange(String newPassword,String Phone);
   Boolean memberDelete(String phone);
   String memberProfileChange(MultipartFile multipartFile, String phone) throws IOException;
   String memberChangebasic(String phone);
}
