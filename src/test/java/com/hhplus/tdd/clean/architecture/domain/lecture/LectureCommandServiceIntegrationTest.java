package com.hhplus.tdd.clean.architecture.domain.lecture;

import static org.assertj.core.api.Assertions.assertThat;

import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureCommand;
import com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre.LectureScheduleEntity;
import com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre.LectureScheduleJpaRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("LectureCommandService 통합 테스트")
class LectureCommandServiceIntegrationTest {

  @Autowired
  private LectureCommandService target;

  @Autowired
  private LectureScheduleJpaRepository lectureScheduleJpaRepository;

  @BeforeEach
  void setUp() {
    lectureScheduleJpaRepository.deleteAll();
  }


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

  @Test
  @DisplayName("increaseEnrollmentCountByLectureScheduleId 테스트 성공")
  void shouldSuccessfullyIncreaseEnrollmentCountByLectureScheduleId() {
    // given
    final LectureScheduleEntity lectureScheduleEntity = lectureScheduleJpaRepository.save(
        new LectureScheduleEntity(null, 1L, 30, 0, LocalDateTime.now(), LocalDateTime.now()));

    // when
    target.increaseEnrollmentCountByLectureScheduleId(lectureScheduleEntity.getId());

    // then
    final LectureScheduleEntity updatedLectureScheduleEntity = lectureScheduleJpaRepository
        .findById(lectureScheduleEntity.getId()).get();
    assertThat(updatedLectureScheduleEntity.getEnrolledCount()).isEqualTo(
        lectureScheduleEntity.getEnrolledCount() + 1);
  }

}