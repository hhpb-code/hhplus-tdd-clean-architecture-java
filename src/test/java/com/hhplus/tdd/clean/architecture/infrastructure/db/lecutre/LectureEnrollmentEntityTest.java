package com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre;

import static org.assertj.core.api.Assertions.assertThat;

import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureEnrollment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("LectureEnrollmentEntity 단위 테스트")
class LectureEnrollmentEntityTest {

  @Test
  @DisplayName("toLectureEnrollment 테스트 성공")
  void shouldSuccessfullyConvertToLectureEnrollment() {
    // given
    final LectureEnrollmentEntity entity = new LectureEnrollmentEntity(1L, 2L, 3L, 4L, null, null);

    // when
    final LectureEnrollment result = entity.toLectureEnrollment();

    // then
    assertThat(result.id()).isEqualTo(entity.getId());
    assertThat(result.lectureId()).isEqualTo(entity.getLectureId());
    assertThat(result.lectureScheduleId()).isEqualTo(entity.getLectureScheduleId());
    assertThat(result.userId()).isEqualTo(entity.getUserId());
    assertThat(result.createdAt()).isEqualTo(entity.getCreatedAt());
    assertThat(result.updatedAt()).isEqualTo(entity.getUpdatedAt());
  }

}