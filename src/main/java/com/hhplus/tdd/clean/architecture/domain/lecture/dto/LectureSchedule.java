package com.hhplus.tdd.clean.architecture.domain.lecture.dto;

import java.time.LocalDateTime;

public record LectureSchedule(
    Long id,
    Long lectureId,
    Integer capacity,
    Integer enrolledCount,
    LocalDateTime startAt,
    LocalDateTime endAt,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

}
