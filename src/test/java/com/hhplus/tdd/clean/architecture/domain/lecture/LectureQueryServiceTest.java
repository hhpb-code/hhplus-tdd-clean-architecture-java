package com.hhplus.tdd.clean.architecture.domain.lecture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import com.hhplus.tdd.clean.architecture.domain.lecture.dto.Lecture;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureEnrollment;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureQuery;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureQuery.FindLectureEnrollmentsByUserId;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureSchedule;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("LectureQueryService 단위 테스트")
class LectureQueryServiceTest {

  @InjectMocks
  private LectureQueryService target;

  @Mock
  private LectureRepository lectureRepository;


  @Test
  @DisplayName("getLectureById 테스트 성공")
  void shouldSuccessfullyGetLectureById() {
    // given
    final Long lectureId = 1L;
    final LectureQuery.GetLectureById query = new LectureQuery.GetLectureById(lectureId);
    final Lecture lecture = new Lecture(lectureId, "title",
        "description", 2L, LocalDateTime.now(),
        null);
    doReturn(lecture).when(lectureRepository).getLectureById(lectureId);

    // when
    final Lecture result = target.getLectureById(query);

    // then
    assertThat(result.id()).isEqualTo(lectureId);
    assertThat(result.title()).isEqualTo(lecture.title());
    assertThat(result.description()).isEqualTo(lecture.description());
    assertThat(result.lecturerId()).isEqualTo(lecture.lecturerId());
    assertThat(result.createdAt()).isEqualTo(lecture.createdAt());
    assertThat(result.updatedAt()).isEqualTo(lecture.updatedAt());
  }

  @Test
  @DisplayName("getLectureScheduleById 테스트 성공")
  void shouldSuccessfullyGetLectureScheduleById() {
    // given
    final Long lectureScheduleId = 1L;
    final LectureQuery.GetLectureScheduleById query = new LectureQuery.GetLectureScheduleById(
        lectureScheduleId);
    final LectureSchedule lectureSchedule = new LectureSchedule(lectureScheduleId, 1L, 30, 0,
        LocalDateTime.now(), LocalDateTime.now(), null, null);
    doReturn(lectureSchedule).when(lectureRepository)
        .getLectureScheduleById(lectureScheduleId, false);

    // when
    final LectureSchedule result = target.getLectureScheduleById(query);

    // then
    assertThat(result.id()).isEqualTo(lectureScheduleId);
    assertThat(result.lectureId()).isEqualTo(lectureSchedule.lectureId());
    assertThat(result.capacity()).isEqualTo(lectureSchedule.capacity());
    assertThat(result.enrolledCount()).isEqualTo(lectureSchedule.enrolledCount());
    assertThat(result.startAt()).isEqualTo(lectureSchedule.startAt());
    assertThat(result.endAt()).isEqualTo(lectureSchedule.endAt());
  }

  @Test
  @DisplayName("findAvailableLectureSchedules 테스트 성공")
  void shouldSuccessfullyFindAvailableLectureSchedules() {
    // given
    final LocalDateTime now = LocalDateTime.now();
    final LectureQuery.FindAvailableLectureSchedulesByDate query = new LectureQuery.FindAvailableLectureSchedulesByDate(
        now.toLocalDate());
    final LectureSchedule lectureSchedule = new LectureSchedule(1L, 1L, 30, 0, now, now, null,
        null);
    doReturn(List.of(lectureSchedule)).when(lectureRepository)
        .findAvailableLectureSchedulesByDate(now.toLocalDate());

    // when
    final List<LectureSchedule> result = target.findAvailableLectureSchedules(query);

    // then
    assertThat(result).hasSize(1);
    assertThat(result.get(0).id()).isEqualTo(lectureSchedule.id());
    assertThat(result.get(0).lectureId()).isEqualTo(lectureSchedule.lectureId());
    assertThat(result.get(0).capacity()).isEqualTo(lectureSchedule.capacity());
    assertThat(result.get(0).enrolledCount()).isEqualTo(lectureSchedule.enrolledCount());
    assertThat(result.get(0).startAt()).isEqualTo(lectureSchedule.startAt());
    assertThat(result.get(0).endAt()).isEqualTo(lectureSchedule.endAt());
  }

