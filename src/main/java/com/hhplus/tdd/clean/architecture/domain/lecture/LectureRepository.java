package com.hhplus.tdd.clean.architecture.domain.lecture;

import com.hhplus.tdd.clean.architecture.domain.lecture.dto.Lecture;

public interface LectureRepository {

  Lecture getLectureById(Long lectureId);
  
}
