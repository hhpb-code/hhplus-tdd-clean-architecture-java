package com.hhplus.tdd.clean.architecture.domain.lecture.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.hhplus.tdd.clean.architecture.domain.common.error.BusinessException;
import com.hhplus.tdd.clean.architecture.domain.lecture.LectureErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("LectureQuery 단위 테스트")
class LectureQueryTest {

  @Nested
  @DisplayName("GetLectureById 테스트")
  class GetLectureByIdTest {

    @Test
    @DisplayName("생성자 테스트 실패 - lectureId가 null")
    void shouldThrowExceptionWhenLectureIdIsNull() {
      // given
      final Long lectureId = null;

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          () -> new LectureQuery.GetLectureById(lectureId));

      // then
      assertThat(result.getMessage()).isEqualTo(LectureErrorCode.INVALID_LECTURE_ID.getMessage());
    }

    @Test
    @DisplayName("생성자 테스트 실패 - lectureId가 0")
    void shouldThrowExceptionWhenLectureIdIsZero() {
      // given
      final Long lectureId = 0L;

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          () -> new LectureQuery.GetLectureById(lectureId));

      // then
      assertThat(result.getMessage()).isEqualTo(LectureErrorCode.INVALID_LECTURE_ID.getMessage());
    }

    @Test
    @DisplayName("생성자 테스트 실패 - lectureId가 음수")
    void shouldThrowExceptionWhenLectureIdIsNegative() {
      // given
      final Long lectureId = -1L;

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          () -> new LectureQuery.GetLectureById(lectureId));

      // then
      assertThat(result.getMessage()).isEqualTo(LectureErrorCode.INVALID_LECTURE_ID.getMessage());
    }

    @Test
    @DisplayName("생성자 테스트 성공")
    void shouldSuccessfullyGetLectureById() {
      // given
      final Long lectureId = 1L;

      // when
      final LectureQuery.GetLectureById result = new LectureQuery.GetLectureById(lectureId);

      // then
      assertThat(result.lectureId()).isEqualTo(lectureId);
    }
  }

  @Nested
  @DisplayName("GetLectureScheduleById 테스트")
  class GetLectureScheduleByIdTest {

    @Test
    @DisplayName("생성자 테스트 실패 - lectureScheduleId가 null")
    void shouldThrowExceptionWhenLectureScheduleIdIsNull() {
      // given
      final Long lectureScheduleId = null;

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          () -> new LectureQuery.GetLectureScheduleById(lectureScheduleId));

      // then
      assertThat(result.getMessage()).isEqualTo(
          LectureErrorCode.INVALID_LECTURE_SCHEDULE_ID.getMessage());
    }

    @Test
    @DisplayName("생성자 테스트 실패 - lectureScheduleId가 0")
    void shouldThrowExceptionWhenLectureScheduleIdIsZero() {
      // given
      final Long lectureScheduleId = 0L;

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          () -> new LectureQuery.GetLectureScheduleById(lectureScheduleId));

      // then
      assertThat(result.getMessage()).isEqualTo(
          LectureErrorCode.INVALID_LECTURE_SCHEDULE_ID.getMessage());
    }

    @Test
    @DisplayName("생성자 테스트 실패 - lectureScheduleId가 음수")
    void shouldThrowExceptionWhenLectureScheduleIdIsNegative() {
      // given
      final Long lectureScheduleId = -1L;

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          () -> new LectureQuery.GetLectureScheduleById(lectureScheduleId));

      // then
      assertThat(result.getMessage()).isEqualTo(
          LectureErrorCode.INVALID_LECTURE_SCHEDULE_ID.getMessage());
    }

    @Test
    @DisplayName("생성자 테스트 성공")
    void shouldSuccessfullyGetLectureScheduleById() {
      // given
      final Long lectureScheduleId = 1L;

      // when
      final LectureQuery.GetLectureScheduleById result = new LectureQuery.GetLectureScheduleById(
          lectureScheduleId);

      // then
      assertThat(result.lectureScheduleId()).isEqualTo(lectureScheduleId);
    }
  }

  @Nested
  @DisplayName("GetLectureEnrollmentById 테스트")
  class GetLectureEnrollmentByIdTest {

    @Test
    @DisplayName("생성자 테스트 실패 - lectureEnrollmentId가 null")
    void shouldThrowExceptionWhenLectureEnrollmentIdIsNull() {
      // given
      final Long lectureEnrollmentId = null;

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          () -> new LectureQuery.GetLectureEnrollmentById(lectureEnrollmentId));

      // then
      assertThat(result.getMessage()).isEqualTo(
          LectureErrorCode.INVALID_LECTURE_ENROLLMENT_ID.getMessage());
    }

    @Test
    @DisplayName("생성자 테스트 실패 - lectureEnrollmentId가 0")
    void shouldThrowExceptionWhenLectureEnrollmentIdIsZero() {
      // given
      final Long lectureEnrollmentId = 0L;

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          () -> new LectureQuery.GetLectureEnrollmentById(lectureEnrollmentId));

      // then
      assertThat(result.getMessage()).isEqualTo(
          LectureErrorCode.INVALID_LECTURE_ENROLLMENT_ID.getMessage());
    }

    @Test
    @DisplayName("생성자 테스트 실패 - lectureEnrollmentId가 음수")
    void shouldThrowExceptionWhenLectureEnrollmentIdIsNegative() {
      // given
      final Long lectureEnrollmentId = -1L;

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          () -> new LectureQuery.GetLectureEnrollmentById(lectureEnrollmentId));

      // then
      assertThat(result.getMessage()).isEqualTo(
          LectureErrorCode.INVALID_LECTURE_ENROLLMENT_ID.getMessage());
    }

    @Test
    @DisplayName("생성자 테스트 성공")
    void shouldSuccessfullyGetLectureEnrollmentById() {
      // given
      final Long lectureEnrollmentId = 1L;

      // when
      final LectureQuery.GetLectureEnrollmentById result = new LectureQuery.GetLectureEnrollmentById(
          lectureEnrollmentId);

      // then
      assertThat(result.lectureEnrollmentId()).isEqualTo(lectureEnrollmentId);
    }
  }

}