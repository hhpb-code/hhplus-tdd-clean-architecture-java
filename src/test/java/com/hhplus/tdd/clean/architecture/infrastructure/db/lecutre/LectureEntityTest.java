package com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre;

import static org.assertj.core.api.Assertions.assertThat;

import com.hhplus.tdd.clean.architecture.domain.lecture.dto.Lecture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("LectureEntity 단위 테스트")
class LectureEntityTest {

  @Test
  @DisplayName("toLecture 테스트")
  void shouldReturnLectureWhenToLecture() {
    // given
    final LectureEntity lectureEntity = new LectureEntity(1L, "title", "description", 2L);

    // when
    final Lecture result = lectureEntity.toLecture();

    // then
    assertThat(result.id()).isEqualTo(lectureEntity.getId());
    assertThat(result.title()).isEqualTo(lectureEntity.getTitle());
    assertThat(result.description()).isEqualTo(lectureEntity.getDescription());
    assertThat(result.lecturerId()).isEqualTo(lectureEntity.getLecturerId());
    assertThat(result.createdAt()).isEqualTo(lectureEntity.getCreatedAt());
    assertThat(result.updatedAt()).isEqualTo(lectureEntity.getUpdatedAt());
  }
}