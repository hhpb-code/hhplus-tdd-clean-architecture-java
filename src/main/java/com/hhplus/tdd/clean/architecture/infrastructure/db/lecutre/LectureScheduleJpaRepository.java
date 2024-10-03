package com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre;

import jakarta.persistence.LockModeType;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LectureScheduleJpaRepository extends JpaRepository<LectureScheduleEntity, Long> {

  @Query("SELECT ls FROM LectureScheduleEntity ls WHERE ls.startAt >= :startDateTime AND ls.startAt < :endDateTime AND ls.enrolledCount < ls.capacity")
  Collection<LectureScheduleEntity> findAvailableByStartAtBetween(
      @Param("startDateTime") LocalDateTime startDateTime,
      @Param("endDateTime") LocalDateTime endDateTime
  );

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<LectureScheduleEntity> findWithLockById(Long lectureScheduleId);
}
