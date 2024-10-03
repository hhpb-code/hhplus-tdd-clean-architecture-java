package com.hhplus.tdd.clean.architecture.application.lecture;

import com.hhplus.tdd.clean.architecture.domain.common.error.BusinessException;
import com.hhplus.tdd.clean.architecture.domain.lecture.LectureCommandService;
import com.hhplus.tdd.clean.architecture.domain.lecture.LectureErrorCode;
import com.hhplus.tdd.clean.architecture.domain.lecture.LectureQueryService;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.Lecture;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureCommand;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureEnrollment;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureQuery;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureQuery.FindAvailableLectureSchedulesByDate;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureQuery.FindLecturesByIds;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureSchedule;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureWithLecturer;
import com.hhplus.tdd.clean.architecture.domain.user.User;
import com.hhplus.tdd.clean.architecture.domain.user.UserQueryService;
import com.hhplus.tdd.clean.architecture.domain.user.dto.UserQuery;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class LectureFacade {

  private final LectureQueryService lectureQueryService;
  private final LectureCommandService lectureCommandService;
  private final UserQueryService userQueryService;

  @Transactional
  public LectureEnrollment enrollLecture(Long lectureId, Long lectureScheduleId, Long userId) {
    final var user = userQueryService.getUserById(new UserQuery.GetUserById(userId));
    final var lecture = lectureQueryService.getLectureById(
        new LectureQuery.GetLectureById(lectureId));
    final var lectureSchedule = lectureQueryService.getLectureScheduleById(
        new LectureQuery.GetLectureScheduleById(lectureScheduleId, true));

    if (lectureSchedule.enrolledCount() + 1 > lectureSchedule.capacity()) {
      throw new BusinessException(LectureErrorCode.ENROLLMENT_EXCEED_CAPACITY);
    }

    final Long enrollmentId = lectureCommandService.createLectureEnrollment(
        new LectureCommand.CreateLectureEnrollment(lecture.id(), lectureSchedule.id(), user.id()));

    lectureCommandService.increaseEnrollmentCountByLectureScheduleId(lectureSchedule.id());

    return lectureQueryService.getLectureEnrollmentById(
        new LectureQuery.GetLectureEnrollmentById(enrollmentId));
  }

  public List<LectureWithLecturer> getEnrolledLectures(Long userId) {
    final var user = userQueryService.getUserById(new UserQuery.GetUserById(userId));

    final var enrolledLectures = lectureQueryService.findLectureEnrollments(
        new LectureQuery.FindLectureEnrollmentsByUserId(user.id()));

    final var lectureIds = enrolledLectures.stream()
        .map(LectureEnrollment::lectureId)
        .toList();
    final var lectures = lectureQueryService.findLecturesByIds(new FindLecturesByIds(lectureIds));

    final var lecturerIds = lectures.stream()
        .map(Lecture::lecturerId)
        .toList();

    final var lecturers = userQueryService.findUsers(
        new UserQuery.FindUsersByIds(lecturerIds));

    Map<Long, User> lecturerMap = lecturers.stream()
        .collect(Collectors.toMap(User::id, Function.identity()));

    return lectures.stream()
        .map(lecture -> {
          User lecturer = lecturerMap.get(lecture.lecturerId());

          return new LectureWithLecturer(lecture, lecturer);
        })
        .toList();
  }

  public List<LectureSchedule> getAvailableLectureSchedules(LocalDate date) {
    return lectureQueryService.findAvailableLectureSchedules(
        new FindAvailableLectureSchedulesByDate(date));
  }
}
