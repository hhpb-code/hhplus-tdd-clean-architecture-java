package com.hhplus.tdd.clean.architecture.application.lecture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import com.hhplus.tdd.clean.architecture.domain.lecture.LectureCommandService;
import com.hhplus.tdd.clean.architecture.domain.lecture.LectureQueryService;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.Lecture;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureCommand;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureEnrollment;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureQuery;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureSchedule;
import com.hhplus.tdd.clean.architecture.domain.user.User;
import com.hhplus.tdd.clean.architecture.domain.user.UserQueryService;
import com.hhplus.tdd.clean.architecture.domain.user.dto.UserQuery;
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

}