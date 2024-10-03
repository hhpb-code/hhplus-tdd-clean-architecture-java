package com.hhplus.tdd.clean.architecture.domain.lecture;

import com.hhplus.tdd.clean.architecture.domain.lecture.dto.Lecture;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureEnrollment;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureQuery;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureQuery.FindAvailableLectureSchedulesByDate;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureQuery.FindLectureEnrollmentsByUserId;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureQuery.FindLecturesByIds;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureSchedule;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LectureQueryService {

  private final LectureRepository lectureRepository;

  public Lecture getLectureById(LectureQuery.GetLectureById query) {
    return lectureRepository.getLectureById(query.lectureId());
  }

  public LectureSchedule getLectureScheduleById(LectureQuery.GetLectureScheduleById query) {
    return lectureRepository.getLectureScheduleById(query.lectureScheduleId());
  }

  public List<LectureSchedule> findAvailableLectureSchedules(
      FindAvailableLectureSchedulesByDate query) {
    return lectureRepository.findAvailableLectureSchedulesByDate(query.date());
  }

  public LectureEnrollment getLectureEnrollmentById(
      LectureQuery.GetLectureEnrollmentById query) {
    return lectureRepository.getLectureEnrollmentById(query.lectureEnrollmentId());
  }

  public List<LectureEnrollment> findLectureEnrollments(
      FindLectureEnrollmentsByUserId query) {
    return lectureRepository.findLectureEnrollmentsByUserId(query.userId());
  }

  public List<Lecture> findLecturesByIds(FindLecturesByIds query) {
    return lectureRepository.findLecturesByIds(query.lectureIds());
  }
}
