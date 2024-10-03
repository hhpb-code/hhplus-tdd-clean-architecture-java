package com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre;

import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureEnrollment;
import com.hhplus.tdd.clean.architecture.infrastructure.db.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lecture_enrollment")
@Getter
@NoArgsConstructor
public class LectureEnrollmentEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long lectureId;

  private Long lectureScheduleId;

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
