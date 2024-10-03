package com.hhplus.tdd.clean.architecture.domain.lecture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.hhplus.tdd.clean.architecture.domain.common.error.BusinessException;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.Lecture;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureEnrollment;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureSchedule;
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
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("LectureRepository 통합 테스트")
class LectureRepositoryTest {

  @Autowired
  private LectureRepository target;

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

  @Nested
  @DisplayName("getLectureById 테스트")
  class GetLectureByIdTest {


    @Test
    @DisplayName("getLectureById 테스트 실패 - 존재하지 않는 lectureId")
    void shouldThrowExceptionWhenGetLectureByIdWithNonExistentLectureId() {
      // given
      final Long lectureId = 1L;

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          () -> target.getLectureById(lectureId));

      // then
      assertThat(result.getMessage()).isEqualTo(LectureErrorCode.LECTURE_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("getLectureById 테스트 성공")
    void shouldSuccessfullyGetLectureById() {
      // given
      final String title = "title";
      final String description = "description";
      final Long lecturerId = 2L;
      final LectureEntity lectureEntity = lectureJpaRepository.save(
          new LectureEntity(null, title, description, lecturerId));

      // when
      final Lecture result = target.getLectureById(lectureEntity.getId());

      // then
      assertThat(result.id()).isEqualTo(lectureEntity.getId());
      assertThat(result.title()).isEqualTo(title);
      assertThat(result.description()).isEqualTo(description);
      assertThat(result.lecturerId()).isEqualTo(lecturerId);
      assertThat(result.createdAt()).isNotNull();
      assertThat(result.updatedAt()).isNull();
    }
  }

  @Nested
  @DisplayName("getLectureScheduleById 테스트")
  class GetLectureScheduleByIdTest {

    @Test
    @DisplayName("getLectureScheduleById 테스트 실패 - 존재하지 않는 lectureScheduleId")
    void shouldThrowExceptionWhenGetLectureScheduleByIdWithNonExistentLectureScheduleId() {
      // given
      final Long lectureScheduleId = 1L;

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          () -> target.getLectureScheduleById(lectureScheduleId, false));

      // then
      assertThat(result.getMessage()).isEqualTo(
          LectureErrorCode.LECTURE_SCHEDULE_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("getLectureScheduleById 테스트 성공")
    void shouldSuccessfullyGetLectureScheduleById() {
      // given
      final int capacity = 30;
      final int enrolledCount = 0;
      final LectureEntity lectureEntity = lectureJpaRepository.save(
          new LectureEntity(null, "title", "description", 2L));
      final LectureScheduleEntity lectureScheduleEntity = lectureScheduleJpaRepository.save(
          new LectureScheduleEntity(null, lectureEntity.getId(), capacity, enrolledCount,
              LocalDateTime.now(),
              LocalDateTime.now()));

      // when
      final LectureSchedule result = target.getLectureScheduleById(lectureScheduleEntity.getId(),
          false);

      // then
      assertThat(result.id()).isEqualTo(lectureScheduleEntity.getId());
      assertThat(result.lectureId()).isEqualTo(lectureEntity.getId());
      assertThat(result.capacity()).isEqualTo(capacity);
      assertThat(result.enrolledCount()).isEqualTo(enrolledCount);
      assertThat(result.startAt()).isNotNull();
      assertThat(result.endAt()).isNotNull();
      assertThat(result.createdAt()).isNotNull();
      assertThat(result.updatedAt()).isNull();
    }
  }

  @Test
  @DisplayName("FindAvailableLectureSchedulesByDate 테스트 성공")
  void shouldSuccessfullyFindAvailableLectureSchedulesByDate() {
    // given
    final LocalDateTime now = LocalDateTime.now();
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
    final List<LectureSchedule> result = target.findAvailableLectureSchedulesByDate(
        now.toLocalDate());

    // then
    assertThat(result).hasSize(2);
    for (int i = 0; i < result.size(); i++) {
      final LectureSchedule lectureSchedule = result.get(i);
      final LectureScheduleEntity lectureScheduleEntity = lectureScheduleEntities.get(i);

      assertThat(lectureSchedule.id()).isEqualTo(lectureScheduleEntity.getId());
      assertThat(lectureSchedule.lectureId()).isEqualTo(lectureScheduleEntity.getLectureId());
      assertThat(lectureSchedule.capacity()).isEqualTo(lectureScheduleEntity.getCapacity());
      assertThat(lectureSchedule.enrolledCount())
          .isEqualTo(lectureScheduleEntity.getEnrolledCount());
      assertThat(lectureSchedule.startAt()).isEqualTo(lectureScheduleEntity.getStartAt());
      assertThat(lectureSchedule.endAt()).isEqualTo(lectureScheduleEntity.getEndAt());
      assertThat(lectureSchedule.createdAt()).isNotNull();
      assertThat(lectureSchedule.updatedAt()).isNull();
    }


  }

  @Nested
  @DisplayName("getLectureEnrollmentById 테스트")
  class GetLectureEnrollmentByIdTest {

    @Test
    @DisplayName("getLectureEnrollmentById 테스트 실패 - 존재하지 않는 enrollmentId")
    void shouldThrowExceptionWhenGetLectureEnrollmentByIdWithNonExistentEnrollmentId() {
      // given
      final Long enrollmentId = 1L;

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          () -> target.getLectureEnrollmentById(enrollmentId));

