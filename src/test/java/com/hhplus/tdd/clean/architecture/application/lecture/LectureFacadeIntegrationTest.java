package com.hhplus.tdd.clean.architecture.application.lecture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.hhplus.tdd.clean.architecture.domain.common.error.BusinessException;
import com.hhplus.tdd.clean.architecture.domain.lecture.LectureErrorCode;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureEnrollment;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureWithLecturer;
import com.hhplus.tdd.clean.architecture.domain.user.UserErrorCode;
import com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre.LectureEnrollmentEntity;
import com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre.LectureEnrollmentJpaRepository;
import com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre.LectureEntity;
import com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre.LectureJpaRepository;
import com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre.LectureScheduleEntity;
import com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre.LectureScheduleJpaRepository;
import com.hhplus.tdd.clean.architecture.infrastructure.db.user.UserEntity;
import com.hhplus.tdd.clean.architecture.infrastructure.db.user.UserJpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;
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

  @Autowired
  private LectureEnrollmentJpaRepository lectureEnrollmentJpaRepository;


  @BeforeEach
  void setUp() {
    userJpaRepository.deleteAll();
    lectureJpaRepository.deleteAll();
    lectureScheduleJpaRepository.deleteAll();
    lectureEnrollmentJpaRepository.deleteAll();
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
    @DisplayName("수강 신청 실패 - 수강 신청 인원 초과")
    void shouldThrowBusinessExceptionWhenExceedCapacity() {
      // given
      final UserEntity userEntity = userJpaRepository.save(
          new UserEntity(null, "name", "password"));
      final LectureEntity lectureEntity = lectureJpaRepository.save(
          new LectureEntity(null, "title", "description", 2L));
      final LectureScheduleEntity lectureScheduleEntity = lectureScheduleJpaRepository.save(
          new LectureScheduleEntity(null, lectureEntity.getId(), 30, 30, LocalDateTime.now(),
              LocalDateTime.now().plusHours(1)));
      final Long userId = userEntity.getId();
      final Long lectureId = lectureEntity.getId();
      final Long lectureScheduleId = lectureScheduleEntity.getId();

      // when
      final BusinessException result = assertThrows(BusinessException.class, () -> {
        target.enrollLecture(lectureId, lectureScheduleId, userId);
      });

      // then
      assertThat(result.getMessage()).isEqualTo(LectureErrorCode.ENROLLMENT_EXCEED_CAPACITY
          .getMessage());
    }

    @Test
    @DisplayName("수강 신청 실패 - 이미 수강 신청한 경우")
    void shouldThrowBusinessExceptionWhenAlreadyEnrolled() {
      // given
      final UserEntity userEntity = userJpaRepository.save(
          new UserEntity(null, "name", "password"));
      final LectureEntity lectureEntity = lectureJpaRepository.save(
          new LectureEntity(null, "title", "description", 2L));
      final LectureScheduleEntity lectureScheduleEntity1 = lectureScheduleJpaRepository.save(
          new LectureScheduleEntity(null, lectureEntity.getId(), 30, 0, LocalDateTime.now(),
              LocalDateTime.now().plusHours(1)));
      final LectureScheduleEntity lectureScheduleEntity2 = lectureScheduleJpaRepository.save(
          new LectureScheduleEntity(null, lectureEntity.getId(), 30, 0, LocalDateTime.now(),
              LocalDateTime.now().plusHours(1)));
      final Long userId = userEntity.getId();
      final Long lectureId = lectureEntity.getId();
      final Long lectureScheduleId = lectureScheduleEntity1.getId();
      lectureEnrollmentJpaRepository.save(
          new LectureEnrollmentEntity(lectureId, lectureScheduleEntity2.getId(), userId));

      // when
      final BusinessException result = assertThrows(BusinessException.class, () -> {
        target.enrollLecture(lectureId, lectureScheduleId, userId);
      });

      // then
      assertThat(result.getMessage()).isEqualTo(LectureErrorCode.DUPLICATE_ENROLLMENT.getMessage());
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

    @Test
    @DisplayName("수강 신청 성공 동시성 테스트")
    void shouldSuccessfullyEnrollLectureWithConcurrency() {
      // given
      final int threadCount = 30;
      final List<UserEntity> userEntities = userJpaRepository.saveAll(
          IntStream.range(0, threadCount)
              .mapToObj(i -> new UserEntity(null, "name" + i, "password"))
              .toList()
      );
      final LectureEntity lectureEntity = lectureJpaRepository.save(
          new LectureEntity(null, "title", "description", 2L));
      final LectureScheduleEntity lectureScheduleEntity = lectureScheduleJpaRepository.save(
          new LectureScheduleEntity(null, lectureEntity.getId(), 30, 0, LocalDateTime.now(),
              LocalDateTime.now().plusHours(1)));
      final Long lectureId = lectureEntity.getId();
      final Long lectureScheduleId = lectureScheduleEntity.getId();

      // when
      final List<CompletableFuture<Void>> futures = IntStream.range(0, threadCount)
          .mapToObj(i -> CompletableFuture.runAsync(() -> {
            final UserEntity userEntity = userEntities.get(i);
            final Long userId = userEntity.getId();
            target.enrollLecture(lectureId, lectureScheduleId, userId);
          }))
          .toList();
      CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

      // then
      final LectureScheduleEntity updatedLectureScheduleEntity = lectureScheduleJpaRepository
          .findById(lectureScheduleId).get();
      assertThat(updatedLectureScheduleEntity.getEnrolledCount()).isEqualTo(threadCount);
    }

    @Test
    @DisplayName("수강 신청 성공 동시성 테스트 - 인원 초과")
    void shouldSuccessfullyEnrollLectureWithConcurrencyWhenExceedCapacity() {
      // given
      final int threadCount = 40;
      final List<UserEntity> userEntities = userJpaRepository.saveAll(
          IntStream.range(0, threadCount)
              .mapToObj(i -> new UserEntity(null, "name" + i, "password"))
              .toList()
      );
      final LectureEntity lectureEntity = lectureJpaRepository.save(
          new LectureEntity(null, "title", "description", 2L));
      final LectureScheduleEntity lectureScheduleEntity = lectureScheduleJpaRepository.save(
          new LectureScheduleEntity(null, lectureEntity.getId(), 30, 0, LocalDateTime.now(),
              LocalDateTime.now().plusHours(1)));
      final Long lectureId = lectureEntity.getId();
      final Long lectureScheduleId = lectureScheduleEntity.getId();

      // when
      final List<CompletableFuture<Void>> futures = IntStream.range(0, threadCount)
          .mapToObj(i -> CompletableFuture.runAsync(() -> {
            try {
              final UserEntity userEntity = userEntities.get(i);
              final Long userId = userEntity.getId();
              target.enrollLecture(lectureId, lectureScheduleId, userId);
            } catch (BusinessException e) {
              assertThat(e.getMessage()).isEqualTo(LectureErrorCode.ENROLLMENT_EXCEED_CAPACITY
                  .getMessage());
            }
          }))
          .toList();
      CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

      // then
      final LectureScheduleEntity updatedLectureScheduleEntity = lectureScheduleJpaRepository
          .findById(lectureScheduleId).get();
      assertThat(updatedLectureScheduleEntity.getEnrolledCount()).isEqualTo(30);
      final List<LectureEnrollmentEntity> lectureEnrollments = lectureEnrollmentJpaRepository
          .findAllByLectureScheduleId(lectureScheduleId);
      assertThat(lectureEnrollments).hasSize(30);
    }

    @Test
    @DisplayName("수강 신청 성공 동시성 테스트 - 동일 유저 중복 신청")
    void shouldSuccessfullyEnrollLectureWithConcurrencyWhenAlreadyEnrolled() {
      // given
      final int threadCount = 5;
      final UserEntity userEntity = userJpaRepository.save(
          new UserEntity(null, "name", "password"));
      final LectureEntity lectureEntity = lectureJpaRepository.save(
          new LectureEntity(null, "title", "description", 2L));
      final List<LectureScheduleEntity> lectureScheduleEntities = lectureScheduleJpaRepository.saveAll(
          IntStream.range(0, threadCount)
              .mapToObj(i -> new LectureScheduleEntity(null, lectureEntity.getId(), 30, 0,
                  LocalDateTime.now(), LocalDateTime.now().plusHours(1)))
              .toList()
      );
      final Long userId = userEntity.getId();
      final Long lectureId = lectureEntity.getId();

      // when
      final List<CompletableFuture<Void>> futures = IntStream.range(0, threadCount)
          .mapToObj(i -> CompletableFuture.runAsync(() -> {
            try {
              final Long lectureScheduleId = lectureScheduleEntities.get(i).getId();
              target.enrollLecture(lectureId, lectureScheduleId, userId);
            } catch (BusinessException e) {
              assertThat(e.getMessage()).isEqualTo(LectureErrorCode.DUPLICATE_ENROLLMENT
                  .getMessage());
            }
          }))
          .toList();
      CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

      // then

      final List<LectureEnrollmentEntity> lectureEnrollments = lectureEnrollmentJpaRepository
          .findAllByLectureId(lectureId);
      assertThat(lectureEnrollments).hasSize(1);
      final var lectureEnrollment = lectureEnrollments.get(0);
      assertThat(lectureEnrollment.getLectureId()).isEqualTo(lectureId);
      assertThat(lectureEnrollment.getUserId()).isEqualTo(userId);
    }

  }

  @Test
  @DisplayName("getEnrolledLectures 테스트 성공")
  void shouldSuccessfullyGetEnrolledLectures() {
    // given
    final UserEntity userEntity = userJpaRepository.save(
        new UserEntity(null, "name", "password"));
    final Long userId = userEntity.getId();
    final List<LectureEntity> lectures = lectureJpaRepository.saveAll(List.of(
        new LectureEntity(null, "title1", "description1", 2L),
        new LectureEntity(null, "title2", "description2", 3L)
    ));
    final List<Long> lectureIds = lectures.stream()
        .map(LectureEntity::getId)
        .toList();
    final List<UserEntity> lecturers = userJpaRepository.saveAll(List.of(
        new UserEntity(null, "lecturer1", "password"),
        new UserEntity(null, "lecturer2", "password")
    ));
    lectureEnrollmentJpaRepository.saveAll(
        List.of(
            new LectureEnrollmentEntity(lectureIds.get(0), 1L, userId),
            new LectureEnrollmentEntity(lectureIds.get(1), 2L, userId)
        ));

    // when
    final List<LectureWithLecturer> result = target.getEnrolledLectures(userId);

    // then
    assertThat(result).hasSize(2);
    for (int i = 0; i < result.size(); i++) {
      final LectureWithLecturer lectureWithLecturer = result.get(i);
      final LectureEntity lectureEntity = lectures.get(i);
      final UserEntity lecturerEntity = lecturers.get(i);

      assertThat(lectureWithLecturer.id()).isEqualTo(lectureEntity.getId());
      assertThat(lectureWithLecturer.title()).isEqualTo(lectureEntity.getTitle());
      assertThat(lectureWithLecturer.description()).isEqualTo(lectureEntity.getDescription());
      assertThat(lectureWithLecturer.createdAt()).isNotNull();
      assertThat(lectureWithLecturer.updatedAt()).isNull();
      assertThat(lectureWithLecturer.lecturer().id()).isEqualTo(lecturerEntity.getId());
      assertThat(lectureWithLecturer.lecturer().name()).isEqualTo(lecturerEntity.getName());
      assertThat(lectureWithLecturer.lecturer().createdAt()).isNotNull();
      assertThat(lectureWithLecturer.lecturer().updatedAt()).isNull();
    }
  }

  @Test
  @DisplayName("getAvailableLectureSchedules 테스트 성공")
  void shouldSuccessfullyGetAvailableLectureSchedules() {
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
    final var result = target.getAvailableLectureSchedules(now.toLocalDate());

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
      assertThat(lectureSchedule.startAt()).isEqualTo(lectureScheduleEntity.getStartAt());
      assertThat(lectureSchedule.endAt()).isEqualTo(lectureScheduleEntity.getEndAt());
    }
  }
}
