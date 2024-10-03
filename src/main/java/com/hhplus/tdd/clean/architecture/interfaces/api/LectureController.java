package com.hhplus.tdd.clean.architecture.interfaces.api;

import com.hhplus.tdd.clean.architecture.application.lecture.LectureFacade;
import com.hhplus.tdd.clean.architecture.interfaces.api.dto.LectureHttp;
import com.hhplus.tdd.clean.architecture.interfaces.api.dto.LectureHttp.GetEnrolledLectures;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

  @GetMapping("/enrolled")
  public ResponseEntity<GetEnrolledLectures.Response> getEnrolledLectures(
      GetEnrolledLectures.Request request) {
    final var lectureEnrollments = lectureFacade.getEnrolledLectures(request.userId());

    return ResponseEntity.ok(new GetEnrolledLectures.Response(lectureEnrollments));
  }

  @GetMapping("/schedules/available")
  public ResponseEntity<LectureHttp.GetAvailableLectureSchedules.Response> getAvailableLectureSchedules(
      LectureHttp.GetAvailableLectureSchedules.Request request) {
    final var lectureSchedules = lectureFacade.getAvailableLectureSchedules(request.date());

    return ResponseEntity.ok(
        new LectureHttp.GetAvailableLectureSchedules.Response(lectureSchedules));
  }

}
