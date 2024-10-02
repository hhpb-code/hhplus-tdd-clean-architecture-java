package com.hhplus.tdd.clean.architecture.domain.lecture;

import static org.assertj.core.api.Assertions.assertThat;

import com.hhplus.tdd.clean.architecture.domain.lecture.dto.Lecture;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureQuery;
import com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre.LectureEntity;
import com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre.LectureJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("LectureQueryService 통합 테스트")
class LectureQueryServiceIntegrationTest {

  @Autowired
  private LectureQueryService target;

  @Autowired
  private LectureJpaRepository lectureJpaRepository;


  @Test
  @DisplayName("getLectureById 테스트 성공")
  void shouldSuccessfullyGetLectureById() {
    // given
    final LectureEntity lectureEntity = lectureJpaRepository.save(
        new LectureEntity(null, "title", "description", 2L));
    final LectureQuery.GetLectureById query = new LectureQuery.GetLectureById(
        lectureEntity.getId());

    // when
    final Lecture result = target.getLectureById(query);

    // then
    assertThat(result.id()).isEqualTo(lectureEntity.getId());
    assertThat(result.title()).isEqualTo(lectureEntity.getTitle());
    assertThat(result.description()).isEqualTo(lectureEntity.getDescription());
    assertThat(result.lecturerId()).isEqualTo(lectureEntity.getLecturerId());
    assertThat(result.createdAt()).isNotNull();
    assertThat(result.updatedAt()).isNull();
  }

}