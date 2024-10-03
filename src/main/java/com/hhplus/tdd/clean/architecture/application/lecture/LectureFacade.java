package com.hhplus.tdd.clean.architecture.application.lecture;

import com.hhplus.tdd.clean.architecture.domain.lecture.LectureCommandService;
import com.hhplus.tdd.clean.architecture.domain.lecture.LectureQueryService;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureCommand;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureEnrollment;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureQuery;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureQuery.FindAvailableLectureSchedulesByDate;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureSchedule;
import com.hhplus.tdd.clean.architecture.domain.user.UserQueryService;
import com.hhplus.tdd.clean.architecture.domain.user.dto.UserQuery;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class LectureFacade {

  private final LectureQueryService lectureQueryService;
  private final LectureCommandService lectureCommandService;
  private final UserQueryService userQueryService;

  // TODO: 최대 수강 인원 체크 구현 (STEP 3)
  // TODO: 동일 특강 중복 등록 체크 구현 (STEP 4)
  @Transactional
  public LectureEnrollment enrollLecture(Long lectureId, Long lectureScheduleId, Long userId) {
    final var user = userQueryService.getUserById(new UserQuery.GetUserById(userId));
    final var lecture = lectureQueryService.getLectureById(
        new LectureQuery.GetLectureById(lectureId));
    final var lectureSchedule = lectureQueryService.getLectureScheduleById(
        new LectureQuery.GetLectureScheduleById(lectureScheduleId));

    final Long enrollmentId = lectureCommandService.createLectureEnrollment(
        new LectureCommand.CreateLectureEnrollment(lecture.id(), lectureSchedule.id(), user.id()));

    lectureCommandService.increaseEnrollmentCountByLectureScheduleId(lectureSchedule.id());

    return lectureQueryService.getLectureEnrollmentById(
        new LectureQuery.GetLectureEnrollmentById(enrollmentId));
  }

  public List<LectureSchedule> getAvailableLectureSchedules(LocalDate date) {
    return lectureQueryService.findAvailableLectureSchedules(
        new FindAvailableLectureSchedulesByDate(date));
  }
}
