package com.hhplus.tdd.clean.architecture.domain.lecture;

import com.hhplus.tdd.clean.architecture.domain.lecture.dto.Lecture;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureSchedule;

public interface LectureRepository {

  Lecture getLectureById(Long lectureId);

  LectureSchedule getLectureScheduleById(Long lectureScheduleId);

  Long createLectureEnrollment(Long lectureId, Long lectureScheduleId, Long userId);

  void increaseEnrollmentCountByLectureScheduleId(Long lectureScheduleId);
}
