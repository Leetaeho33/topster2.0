package com.sparta.topster.domain.post.dto.request;

import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Getter
public class PostPageReq {

    private Integer page;
    private Integer max;

    public Pageable toPageable() {
        page = page == null ? 1 : page;
        max = max == null ? 10 : max;
        return PageRequest.of(page - 1, max);
    }

}
