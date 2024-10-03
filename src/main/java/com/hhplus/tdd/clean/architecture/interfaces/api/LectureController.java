package com.hhplus.tdd.clean.architecture.interfaces.api;

import com.hhplus.tdd.clean.architecture.application.lecture.LectureFacade;
import com.hhplus.tdd.clean.architecture.interfaces.api.dto.LectureHttp;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/lectures")
@RequiredArgsConstructor
public class LectureController {

  private final LectureFacade lectureFacade;

  @PostMapping("/enrollments")
  public ResponseEntity<LectureHttp.EnrollLecture.Response> enrollLecture(
      @RequestBody final LectureHttp.EnrollLecture.Request request) {
    final var lectureEnrollment = lectureFacade.enrollLecture(request.lectureId(),
        request.lectureScheduleId(),
        request.userId());

    return ResponseEntity.ok(new LectureHttp.EnrollLecture.Response(lectureEnrollment));
  }

  @GetMapping("/schedules/available")
  public ResponseEntity<LectureHttp.GetAvailableLectureSchedules.Response> getAvailableLectureSchedules(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
  ) {
    final var lectureSchedules = lectureFacade.getAvailableLectureSchedules(date);

    return ResponseEntity.ok(
        new LectureHttp.GetAvailableLectureSchedules.Response(lectureSchedules));
  }

}
