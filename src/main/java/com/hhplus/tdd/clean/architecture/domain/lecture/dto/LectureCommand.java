package com.hhplus.tdd.clean.architecture.domain.lecture.dto;

import com.hhplus.tdd.clean.architecture.domain.common.error.BusinessException;
import com.hhplus.tdd.clean.architecture.domain.lecture.LectureErrorCode;

public class LectureCommand {

  public record CreateLectureEnrollment(
      Long lectureId,
      Long lectureScheduleId,
      Long userId
  ) {

    public CreateLectureEnrollment {
      if (lectureId == null || lectureId <= 0) {
        throw new BusinessException(LectureErrorCode.INVALID_LECTURE_ID);
      }

      if (lectureScheduleId == null || lectureScheduleId <= 0) {
        throw new BusinessException(LectureErrorCode.INVALID_LECTURE_SCHEDULE_ID);
      }

      if (userId == null || userId <= 0) {
        throw new BusinessException(LectureErrorCode.INVALID_USER_ID);
      }
    }

  }
}
