package com.hhplus.tdd.clean.architecture.domain.lecture;

import com.hhplus.tdd.clean.architecture.domain.common.error.BusinessException;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LectureCommandService {

  private final LectureRepository lectureRepository;

  public Long createLectureEnrollment(LectureCommand.CreateLectureEnrollment command) {
    try {
      return lectureRepository.createLectureEnrollment(command.lectureId(),
          command.lectureScheduleId(), command.userId());
    } catch (DataIntegrityViolationException e) {
      throw new BusinessException(LectureErrorCode.DUPLICATE_ENROLLMENT);
    }
  }

  public void increaseEnrollmentCountByLectureScheduleId(Long lectureScheduleId) {
    lectureRepository.increaseEnrollmentCountByLectureScheduleId(lectureScheduleId);
  }

}
