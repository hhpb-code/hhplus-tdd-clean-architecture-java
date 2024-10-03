package com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureEnrollmentJpaRepository extends
    JpaRepository<LectureEnrollmentEntity, Long> {

  List<LectureEnrollmentEntity> findAllByUserId(Long userId);

  List<LectureEnrollmentEntity> findAllByLectureScheduleId(Long lectureScheduleId);

  Optional<LectureEnrollmentEntity> findByLectureScheduleIdAndUserId(Long lectureScheduleId,
      Long userId);
}
