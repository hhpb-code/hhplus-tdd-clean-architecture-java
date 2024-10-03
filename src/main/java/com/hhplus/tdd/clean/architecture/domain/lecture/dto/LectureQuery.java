package com.hhplus.tdd.clean.architecture.domain.lecture.dto;

import com.hhplus.tdd.clean.architecture.domain.common.error.BusinessException;
import com.hhplus.tdd.clean.architecture.domain.lecture.LectureErrorCode;
import java.time.LocalDate;
import java.util.List;

public class LectureQuery {

  public record GetLectureById(Long lectureId) {

    public GetLectureById {
      if (lectureId == null || lectureId <= 0) {
        throw new BusinessException(LectureErrorCode.INVALID_LECTURE_ID);
      }
    }
  }

  public record GetLectureScheduleById(Long lectureScheduleId, Boolean forUpdate) {

    public GetLectureScheduleById(Long lectureScheduleId, Boolean forUpdate) {
      if (lectureScheduleId == null || lectureScheduleId <= 0) {
        throw new BusinessException(LectureErrorCode.INVALID_LECTURE_SCHEDULE_ID);
      }

      this.lectureScheduleId = lectureScheduleId;
      this.forUpdate = forUpdate.equals(Boolean.TRUE);
    }

    public GetLectureScheduleById(Long lectureScheduleId) {
      this(lectureScheduleId, false);
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

  public record FindLectureEnrollmentsByUserId(Long userId) {

    public FindLectureEnrollmentsByUserId {
      if (userId == null || userId <= 0) {
        throw new BusinessException(LectureErrorCode.INVALID_USER_ID);
      }
    }
  }

  public record FindLecturesByIds(List<Long> lectureIds) {

    public FindLecturesByIds {
      if (lectureIds == null || lectureIds.isEmpty() || lectureIds.stream()
          .anyMatch(id -> id == null || id <= 0)) {
        throw new BusinessException(LectureErrorCode.INVALID_LECTURE_IDS);
      }
    }
  }

  public record FindLectureEnrollmentByLectureScheduleIdAndUserId(Long lectureScheduleId,
                                                                  Long userId) {

    public FindLectureEnrollmentByLectureScheduleIdAndUserId {
      if (lectureScheduleId == null || lectureScheduleId <= 0) {
        throw new BusinessException(LectureErrorCode.INVALID_LECTURE_SCHEDULE_ID);
      }

      if (userId == null || userId <= 0) {
        throw new BusinessException(LectureErrorCode.INVALID_USER_ID);
      }
    }
  }

}
