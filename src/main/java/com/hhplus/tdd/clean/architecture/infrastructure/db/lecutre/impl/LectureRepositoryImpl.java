package com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre.impl;

import com.hhplus.tdd.clean.architecture.domain.common.error.BusinessException;
import com.hhplus.tdd.clean.architecture.domain.lecture.LectureErrorCode;
import com.hhplus.tdd.clean.architecture.domain.lecture.LectureRepository;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.Lecture;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureSchedule;
import com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre.LectureEnrollmentEntity;
import com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre.LectureEnrollmentJpaRepository;
import com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre.LectureEntity;
import com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre.LectureJpaRepository;
import com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre.LectureScheduleEntity;
import com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre.LectureScheduleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepository {

  private final LectureJpaRepository lectureJpaRepository;
  private final LectureScheduleJpaRepository lectureScheduleJpaRepository;
  private final LectureEnrollmentJpaRepository lectureEnrollmentJpaRepository;

  @Override
  public Lecture getLectureById(Long lectureId) {
    return lectureJpaRepository.findById(lectureId)
        .map(LectureEntity::toLecture)
        .orElseThrow(() -> new BusinessException(LectureErrorCode.LECTURE_NOT_FOUND));
  }

  @Override
  public LectureSchedule getLectureScheduleById(Long lectureScheduleId) {
    return lectureScheduleJpaRepository.findById(lectureScheduleId)
        .map(LectureScheduleEntity::toLectureSchedule)
        .orElseThrow(() -> new BusinessException(LectureErrorCode.LECTURE_SCHEDULE_NOT_FOUND));
  }

  @Override
  public Long createLectureEnrollment(Long lectureId, Long lectureScheduleId, Long userId) {
    return lectureEnrollmentJpaRepository.save(
        new LectureEnrollmentEntity(lectureId, lectureScheduleId, userId)).getId();
  }
}
