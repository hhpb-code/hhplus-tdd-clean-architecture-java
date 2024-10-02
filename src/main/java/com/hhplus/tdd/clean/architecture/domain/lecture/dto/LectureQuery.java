package com.hhplus.tdd.clean.architecture.domain.lecture.dto;

import com.hhplus.tdd.clean.architecture.domain.common.error.BusinessException;
import com.hhplus.tdd.clean.architecture.domain.lecture.LectureErrorCode;

public class LectureQuery {

  public record GetLectureById(Long lectureId) {

    public GetLectureById {
      if (lectureId == null || lectureId <= 0) {
        throw new BusinessException(LectureErrorCode.INVALID_LECTURE_ID);
      }
    }
  }

}