      // then
      assertThat(result.getMessage()).isEqualTo(
          LectureErrorCode.LECTURE_ENROLLMENT_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("getLectureEnrollmentById 테스트 성공")
    void shouldSuccessfullyGetLectureEnrollmentById() {
      // given
      final Long lectureId = 1L;
      final Long lectureScheduleId = 1L;
      final Long userId = 1L;
      final LectureEnrollmentEntity lectureEnrollmentEntity = lectureEnrollmentJpaRepository.save(
          new LectureEnrollmentEntity(lectureId, lectureScheduleId, userId));

      // when
      final LectureEnrollment result = target.getLectureEnrollmentById(
          lectureEnrollmentEntity.getId());

      // then
      assertThat(result.id()).isEqualTo(lectureEnrollmentEntity.getId());
      assertThat(result.lectureId()).isEqualTo(lectureId);
      assertThat(result.lectureScheduleId()).isEqualTo(lectureScheduleId);
      assertThat(result.userId()).isEqualTo(userId);
      assertThat(result.createdAt()).isNotNull();
      assertThat(result.updatedAt()).isNull();
    }
  }

  @Test
  @DisplayName("createLectureEnrollment 테스트 성공")
  void shouldSuccessfullyCreateLectureEnrollment() {
    // given
    final Long lectureId = 1L;
    final Long lectureScheduleId = 1L;
    final Long userId = 1L;

    // when
    final Long result = target.createLectureEnrollment(lectureId, lectureScheduleId, userId);

    // then
    assertThat(result).isNotNull();

    final LectureEnrollmentEntity lectureEnrollmentEntity = lectureEnrollmentJpaRepository.findById(
        result).get();

    assertThat(lectureEnrollmentEntity.getLectureId()).isEqualTo(lectureId);
    assertThat(lectureEnrollmentEntity.getLectureScheduleId()).isEqualTo(lectureScheduleId);
    assertThat(lectureEnrollmentEntity.getUserId()).isEqualTo(userId);
    assertThat(lectureEnrollmentEntity.getCreatedAt()).isNotNull();
    assertThat(lectureEnrollmentEntity.getUpdatedAt()).isNull();
  }

  @Test
  @DisplayName("increaseEnrollmentCountByLectureScheduleId 테스트 성공")
  void shouldSuccessfullyIncreaseEnrollmentCountByLectureScheduleId() {
    // given
    final LectureScheduleEntity lectureScheduleEntity = lectureScheduleJpaRepository.save(
        new LectureScheduleEntity(null, 1L, 30, 0, LocalDateTime.now(), LocalDateTime.now()));

    // when
    target.increaseEnrollmentCountByLectureScheduleId(lectureScheduleEntity.getId());

    // then
    final LectureScheduleEntity updatedLectureScheduleEntity = lectureScheduleJpaRepository
        .findById(lectureScheduleEntity.getId()).get();
    assertThat(updatedLectureScheduleEntity.getEnrolledCount()).isEqualTo(
        lectureScheduleEntity.getEnrolledCount() + 1);
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
    final List<LectureEnrollment> result = target.findLectureEnrollmentsByUserId(userId);

    // then
    assertThat(result).hasSize(2);
    for (int i = 0; i < result.size(); i++) {
      final LectureEnrollment lectureEnrollment = result.get(i);
      final LectureEnrollmentEntity lectureEnrollmentEntity = lectureEnrollmentEntities.get(i);

      assertThat(lectureEnrollment.id()).isEqualTo(lectureEnrollmentEntity.getId());
      assertThat(lectureEnrollment.lectureId()).isEqualTo(lectureEnrollmentEntity.getLectureId());
      assertThat(lectureEnrollment.userId()).isEqualTo(lectureEnrollmentEntity.getUserId());
      assertThat(lectureEnrollment.createdAt()).isNotNull();
      assertThat(lectureEnrollment.updatedAt()).isNull();
    }
  }

  @Test
  @DisplayName("findLecturesByIds 테스트 성공")
  void shouldSuccessfullyFindLecturesByIds() {
    // given
    final List<LectureEntity> lectureEntities = List.of(
        lectureJpaRepository.save(new LectureEntity(null, "title1", "description1", 1L)),
        lectureJpaRepository.save(new LectureEntity(null, "title2", "description2", 2L))
    );
    final List<Long> lectureIds = lectureEntities.stream()
        .map(LectureEntity::getId)
        .toList();
    lectureJpaRepository.save(new LectureEntity(null, "title3", "description3", 3L));

    // when
    final List<Lecture> result = target.findLecturesByIds(lectureIds);

    // then
    assertThat(result).hasSize(2);
    for (int i = 0; i < result.size(); i++) {
      final Lecture lecture = result.get(i);
      final LectureEntity lectureEntity = lectureEntities.get(i);

      assertThat(lecture.id()).isEqualTo(lectureEntity.getId());
      assertThat(lecture.title()).isEqualTo(lectureEntity.getTitle());
      assertThat(lecture.description()).isEqualTo(lectureEntity.getDescription());
      assertThat(lecture.lecturerId()).isEqualTo(lectureEntity.getLecturerId());
      assertThat(lecture.createdAt()).isNotNull();
      assertThat(lecture.updatedAt()).isNull();
    }
  }

}