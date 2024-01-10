package com.sparta.topster.domain.comment.exception;

import com.sparta.topster.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
@Getter
@RequiredArgsConstructor
public enum CommentException implements ErrorCode {

  NO_COMMENT(HttpStatus.BAD_REQUEST, "T1001", "댓글이 존재하지 않습니다."),
  MODIFY_AND_DELETE_ONLY_AUTHOR(HttpStatus.BAD_REQUEST, "T1002", "작성자만 수정 및 삭제 할 수 있습니다.")
  ;

  private final HttpStatus Status;
  private final String code;
  private final String message;

  @Override
  public String getCode() {
    return null;
  }

  @Override
  public HttpStatus getStatus() {
    return null;
  }

  @Override
  public String getMessage() {
    return null;
  }
}
