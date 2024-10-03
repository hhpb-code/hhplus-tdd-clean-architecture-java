package com.hhplus.tdd.clean.architecture.infrastructure.db.lecutre;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureJpaRepository extends JpaRepository<LectureEntity, Long> {

}
