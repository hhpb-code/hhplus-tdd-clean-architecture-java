package com.hhplus.tdd.clean.architecture.domain.lecture.dto;

import com.hhplus.tdd.clean.architecture.domain.common.error.BusinessException;
import com.hhplus.tdd.clean.architecture.domain.lecture.LectureErrorCode;
import java.time.LocalDate;

public class LectureQuery {

  public record GetLectureById(Long lectureId) {

    public GetLectureById {
      if (lectureId == null || lectureId <= 0) {
        throw new BusinessException(LectureErrorCode.INVALID_LECTURE_ID);
      }
    }
  }

  public record GetLectureScheduleById(Long lectureScheduleId) {

    public GetLectureScheduleById {
      if (lectureScheduleId == null || lectureScheduleId <= 0) {
        throw new BusinessException(LectureErrorCode.INVALID_LECTURE_SCHEDULE_ID);
      }
    }
  }

  public record FindAvailableLectureSchedulesByDate(LocalDate date) {

    public FindAvailableLectureSchedulesByDate {
      if (date == null || date.isBefore(LocalDate.now())) {
        throw new BusinessException(LectureErrorCode.INVALID_DATE);
      }
    }
  }

  public record GetLectureEnrollmentById(Long lectureEnrollmentId) {

    public GetLectureEnrollmentById {
      if (lectureEnrollmentId == null || lectureEnrollmentId <= 0) {
        throw new BusinessException(LectureErrorCode.INVALID_LECTURE_ENROLLMENT_ID);
      }
    }
  }

}
