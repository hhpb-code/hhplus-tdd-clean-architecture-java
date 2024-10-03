package com.hhplus.tdd.clean.architecture.interfaces.api.dto;

import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureEnrollment;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureSchedule;
import java.util.List;

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

  public class GetAvailableLectureSchedules {

    public record Response(List<LectureSchedule> lectureSchedules) {

    }

  }

}
