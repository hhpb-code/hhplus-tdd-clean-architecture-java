package com.hhplus.tdd.clean.architecture.domain.lecture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import com.hhplus.tdd.clean.architecture.domain.lecture.dto.Lecture;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureQuery;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureSchedule;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("LectureQueryService 단위 테스트")
class LectureQueryServiceTest {

  @InjectMocks
  private LectureQueryService target;

  @Mock
  private LectureRepository lectureRepository;


  @Test
  @DisplayName("getLectureById 테스트 성공")
  void shouldSuccessfullyGetLectureById() {
    // given
    final Long lectureId = 1L;
    final LectureQuery.GetLectureById query = new LectureQuery.GetLectureById(lectureId);
    final Lecture lecture = new Lecture(lectureId, "title",
        "description", 2L, LocalDateTime.now(),
        null);
    doReturn(lecture).when(lectureRepository).getLectureById(lectureId);

    // when
    final Lecture result = target.getLectureById(query);

    // then
    assertThat(result.id()).isEqualTo(lectureId);
    assertThat(result.title()).isEqualTo(lecture.title());
    assertThat(result.description()).isEqualTo(lecture.description());
    assertThat(result.lecturerId()).isEqualTo(lecture.lecturerId());
    assertThat(result.createdAt()).isEqualTo(lecture.createdAt());
    assertThat(result.updatedAt()).isEqualTo(lecture.updatedAt());
  }

  @Test
  @DisplayName("getLectureScheduleById 테스트 성공")
  void shouldSuccessfullyGetLectureScheduleById() {
    // given
    final Long lectureScheduleId = 1L;
    final LectureQuery.GetLectureScheduleById query = new LectureQuery.GetLectureScheduleById(
        lectureScheduleId);
    final LectureSchedule lectureSchedule = new LectureSchedule(lectureScheduleId, 1L, 30, 0,
        LocalDateTime.now(), LocalDateTime.now(), null, null);
    doReturn(lectureSchedule).when(lectureRepository).getLectureScheduleById(lectureScheduleId);

    // when
    final LectureSchedule result = target.getLectureScheduleById(query);

    // then
    assertThat(result.id()).isEqualTo(lectureScheduleId);
    assertThat(result.lectureId()).isEqualTo(lectureSchedule.lectureId());
    assertThat(result.capacity()).isEqualTo(lectureSchedule.capacity());
    assertThat(result.enrolledCount()).isEqualTo(lectureSchedule.enrolledCount());
    assertThat(result.startAt()).isEqualTo(lectureSchedule.startAt());
    assertThat(result.endAt()).isEqualTo(lectureSchedule.endAt());
  }

}