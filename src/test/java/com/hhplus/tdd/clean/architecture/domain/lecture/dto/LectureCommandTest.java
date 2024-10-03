package com.hhplus.tdd.clean.architecture.domain.lecture.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.hhplus.tdd.clean.architecture.domain.common.error.BusinessException;
import com.hhplus.tdd.clean.architecture.domain.lecture.LectureErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("LectureCommand 단위 테스트")
class LectureCommandTest {

  @Nested
  @DisplayName("CreateLectureEnrollment 테스트")
  class CreateLectureEnrollmentTest {

    @Test
    @DisplayName("생성자 테스트 실패 - lectureId가 null")
    void shouldThrowExceptionWhenLectureIdIsNull() {
      // given
      final Long lectureId = null;

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          () -> new LectureCommand.CreateLectureEnrollment(lectureId, 1L, 1L));

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
          () -> new LectureCommand.CreateLectureEnrollment(lectureId, 1L, 1L));

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
          () -> new LectureCommand.CreateLectureEnrollment(lectureId, 1L, 1L));

      // then
      assertThat(result.getMessage()).isEqualTo(LectureErrorCode.INVALID_LECTURE_ID.getMessage());
    }

    @Test
    @DisplayName("생성자 테스트 실패 - lectureScheduleId가 null")
    void shouldThrowExceptionWhenLectureScheduleIdIsNull() {
      // given
      final Long lectureScheduleId = null;

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          () -> new LectureCommand.CreateLectureEnrollment(1L, lectureScheduleId, 1L));

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
          () -> new LectureCommand.CreateLectureEnrollment(1L, lectureScheduleId, 1L));

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
          () -> new LectureCommand.CreateLectureEnrollment(1L, lectureScheduleId, 1L));

      // then
      assertThat(result.getMessage()).isEqualTo(
          LectureErrorCode.INVALID_LECTURE_SCHEDULE_ID.getMessage());
    }

    @Test
    @DisplayName("생성자 테스트 실패 - userId가 null")
    void shouldThrowExceptionWhenUserIdIsNull() {
      // given
      final Long userId = null;

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          () -> new LectureCommand.CreateLectureEnrollment(1L, 1L, userId));

      // then
      assertThat(result.getMessage()).isEqualTo(LectureErrorCode.INVALID_USER_ID.getMessage());
    }

    @Test
    @DisplayName("생성자 테스트 실패 - userId가 0")
    void shouldThrowExceptionWhenUserIdIsZero() {
      // given
      final Long userId = 0L;

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          () -> new LectureCommand.CreateLectureEnrollment(1L, 1L, userId));

      // then
      assertThat(result.getMessage()).isEqualTo(LectureErrorCode.INVALID_USER_ID.getMessage());
    }

    @Test
    @DisplayName("생성자 테스트 실패 - userId가 음수")
    void shouldThrowExceptionWhenUserIdIsNegative() {
      // given
      final Long userId = -1L;

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          () -> new LectureCommand.CreateLectureEnrollment(1L, 1L, userId));

      // then
      assertThat(result.getMessage()).isEqualTo(LectureErrorCode.INVALID_USER_ID.getMessage());
    }

    @Test
    @DisplayName("생성자 테스트 성공")
    void shouldSuccessfullyCreateLectureEnrollment() {
      // given
      final Long lectureId = 1L;
      final Long lectureScheduleId = 2L;
      final Long userId = 3L;

      // when
      final LectureCommand.CreateLectureEnrollment result = new LectureCommand.CreateLectureEnrollment(
          lectureId, lectureScheduleId, userId);

      // then
      assertThat(result.lectureId()).isEqualTo(lectureId);
      assertThat(result.lectureScheduleId()).isEqualTo(lectureScheduleId);
      assertThat(result.userId()).isEqualTo(userId);
    }
  }

}