package com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre;

import com.hhplus.tdd.clean.architecture.domain.lecture.dto.Lecture;
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
@Table(name = "lecture")
@Getter
@NoArgsConstructor
public class LectureEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private Long lecturerId;

  public LectureEntity(Long id, String title, String description, Long lecturerId,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    super(createdAt, updatedAt);
    this.id = id;
    this.title = title;
    this.description = description;
    this.lecturerId = lecturerId;
  }

  public LectureEntity(Long id, String title, String description, Long lecturerId) {
    this(id, title, description, lecturerId, null, null);
  }

  public Lecture toLecture() {
    return new Lecture(id, title, description, lecturerId, getCreatedAt(), getUpdatedAt());
  }
}
