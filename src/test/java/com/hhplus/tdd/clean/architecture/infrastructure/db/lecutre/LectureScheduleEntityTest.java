package com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre;

import static org.assertj.core.api.Assertions.assertThat;

import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureSchedule;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("LectureScheduleEntity 단위 테스트")
class LectureScheduleEntityTest {

  @Test
  @DisplayName("toLectureSchedule 테스트 성공")
  void shouldSuccessfullyToLectureSchedule() {
    // given
    final LectureScheduleEntity lectureScheduleEntity = new LectureScheduleEntity(1L, 1L, 30, 0,
        LocalDateTime.now(), LocalDateTime.now());

    // when
    final LectureSchedule result = lectureScheduleEntity.toLectureSchedule();

    // then
    assertThat(result.id()).isEqualTo(lectureScheduleEntity.getId());
    assertThat(result.lectureId()).isEqualTo(lectureScheduleEntity.getLectureId());
    assertThat(result.capacity()).isEqualTo(lectureScheduleEntity.getCapacity());
    assertThat(result.enrolledCount()).isEqualTo(lectureScheduleEntity.getEnrolledCount());
    assertThat(result.startAt()).isEqualTo(lectureScheduleEntity.getStartAt());
    assertThat(result.endAt()).isEqualTo(lectureScheduleEntity.getEndAt());
    assertThat(result.createdAt()).isEqualTo(lectureScheduleEntity.getCreatedAt());
    assertThat(result.updatedAt()).isEqualTo(lectureScheduleEntity.getUpdatedAt());
  }

  @Test
  @DisplayName("increaseEnrollmentCount 테스트 성공")
  void shouldSuccessfullyIncreaseEnrollmentCount() {
    // given
    final LectureScheduleEntity lectureScheduleEntity = new LectureScheduleEntity(1L, 1L, 30, 0,
        LocalDateTime.now(), LocalDateTime.now());

    // when
    lectureScheduleEntity.increaseEnrollmentCount();

    // then
    assertThat(lectureScheduleEntity.getEnrolledCount()).isEqualTo(1);
  }

}