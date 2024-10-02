package com.hhplus.tdd.clean.architecture.domain.lecture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.hhplus.tdd.clean.architecture.domain.common.error.BusinessException;
import com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre.LectureJpaRepository;
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

  @BeforeEach
  void setUp() {
    lectureJpaRepository.deleteAll();
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
  }


}