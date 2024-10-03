package com.hhplus.tdd.clean.architecture.interfaces.api.dto;

import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureEnrollment;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureSchedule;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureWithLecturer;
import java.time.LocalDate;
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

  public class GetEnrolledLectures {

    public record Request(Long userId) {

    }

    public record Response(List<LectureWithLecturer> lectureEnrollments) {

    }

  }

  public class GetAvailableLectureSchedules {

    public record Request(LocalDate date) {

    }

    public record Response(List<LectureSchedule> lectureSchedules) {

    }

  }

}
