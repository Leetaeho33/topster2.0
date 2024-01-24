package com.sparta.topster.domain.topster.dto.res;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class TopsterPageRes {
    List<TopsterGetRes> data;
    long totalElement;
    long totalPage;
    int currentPage;
    int size;

    @Builder
    public TopsterPageRes(List<TopsterGetRes> data, long totalElement, long totalPage, int currentPage, int size) {
        this.data = data;
        this.totalElement = totalElement;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
        this.size = size;
    }
}
