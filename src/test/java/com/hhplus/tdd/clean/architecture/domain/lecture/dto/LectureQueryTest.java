package com.hhplus.tdd.clean.architecture.domain.lecture.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.hhplus.tdd.clean.architecture.domain.common.error.BusinessException;
import com.hhplus.tdd.clean.architecture.domain.lecture.LectureErrorCode;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureQuery.FindAvailableLectureSchedulesByDate;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureQuery.FindLectureEnrollmentsByUserId;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
  @DisplayName("FindAvailableLectureSchedulesByDate 테스트")
  class FindAvailableLectureSchedulesByDateTest {

    @Test
    @DisplayName("생성자 테스트 실패 - date가 null")
    void shouldThrowExceptionWhenDateIsNull() {
      // given
      final LocalDate date = null;

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          () -> new FindAvailableLectureSchedulesByDate(date));

      // then
      assertThat(result.getMessage()).isEqualTo(LectureErrorCode.INVALID_DATE.getMessage());
    }

    @Test
    @DisplayName("생성자 테스트 실패 - date가 현재 날짜 이전")
    void shouldThrowExceptionWhenDateIsBeforeCurrentDate() {
      // given
      final LocalDate date = LocalDate.now().minusDays(1);

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          () -> new FindAvailableLectureSchedulesByDate(date));

      // then
      assertThat(result.getMessage()).isEqualTo(LectureErrorCode.INVALID_DATE.getMessage());
    }

    @Test
    @DisplayName("생성자 테스트 성공")
    void shouldSuccessfullyFindAvailableLectureSchedulesByDate() {
      // given
      final LocalDate date = LocalDate.now();

      // when
      final FindAvailableLectureSchedulesByDate result = new FindAvailableLectureSchedulesByDate(
          date);

      // then
      assertThat(result.date()).isEqualTo(date);
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

  @Nested
  @DisplayName("FindLectureEnrollmentsByUserId 테스트")
  class FindLectureEnrollmentsByUserIdTest {

    @Test
    @DisplayName("생성자 테스트 실패 - userId가 null")
    void shouldThrowExceptionWhenUserIdIsNull() {
      // given
      final Long userId = null;

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          () -> new FindLectureEnrollmentsByUserId(userId));

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
          () -> new FindLectureEnrollmentsByUserId(userId));

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
          () -> new FindLectureEnrollmentsByUserId(userId));

      // then
      assertThat(result.getMessage()).isEqualTo(LectureErrorCode.INVALID_USER_ID.getMessage());
    }

    @Test
    @DisplayName("생성자 테스트 성공")
    void shouldSuccessfullyFindLectureEnrollmentsByUserId() {
      // given
      final Long userId = 1L;

      // when
      final FindLectureEnrollmentsByUserId result = new FindLectureEnrollmentsByUserId(
          userId);

      // then
      assertThat(result.userId()).isEqualTo(userId);
    }
  }

  @Nested
  @DisplayName("FindLecturesByIds 테스트")
  class FindLecturesByIdsTest {

    @Test
    @DisplayName("생성자 테스트 실패 - lectureIds가 null")
    void shouldThrowExceptionWhenLectureIdsIsNull() {
      // given
      final List<Long> lectureIds = null;

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          () -> new LectureQuery.FindLecturesByIds(lectureIds));

      // then
      assertThat(result.getMessage()).isEqualTo(LectureErrorCode.INVALID_LECTURE_IDS.getMessage());
    }

    @Test
    @DisplayName("생성자 테스트 실패 - lectureIds가 비어있음")
    void shouldThrowExceptionWhenLectureIdsIsEmpty() {
      // given
      final List<Long> lectureIds = List.of();

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          () -> new LectureQuery.FindLecturesByIds(lectureIds));

      // then
      assertThat(result.getMessage()).isEqualTo(LectureErrorCode.INVALID_LECTURE_IDS.getMessage());
    }

    @Test
    @DisplayName("생성자 테스트 실패 - lectureIds에 null이 포함됨")
    void shouldThrowExceptionWhenLectureIdsContainsNull() {
      // given
      final List<Long> lectureIds = new ArrayList<>();
      lectureIds.add(1L);
      lectureIds.add(null);
      lectureIds.add(3L);

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          () -> new LectureQuery.FindLecturesByIds(lectureIds));

      // then
      assertThat(result.getMessage()).isEqualTo(LectureErrorCode.INVALID_LECTURE_IDS.getMessage());
    }

    @Test
    @DisplayName("생성자 테스트 실패 - lectureIds에 0이 포함됨")
    void shouldThrowExceptionWhenLectureIdsContainsZero() {
      // given
      final List<Long> lectureIds = List.of(1L, 0L, 3L);

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          () -> new LectureQuery.FindLecturesByIds(lectureIds));

      // then
      assertThat(result.getMessage()).isEqualTo(LectureErrorCode.INVALID_LECTURE_IDS.getMessage());
    }

    @Test
    @DisplayName("생성자 테스트 실패 - lectureIds에 음수가 포함됨")
    void shouldThrowExceptionWhenLectureIdsContainsNegative() {
      // given
      final List<Long> lectureIds = List.of(1L, -1L, 3L);

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          () -> new LectureQuery.FindLecturesByIds(lectureIds));

      // then
      assertThat(result.getMessage()).isEqualTo(LectureErrorCode.INVALID_LECTURE_IDS.getMessage());
    }

    @Test
    @DisplayName("생성자 테스트 성공")
    void shouldSuccessfullyFindLecturesByIds() {
      // given
      final List<Long> lectureIds = List.of(1L, 2L, 3L);

      // when
      final LectureQuery.FindLecturesByIds result = new LectureQuery.FindLecturesByIds(lectureIds);

      // then
      assertThat(result.lectureIds()).isEqualTo(lectureIds);
    }

  }

}