package com.hhplus.tdd.clean.architecture.interfaces.api.dto;

import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureEnrollment;

public class LectureHttp {

  public class EnrollLecture {

    public record Request(
        Long lectureId,
        Long lectureScheduleId,
        Long userId
    ) {

    }

    public record Response(LectureEnrollment lectureEnrollment) {

    }

  }

}