  @Test
  @DisplayName("getLectureEnrollmentById 테스트 성공")
  void shouldSuccessfullyGetLectureEnrollmentById() {
    // given
    final Long lectureEnrollmentId = 1L;
    final LectureQuery.GetLectureEnrollmentById query = new LectureQuery.GetLectureEnrollmentById(
        lectureEnrollmentId);
    final LectureEnrollment lectureEnrollment = new LectureEnrollment(lectureEnrollmentId, 1L,
        2L, 3L, LocalDateTime.now(), null);
    doReturn(lectureEnrollment).when(lectureRepository)
        .getLectureEnrollmentById(lectureEnrollmentId);

    // when
    final LectureEnrollment result = target.getLectureEnrollmentById(query);

    // then
    assertThat(result.id()).isEqualTo(lectureEnrollmentId);
    assertThat(result.userId()).isEqualTo(lectureEnrollment.userId());
    assertThat(result.lectureId()).isEqualTo(lectureEnrollment.lectureId());
    assertThat(result.lectureScheduleId()).isEqualTo(lectureEnrollment.lectureScheduleId());
    assertThat(result.createdAt()).isEqualTo(lectureEnrollment.createdAt());
    assertThat(result.updatedAt()).isEqualTo(lectureEnrollment.updatedAt());
  }

  @Test
  @DisplayName("findLectureEnrollments 테스트 성공")
  void shouldSuccessfullyFindLectureEnrollments() {
    // given
    final Long userId = 1L;
    final LectureEnrollment lectureEnrollment = new LectureEnrollment(1L, 1L, 2L, userId,
        LocalDateTime.now(), null);
    doReturn(List.of(lectureEnrollment)).when(lectureRepository)
        .findLectureEnrollmentsByUserId(userId);
    final FindLectureEnrollmentsByUserId query = new FindLectureEnrollmentsByUserId(
        userId);

    // when
    final List<LectureEnrollment> result = target.findLectureEnrollments(query);

    // then
    assertThat(result).hasSize(1);
    assertThat(result.get(0).id()).isEqualTo(lectureEnrollment.id());
    assertThat(result.get(0).userId()).isEqualTo(lectureEnrollment.userId());
    assertThat(result.get(0).lectureId()).isEqualTo(lectureEnrollment.lectureId());
    assertThat(result.get(0).lectureScheduleId()).isEqualTo(lectureEnrollment.lectureScheduleId());
    assertThat(result.get(0).createdAt()).isEqualTo(lectureEnrollment.createdAt());
    assertThat(result.get(0).updatedAt()).isEqualTo(lectureEnrollment.updatedAt());
  }

  @Test
  @DisplayName("findLecturesByIds 테스트 성공")
  void shouldSuccessfullyFindLecturesByIds() {
    // given
    final List<Long> lectureIds = List.of(1L, 2L);
    final List<Lecture> lectures = List.of(
        new Lecture(1L, "title1", "description1", 1L, LocalDateTime.now(), null),
        new Lecture(2L, "title2", "description2", 2L, LocalDateTime.now(), null)
    );
    final LectureQuery.FindLecturesByIds query = new LectureQuery.FindLecturesByIds(lectureIds);
    doReturn(lectures).when(lectureRepository).findLecturesByIds(lectureIds);

    // when
    final List<Lecture> result = target.findLecturesByIds(query);

    // then
    assertThat(result).hasSize(2);
    for (int i = 0; i < result.size(); i++) {
      final var lecture = result.get(i);
      final var expected = lectures.get(i);

      assertThat(lecture.id()).isEqualTo(expected.id());
      assertThat(lecture.title()).isEqualTo(expected.title());
      assertThat(lecture.description()).isEqualTo(expected.description());
      assertThat(lecture.lecturerId()).isEqualTo(expected.lecturerId());
      assertThat(lecture.createdAt()).isEqualTo(expected.createdAt());
      assertThat(lecture.updatedAt()).isEqualTo(expected.updatedAt());
    }
  }

}