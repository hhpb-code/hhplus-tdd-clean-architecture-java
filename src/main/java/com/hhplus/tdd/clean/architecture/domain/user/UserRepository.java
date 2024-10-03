package com.hhplus.tdd.clean.architecture.domain.user;

import java.util.List;

public interface UserRepository {

  User getUserById(Long userId);

  List<User> findUsersByIds(List<Long> userIds);
}
