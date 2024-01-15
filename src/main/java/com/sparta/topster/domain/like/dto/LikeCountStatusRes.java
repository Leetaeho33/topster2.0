package com.sparta.topster.domain.like.dto;

import lombok.Getter;

@Getter
public class LikeCountStatusRes {

  private Long id;
  private Long likeCount;
  private boolean status;  // 좋아요 눌렀는지 여부

  public LikeCountStatusRes(Long topsterId, Long likeCount, boolean isLiked) {
    this.id = topsterId;
    this.likeCount = likeCount;
    this.status = isLiked;
  }
}
