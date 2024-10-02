package com.hhplus.tdd.clean.architecture.domain.lecture;

import static org.assertj.core.api.Assertions.assertThat;

import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("LectureCommandService 통합 테스트")
class LectureCommandServiceIntegrationTest {

  @Autowired
  private LectureCommandService target;


  @Test
  @DisplayName("createLectureEnrollment 테스트 성공")
  void shouldSuccessfullyCreateLectureEnrollment() {
    // given
    final LectureCommand.CreateLectureEnrollment command = new LectureCommand.CreateLectureEnrollment(
        1L, 1L, 1L);

    // when
    final Long result = target.createLectureEnrollment(command);

    // then
    assertThat(result).isNotNull();
  }

}