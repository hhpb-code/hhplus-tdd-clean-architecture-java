package com.hhplus.tdd.clean.architecture.infrastructure.db.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

}
