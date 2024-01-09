package com.sparta.topster.domain.post.dto.request;

import lombok.Getter;

@Getter
public class PostSortReq {

    private String sortBy;
    private Boolean asc;

    public PostSortReq(String sortBy, Boolean asc) {
        this.sortBy = sortBy;
        this.asc = asc == null ? false : true;
    }
}
