package com.cerdure.bookshelf.dto.loginApi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoogleTokenDto {
    private String code ;
    private String client_id ;
    private String client_secret;
    private String redirect_uri;
    private String grant_type;
}
