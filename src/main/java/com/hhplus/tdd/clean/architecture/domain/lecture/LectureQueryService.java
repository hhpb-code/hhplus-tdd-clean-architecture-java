package com.hhplus.tdd.clean.architecture.domain.lecture;

import com.hhplus.tdd.clean.architecture.domain.lecture.dto.Lecture;
import com.hhplus.tdd.clean.architecture.domain.lecture.dto.LectureQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LectureQueryService {

  private final LectureRepository lectureRepository;

  public Lecture getLectureById(LectureQuery.GetLectureById query) {
    return lectureRepository.getLectureById(query.lectureId());
  }

}
