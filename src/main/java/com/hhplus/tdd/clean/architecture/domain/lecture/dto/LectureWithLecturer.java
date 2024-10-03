package com.hhplus.tdd.clean.architecture.domain.lecture.dto;

import com.hhplus.tdd.clean.architecture.domain.user.User;
import java.time.LocalDateTime;

public record LectureWithLecturer(
    Long id,
    String title,
    String description,
    User lecturer,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

  public LectureWithLecturer(Lecture lecture, User lecturer
  ) {
    this(lecture.id(), lecture.title(), lecture.description(), lecturer, lecture.createdAt(),
        lecture.updatedAt());
  }
}
