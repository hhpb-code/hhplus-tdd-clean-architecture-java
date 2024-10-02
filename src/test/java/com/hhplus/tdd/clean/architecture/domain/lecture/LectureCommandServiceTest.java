package com.hhplus.tdd.clean.architecture.domain.lecture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("LectureCommandService 단위 테스트")
class LectureCommandServiceTest {

  @InjectMocks
  private LectureCommandService target;

  @Mock
  private LectureRepository lectureRepository;


  @Test
  @DisplayName("createLectureEnrollment 테스트 성공")
  void shouldSuccessfullyCreateLectureEnrollment() {
    // given
    final Long lectureEnrollmentId = 1L;
    final LectureCommand.CreateLectureEnrollment command = new LectureCommand.CreateLectureEnrollment(
        1L, 1L, 1L);
    doReturn(lectureEnrollmentId).when(lectureRepository)
        .createLectureEnrollment(command.lectureId(),
            command.lectureScheduleId(), command.userId());

    // when
    final Long result = target.createLectureEnrollment(command);

    // then
    assertThat(result).isEqualTo(lectureEnrollmentId);
  }

}