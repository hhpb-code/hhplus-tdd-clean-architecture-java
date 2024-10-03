package com.hhplus.tdd.clean.architecture.domain.lecture;

import com.hhplus.tdd.clean.architecture.domain.common.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum LectureErrorCode implements ErrorCode {
  INVALID_LECTURE_ID(HttpStatus.BAD_REQUEST, "유요하지 않은 강의 ID 입니다."),
  INVALID_LECTURE_SCHEDULE_ID(HttpStatus.BAD_REQUEST, "유요하지 않은 강의 스케줄 ID 입니다."),
  INVALID_LECTURE_ENROLLMENT_ID(HttpStatus.BAD_REQUEST, "유요하지 않은 강의 등록 ID 입니다."),
  INVALID_USER_ID(HttpStatus.BAD_REQUEST, "유요하지 않은 사용자 ID 입니다."),
  INVALID_DATE(HttpStatus.BAD_REQUEST, "유요하지 않은 날짜 입니다."),
  INVALID_LECTURE_IDS(HttpStatus.BAD_REQUEST, "유요하지 않은 강의 ID 목록 입니다."),
  LECTURE_NOT_FOUND(HttpStatus.NOT_FOUND, "강의를 찾을 수 없습니다."),
  LECTURE_SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "강의 스케줄을 찾을 수 없습니다."),
  LECTURE_ENROLLMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "강의 등록을 찾을 수 없습니다."),
  ;


  private final HttpStatus status;
  private final String message;

  @Override
  public String getCode() {
    return name();
  }

  @Override
  public HttpStatus getStatus() {
    return status;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
