package com.hhplus.tdd.clean.architecture.infrastructure.db.user.impl;

import com.hhplus.tdd.clean.architecture.domain.common.error.BusinessException;
import com.hhplus.tdd.clean.architecture.domain.user.User;
import com.hhplus.tdd.clean.architecture.domain.user.UserErrorCode;
import com.hhplus.tdd.clean.architecture.domain.user.UserRepository;
import com.hhplus.tdd.clean.architecture.infrastructure.db.user.UserEntity;
import com.hhplus.tdd.clean.architecture.infrastructure.db.user.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

  private final UserJpaRepository userJpaRepository;

  @Override
  public User getUserById(Long userId) {
    return userJpaRepository.findById(userId)
        .map(UserEntity::toUser)
        .orElseThrow(() -> new BusinessException(UserErrorCode.USER_NOT_FOUND));
  }
}
