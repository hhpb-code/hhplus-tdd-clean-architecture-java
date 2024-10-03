package com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre;

import java.time.LocalDateTime;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LectureScheduleJpaRepository extends JpaRepository<LectureScheduleEntity, Long> {

  @Query("SELECT ls FROM LectureScheduleEntity ls WHERE ls.startAt >= :startDateTime AND ls.startAt < :endDateTime AND ls.enrolledCount < ls.capacity")
  Collection<LectureScheduleEntity> findAvailableByStartAtBetween(
      @Param("startDateTime") LocalDateTime startDateTime,
      @Param("endDateTime") LocalDateTime endDateTime
  );
}
