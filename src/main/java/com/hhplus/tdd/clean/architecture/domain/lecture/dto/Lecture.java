package com.hhplus.tdd.clean.architecture.domain.lecture.dto;

import java.time.LocalDateTime;

public record Lecture(
    Long id,
    String title,
    String description,
    Long lecturerId,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

}
