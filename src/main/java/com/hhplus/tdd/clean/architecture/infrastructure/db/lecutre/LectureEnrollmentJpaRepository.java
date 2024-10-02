package com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureEnrollmentJpaRepository extends
    JpaRepository<LectureEnrollmentEntity, Long> {

}
