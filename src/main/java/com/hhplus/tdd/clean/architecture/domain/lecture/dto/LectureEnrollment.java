package com.hhplus.tdd.clean.architecture.domain.lecture.dto;

import java.time.LocalDateTime;

public record LectureEnrollment(
    Long id,
    Long lectureId,
    Long lectureScheduleId,
    Long userId,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

}
