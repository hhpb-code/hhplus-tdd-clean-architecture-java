package com.hhplus.tdd.clean.architecture.domain.lecture;

import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LectureCommandService {

  private final LectureRepository lectureRepository;

  public Long createLectureEnrollment(LectureCommand.CreateLectureEnrollment command) {
    return lectureRepository.createLectureEnrollment(command.lectureId(),
        command.lectureScheduleId(), command.userId());
  }

  public void increaseEnrollmentCountByLectureScheduleId(Long lectureScheduleId) {
    lectureRepository.increaseEnrollmentCountByLectureScheduleId(lectureScheduleId);
  }

}
