package com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre;

import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureEnrollment;
import com.hhplus.tdd.clean.architecture.infrastructure.db.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "lecture_enrollment",
    uniqueConstraints = @UniqueConstraint(columnNames = {"lectureScheduleId", "userId"})
)
@Getter
@NoArgsConstructor
public class LectureEnrollmentEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long lectureId;

  @Column(nullable = false)
  private Long lectureScheduleId;

  @Column(nullable = false)
  private Long userId;


  public LectureEnrollmentEntity(Long id, Long lectureId, Long lectureScheduleId, Long userId,
      LocalDateTime createdAt, LocalDateTime updatedAt) {
    super(createdAt, updatedAt);
    this.id = id;
    this.lectureId = lectureId;
    this.lectureScheduleId = lectureScheduleId;
    this.userId = userId;
  }

  public LectureEnrollmentEntity(Long lectureId, Long lectureScheduleId, Long userId) {
    this(null, lectureId, lectureScheduleId, userId, null, null);
  }

  public LectureEnrollment toLectureEnrollment() {
    return new LectureEnrollment(id, lectureId, lectureScheduleId, userId, getCreatedAt(),
        getUpdatedAt());
  }
}
