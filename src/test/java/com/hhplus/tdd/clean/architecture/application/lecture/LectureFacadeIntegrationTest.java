package com.hhplus.tdd.clean.architecture.application.lecture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.hhplus.tdd.clean.architecture.domain.common.error.BusinessException;
import com.hhplus.tdd.clean.architecture.domain.lecture.LectureErrorCode;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureEnrollment;
import com.hhplus.tdd.clean.architecture.domain.user.UserErrorCode;
import com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre.LectureEntity;
import com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre.LectureJpaRepository;
import com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre.LectureScheduleEntity;
import com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre.LectureScheduleJpaRepository;
import com.hhplus.tdd.clean.architecture.infrastructure.db.user.UserEntity;
import com.hhplus.tdd.clean.architecture.infrastructure.db.user.UserJpaRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("LectureFacade 통합 테스트")
class LectureFacadeIntegrationTest {

  @Autowired
  private LectureFacade target;

  @Autowired
  private UserJpaRepository userJpaRepository;

  @Autowired
  private LectureJpaRepository lectureJpaRepository;

  @Autowired
  private LectureScheduleJpaRepository lectureScheduleJpaRepository;


  @BeforeEach
  void setUp() {
    userJpaRepository.deleteAll();
    lectureJpaRepository.deleteAll();
    lectureScheduleJpaRepository.deleteAll();
  }

  @Nested
  @DisplayName("enrollLecture 테스트")
  class EnrollLectureTest {

    @Test
    @DisplayName("수강 신청 실패 - 유저가 존재하지 않음")
    void shouldThrowBusinessExceptionWhenUserNotFound() {
      // given
      final Long userId = 1L;
      final Long lectureId = 1L;
      final Long lectureScheduleId = 1L;

      // when
      final BusinessException result = assertThrows(BusinessException.class, () -> {
        target.enrollLecture(lectureId, lectureScheduleId, userId);
      });

      // then
      assertThat(result.getMessage()).isEqualTo(UserErrorCode.USER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("수강 신청 실패 - 강의가 존재하지 않음")
    void shouldThrowBusinessExceptionWhenLectureNotFound() {
      // given
      final Long lectureId = 1L;
      final Long lectureScheduleId = 1L;
      final UserEntity userEntity = userJpaRepository.save(
          new UserEntity(null, "name", "password"));
      final Long userId = userEntity.getId();

      // when
      final BusinessException result = assertThrows(BusinessException.class, () -> {
        target.enrollLecture(lectureId, lectureScheduleId, userId);
      });

      // then
      assertThat(result.getMessage()).isEqualTo(LectureErrorCode.LECTURE_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("수강 신청 실패 - 강의 스케줄이 존재하지 않음")
    void shouldThrowBusinessExceptionWhenLectureScheduleNotFound() {
      // given
      final Long lectureScheduleId = 1L;
      final UserEntity userEntity = userJpaRepository.save(
          new UserEntity(null, "name", "password"));
      final LectureEntity lectureEntity = lectureJpaRepository.save(
          new LectureEntity(null, "title", "description", 2L));
      final Long userId = userEntity.getId();
      final Long lectureId = lectureEntity.getId();

      // when
      final BusinessException result = assertThrows(BusinessException.class, () -> {
        target.enrollLecture(lectureId, lectureScheduleId, userId);
      });

      // then
      assertThat(result.getMessage()).isEqualTo(
          LectureErrorCode.LECTURE_SCHEDULE_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("수강 신청 성공")
    void shouldSuccessfullyEnrollLecture() {
      // given
      final UserEntity userEntity = userJpaRepository.save(
          new UserEntity(null, "name", "password"));
      final LectureEntity lectureEntity = lectureJpaRepository.save(
          new LectureEntity(null, "title", "description", 2L));
      final LectureScheduleEntity lectureScheduleEntity = lectureScheduleJpaRepository.save(
          new LectureScheduleEntity(null, lectureEntity.getId(), 30, 0, LocalDateTime.now(),
              LocalDateTime.now().plusHours(1)));
      final Long userId = userEntity.getId();
      final Long lectureId = lectureEntity.getId();
      final Long lectureScheduleId = lectureScheduleEntity.getId();

      // when
      final LectureEnrollment result = target.enrollLecture(lectureId, lectureScheduleId, userId);

      // then
      assertThat(result.userId()).isEqualTo(userId);
      assertThat(result.lectureId()).isEqualTo(lectureId);
      assertThat(result.lectureScheduleId()).isEqualTo(lectureScheduleId);
      assertThat(result.createdAt()).isNotNull();
      assertThat(result.updatedAt()).isNull();

      final LectureScheduleEntity updatedLectureScheduleEntity = lectureScheduleJpaRepository
          .findById(lectureScheduleId).get();
      assertThat(updatedLectureScheduleEntity.getEnrolledCount()).isEqualTo(
          lectureScheduleEntity.getEnrolledCount() + 1);
    }
  }
}
