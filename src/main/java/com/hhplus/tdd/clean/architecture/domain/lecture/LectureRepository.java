package com.hhplus.tdd.clean.architecture.domain.lecture;

import com.hhplus.tdd.clean.architecture.domain.lecture.dto.Lecture;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureEnrollment;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureSchedule;
import java.time.LocalDate;
import java.util.List;

public interface LectureRepository {

  Lecture getLectureById(Long lectureId);

  LectureSchedule getLectureScheduleById(Long lectureScheduleId);

  List<LectureSchedule> findAvailableLectureSchedulesByDate(LocalDate date);

  LectureEnrollment getLectureEnrollmentById(Long enrollmentId);

  List<LectureEnrollment> findLectureEnrollmentsByUserId(Long userId);

  Long createLectureEnrollment(Long lectureId, Long lectureScheduleId, Long userId);

  void increaseEnrollmentCountByLectureScheduleId(Long lectureScheduleId);

}
