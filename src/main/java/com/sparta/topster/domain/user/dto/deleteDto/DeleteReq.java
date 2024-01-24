package com.sparta.topster.domain.user.dto.deleteDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeleteReq {

    String password;

    @Builder
    public DeleteReq(String password){
        this.password = password;
    }
}
