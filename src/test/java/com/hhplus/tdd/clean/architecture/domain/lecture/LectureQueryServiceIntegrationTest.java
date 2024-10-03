package com.hhplus.tdd.clean.architecture.domain.lecture;

import static org.assertj.core.api.Assertions.assertThat;

import com.hhplus.tdd.clean.architecture.domain.lecture.dto.Lecture;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureQuery;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureQuery.FindLectureEnrollmentsByUserId;
import com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre.LectureEnrollmentEntity;
import com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre.LectureEnrollmentJpaRepository;
import com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre.LectureEntity;
import com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre.LectureJpaRepository;
import com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre.LectureScheduleEntity;
import com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre.LectureScheduleJpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("LectureQueryService 통합 테스트")
class LectureQueryServiceIntegrationTest {

  @Autowired
  private LectureQueryService target;

  @Autowired
  private LectureJpaRepository lectureJpaRepository;

  @Autowired
  private LectureScheduleJpaRepository lectureScheduleJpaRepository;

  @Autowired
  private LectureEnrollmentJpaRepository lectureEnrollmentJpaRepository;

  @BeforeEach
  void setUp() {
    lectureJpaRepository.deleteAll();
    lectureScheduleJpaRepository.deleteAll();
    lectureEnrollmentJpaRepository.deleteAll();
  }

  @Test
  @DisplayName("getLectureById 테스트 성공")
  void shouldSuccessfullyGetLectureById() {
    // given
    final LectureEntity lectureEntity = lectureJpaRepository.save(
        new LectureEntity(null, "title", "description", 2L));
    final LectureQuery.GetLectureById query = new LectureQuery.GetLectureById(
        lectureEntity.getId());

    // when
    final Lecture result = target.getLectureById(query);

    // then
    assertThat(result.id()).isEqualTo(lectureEntity.getId());
    assertThat(result.title()).isEqualTo(lectureEntity.getTitle());
    assertThat(result.description()).isEqualTo(lectureEntity.getDescription());
    assertThat(result.lecturerId()).isEqualTo(lectureEntity.getLecturerId());
    assertThat(result.createdAt()).isNotNull();
    assertThat(result.updatedAt()).isNull();
  }

  @Test
  @DisplayName("getLectureScheduleById 테스트 성공")
  void shouldSuccessfullyGetLectureScheduleById() {
    // given
    final LectureScheduleEntity lectureScheduleEntity = lectureScheduleJpaRepository.save(
        new LectureScheduleEntity(null, 1L, 30, 0, LocalDateTime.now(), LocalDateTime.now()));
    final LectureQuery.GetLectureScheduleById query = new LectureQuery.GetLectureScheduleById(
        lectureScheduleEntity.getId());

    // when
    final var result = target.getLectureScheduleById(query);

    // then
    assertThat(result.id()).isEqualTo(lectureScheduleEntity.getId());
    assertThat(result.lectureId()).isEqualTo(lectureScheduleEntity.getLectureId());
    assertThat(result.capacity()).isEqualTo(lectureScheduleEntity.getCapacity());
    assertThat(result.enrolledCount()).isEqualTo(lectureScheduleEntity.getEnrolledCount());
    assertThat(result.startAt()).isNotNull();
    assertThat(result.endAt()).isNotNull();
    assertThat(result.createdAt()).isNotNull();
    assertThat(result.updatedAt()).isNull();
  }

