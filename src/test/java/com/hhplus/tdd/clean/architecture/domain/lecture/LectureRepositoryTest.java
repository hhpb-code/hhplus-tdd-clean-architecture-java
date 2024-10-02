package com.hhplus.tdd.clean.architecture.domain.lecture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.hhplus.tdd.clean.architecture.domain.common.error.BusinessException;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.Lecture;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureSchedule;
import com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre.LectureEntity;
import com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre.LectureJpaRepository;
import com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre.LectureScheduleEntity;
import com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre.LectureScheduleJpaRepository;
import java.time.LocalDateTime;
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

  @BeforeEach
  void setUp() {
    lectureJpaRepository.deleteAll();
    lectureScheduleJpaRepository.deleteAll();
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
          () -> target.getLectureScheduleById(lectureScheduleId));

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
      final LectureSchedule result = target.getLectureScheduleById(lectureScheduleEntity.getId());

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

}