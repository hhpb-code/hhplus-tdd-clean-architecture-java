package com.hhplus.tdd.clean.architecture.application.lecture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import com.hhplus.tdd.clean.architecture.domain.lecture.LectureCommandService;
import com.hhplus.tdd.clean.architecture.domain.lecture.LectureQueryService;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.Lecture;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureCommand;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureEnrollment;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureQuery;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureQuery.FindAvailableLectureSchedulesByDate;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureQuery.FindLectureEnrollmentsByUserId;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureSchedule;
import com.hhplus.tdd.clean.architecture.domain.user.User;
import com.hhplus.tdd.clean.architecture.domain.user.UserQueryService;
import com.hhplus.tdd.clean.architecture.domain.user.dto.UserQuery;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("LectureFacade 단위 테스트")
class LectureFacadeTest {

  @InjectMocks
  private LectureFacade target;

  @Mock
  private LectureQueryService lectureQueryService;

  @Mock
  private LectureCommandService lectureCommandService;

  @Mock
  private UserQueryService userQueryService;

  @Test
  @DisplayName("enrollLecture 테스트 성공")
  void shouldSuccessfullyEnrollLecture() {
    // given
    final Long userId = 1L;
    final Long lectureId = 1L;
    final Long lectureScheduleId = 1L;
    final Long lectureEnrollmentId = 1L;
    final User user = new User(userId, "name", null, null);
    final Lecture lecture = new Lecture(lectureId, "title", "description", 2L, null, null);
    final LectureSchedule lectureSchedule = new LectureSchedule(lectureScheduleId, lectureId, 30, 0,
        null, null, null, null);
    final LectureEnrollment lectureEnrollment = new LectureEnrollment(lectureEnrollmentId,
        lectureId, lectureScheduleId, userId, null, null);

    doReturn(user).when(userQueryService).getUserById(new UserQuery.GetUserById(userId));
    doReturn(lecture).when(lectureQueryService)
        .getLectureById(new LectureQuery.GetLectureById(lectureId));
    doReturn(lectureSchedule).when(lectureQueryService).getLectureScheduleById(
        new LectureQuery.GetLectureScheduleById(lectureScheduleId));
    doReturn(lectureEnrollmentId).when(lectureCommandService)
        .createLectureEnrollment(
            new LectureCommand.CreateLectureEnrollment(lectureId, lectureScheduleId, userId));
    doReturn(lectureEnrollment).when(lectureQueryService)
        .getLectureEnrollmentById(new LectureQuery.GetLectureEnrollmentById(lectureEnrollmentId));

    // when
    final LectureEnrollment result = target.enrollLecture(lectureId, lectureScheduleId, userId);

    // then
    assertThat(result.id()).isEqualTo(lectureEnrollmentId);
    assertThat(result.lectureId()).isEqualTo(lectureId);
    assertThat(result.lectureScheduleId()).isEqualTo(lectureScheduleId);
    assertThat(result.userId()).isEqualTo(userId);
    assertThat(result.createdAt()).isEqualTo(lectureEnrollment.createdAt());
    assertThat(result.updatedAt()).isEqualTo(lectureEnrollment.updatedAt());
  }

  @Test
  @DisplayName("getAvailableLectureSchedules 테스트 성공")
  void shouldSuccessfullyGetAvailableLectureSchedules() {
    // given
    final LocalDateTime now = LocalDateTime.now();
    final var lectureSchedules = List.of(
        new LectureSchedule(1L, 1L, 30, 0, now, now, now, null),
        new LectureSchedule(2L, 2L, 10, 5, now, now, now, null)
    );
    doReturn(lectureSchedules).when(lectureQueryService)
        .findAvailableLectureSchedules(
            new FindAvailableLectureSchedulesByDate(now.toLocalDate()));

    // when
    final var result = target.getAvailableLectureSchedules(now.toLocalDate());

    // then
    assertThat(result).hasSize(2);
    for (int i = 0; i < result.size(); i++) {
      final var lectureSchedule = result.get(i);
      final var expected = lectureSchedules.get(i);

      assertThat(lectureSchedule.id()).isEqualTo(expected.id());
      assertThat(lectureSchedule.lectureId()).isEqualTo(expected.lectureId());
      assertThat(lectureSchedule.capacity()).isEqualTo(expected.capacity());
      assertThat(lectureSchedule.enrolledCount()).isEqualTo(expected.enrolledCount());
      assertThat(lectureSchedule.startAt()).isEqualTo(expected.startAt());
      assertThat(lectureSchedule.endAt()).isEqualTo(expected.endAt());
      assertThat(lectureSchedule.createdAt()).isEqualTo(expected.createdAt());
      assertThat(lectureSchedule.updatedAt()).isEqualTo(expected.updatedAt());
    }
  }

  @Test
  @DisplayName("getEnrolledLectures 테스트 성공")
  void shouldSuccessfullyGetLectureEnrollments() {
    // given
    final Long userId = 1L;
    final User user = new User(userId, "name", null, null);
    final List<LectureEnrollment> lectureEnrollments = List.of(
        new LectureEnrollment(1L, 2L, 1L, userId, null, null),
        new LectureEnrollment(2L, 3L, 2L, userId, null, null)
    );
    final List<Lecture> lectures = List.of(
        new Lecture(1L, "title1", "description1", 2L, null, null),
        new Lecture(2L, "title2", "description2", 3L, null, null)
    );
    final List<User> lecturers = List.of(
        new User(2L, "lecturer1", null, null),
        new User(3L, "lecturer2", null, null)
    );
    final List<Long> lectureIds = lecturers.stream().map(User::id).toList();
    final List<Long> lecturerIds = lecturers.stream().map(User::id).toList();

    doReturn(user).when(userQueryService).getUserById(new UserQuery.GetUserById(userId));
    doReturn(lectureEnrollments).when(lectureQueryService)
        .findLectureEnrollments(new FindLectureEnrollmentsByUserId(userId));
    doReturn(lectures).when(lectureQueryService)
        .findLecturesByIds(new LectureQuery.FindLecturesByIds(lectureIds));
    doReturn(lecturers).when(userQueryService)
        .findUsers(new UserQuery.FindUsersByIds(lecturerIds));

    // when
    final var result = target.getEnrolledLectures(userId);

    // then
    assertThat(result).hasSize(2);
    for (int i = 0; i < result.size(); i++) {
      final var lectureWithLecturer = result.get(i);
      final var lecture = lectures.get(i);
      final var lecturer = lecturers.get(i);

      assertThat(lectureWithLecturer.id()).isEqualTo(lecture.id());
      assertThat(lectureWithLecturer.title()).isEqualTo(lecture.title());
      assertThat(lectureWithLecturer.description()).isEqualTo(lecture.description());
      assertThat(lectureWithLecturer.createdAt()).isEqualTo(lecture.createdAt());
      assertThat(lectureWithLecturer.updatedAt()).isEqualTo(lecture.updatedAt());
      assertThat(lectureWithLecturer.lecturer().id()).isEqualTo(lecturer.id());
      assertThat(lectureWithLecturer.lecturer().name()).isEqualTo(lecturer.name());
      assertThat(lectureWithLecturer.lecturer().createdAt()).isEqualTo(lecturer.createdAt());
      assertThat(lectureWithLecturer.lecturer().updatedAt()).isEqualTo(lecturer.updatedAt());
    }
  }

}