package com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre;

import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureSchedule;
import com.hhplus.tdd.clean.architecture.infrastructure.db.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lecture_schedule")
@Getter
@NoArgsConstructor
public class LectureScheduleEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long lectureId;

  @Column(nullable = false)
  private Integer capacity;

  @Column(nullable = false)
  private Integer enrolledCount;

  @Column(nullable = false)
  private LocalDateTime startAt;

  @Column(nullable = false)
  private LocalDateTime endAt;

  public LectureScheduleEntity(Long id, Long lectureId, Integer capacity, Integer enrolledCount,
      LocalDateTime startAt, LocalDateTime endAt, LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    super(createdAt, updatedAt);
    this.id = id;
    this.lectureId = lectureId;
    this.capacity = capacity;
    this.enrolledCount = enrolledCount;
    this.startAt = startAt;
    this.endAt = endAt;
  }

  public LectureScheduleEntity(Long id, Long lectureId, Integer capacity, Integer enrolledCount,
      LocalDateTime startAt, LocalDateTime endAt) {
    this(id, lectureId, capacity, enrolledCount, startAt, endAt, null, null);
  }

  public LectureSchedule toLectureSchedule() {
    return new LectureSchedule(id, lectureId, capacity, enrolledCount, startAt, endAt,
        getCreatedAt(),
        getUpdatedAt());
  }

  public void increaseEnrollmentCount() {
    enrolledCount++;
  }
}
