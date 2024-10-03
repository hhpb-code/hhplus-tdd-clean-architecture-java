package com.hhplus.tdd.clean.architecture.domain.user;

import com.hhplus.tdd.clean.architecture.domain.user.dto.UserQuery;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryService {

  private final UserRepository userRepository;

  public User getUserById(UserQuery.GetUserById query) {
    return userRepository.getUserById(query.userId());
  }

  public List<User> findUsers(UserQuery.FindUsersByIds query) {
    return userRepository.findUsersByIds(query.userIds());
  }
}