  @Test
  @DisplayName("findAvailableLectureSchedulesByDate 테스트 성공")
  void shouldSuccessfullyFindAvailableLectureSchedulesByDate() {
    // given
    final LocalDateTime now = LocalDateTime.now();
    final LectureQuery.FindAvailableLectureSchedulesByDate query = new LectureQuery.FindAvailableLectureSchedulesByDate(
        now.toLocalDate());
    final var lectureScheduleEntities = List.of(
        lectureScheduleJpaRepository.save(
            new LectureScheduleEntity(null, 1L, 30, 0, now, now)),
        lectureScheduleJpaRepository.save(
            new LectureScheduleEntity(null, 2L, 10, 5, now, now))
    );
    lectureScheduleJpaRepository.save(
        new LectureScheduleEntity(null, 3L, 10, 10, now, now));
    lectureScheduleJpaRepository.save(
        new LectureScheduleEntity(null, 3L, 30, 0, now.minusDays(1), now.minusDays(1)));
    lectureScheduleJpaRepository.save(
        new LectureScheduleEntity(null, 4L, 30, 0, now.plusDays(1), now.plusDays(1)));

    // when
    final var result = target.findAvailableLectureSchedules(query);

    // then
    assertThat(result).hasSize(2);
    for (int i = 0; i < result.size(); i++) {
      final var lectureSchedule = result.get(i);
      final var lectureScheduleEntity = lectureScheduleEntities.get(i);

      assertThat(lectureSchedule.id()).isEqualTo(lectureScheduleEntity.getId());
      assertThat(lectureSchedule.lectureId()).isEqualTo(lectureScheduleEntity.getLectureId());
      assertThat(lectureSchedule.capacity()).isEqualTo(lectureScheduleEntity.getCapacity());
      assertThat(lectureSchedule.enrolledCount()).isEqualTo(
          lectureScheduleEntity.getEnrolledCount());
      assertThat(lectureSchedule.startAt()).isNotNull();
      assertThat(lectureSchedule.endAt()).isNotNull();
      assertThat(lectureSchedule.createdAt()).isNotNull();
      assertThat(lectureSchedule.updatedAt()).isNull();
    }
  }

  @Test
  @DisplayName("getLectureEnrollmentById 테스트 성공")
  void shouldSuccessfullyGetLectureEnrollmentById() {
    // given
    final LectureEnrollmentEntity lectureEnrollmentEntity = lectureEnrollmentJpaRepository.save(
        new LectureEnrollmentEntity(2L, 3L, 4L));
    final LectureQuery.GetLectureEnrollmentById query = new LectureQuery.GetLectureEnrollmentById(
        lectureEnrollmentEntity.getId());

    // when
    final var result = target.getLectureEnrollmentById(query);

    // then
    assertThat(result.id()).isEqualTo(lectureEnrollmentEntity.getId());
    assertThat(result.lectureId()).isEqualTo(lectureEnrollmentEntity.getLectureId());
    assertThat(result.userId()).isEqualTo(lectureEnrollmentEntity.getUserId());
    assertThat(result.createdAt()).isNotNull();
    assertThat(result.updatedAt()).isNull();
  }

  @Test
  @DisplayName("findLectureEnrollmentsByUserId 테스트 성공")
  void shouldSuccessfullyFindLectureEnrollmentsByUserId() {
    // given
    final Long userId = 1L;
    final var lectureEnrollmentEntities = List.of(
        lectureEnrollmentJpaRepository.save(new LectureEnrollmentEntity(1L, 1L, userId)),
        lectureEnrollmentJpaRepository.save(new LectureEnrollmentEntity(2L, 2L, userId))
    );
    lectureEnrollmentJpaRepository.save(new LectureEnrollmentEntity(3L, 3L, userId + 1));

    // when
    final var result = target.findLectureEnrollments(
        new FindLectureEnrollmentsByUserId(userId));

    // then
    assertThat(result).hasSize(2);
    for (int i = 0; i < result.size(); i++) {
      final var lectureEnrollment = result.get(i);
      final var lectureEnrollmentEntity = lectureEnrollmentEntities.get(i);

      assertThat(lectureEnrollment.id()).isEqualTo(lectureEnrollmentEntity.getId());
      assertThat(lectureEnrollment.lectureId()).isEqualTo(lectureEnrollmentEntity.getLectureId());
      assertThat(lectureEnrollment.userId()).isEqualTo(lectureEnrollmentEntity.getUserId());
      assertThat(lectureEnrollment.createdAt()).isNotNull();
      assertThat(lectureEnrollment.updatedAt()).isNull();
    }
  }

}