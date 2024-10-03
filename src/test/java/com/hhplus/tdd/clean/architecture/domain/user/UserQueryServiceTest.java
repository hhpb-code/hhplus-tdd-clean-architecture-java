package com.hhplus.tdd.clean.architecture.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import com.hhplus.tdd.clean.architecture.domain.user.dto.UserQuery;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserQueryService 단위 테스트")
class UserQueryServiceTest {

  @InjectMocks
  private UserQueryService target;

  @Mock
  private UserRepository userRepository;


  @Test
  @DisplayName("getUserById 테스트 성공")
  void shouldSuccessfullyGetUserById() {
    // given
    final Long userId = 1L;
    final User user = new User(userId, "test", LocalDateTime.now(), LocalDateTime.now());
    final UserQuery.GetUserById query = new UserQuery.GetUserById(userId);
    doReturn(user).when(userRepository).getUserById(userId);

    // when
    final User result = target.getUserById(query);

    // then
    assertThat(result.id()).isEqualTo(user.id());
    assertThat(result.name()).isEqualTo(user.name());
    assertThat(result.createdAt()).isEqualTo(user.createdAt());
    assertThat(result.updatedAt()).isEqualTo(user.updatedAt());
  }

  @Test
  @DisplayName("findUsersByIds 테스트 성공")
  void shouldSuccessfullyGetUsersByIds() {
    // given
    final Long userId = 1L;
    final User user = new User(userId, "test", LocalDateTime.now(), LocalDateTime.now());
    final UserQuery.FindUsersByIds query = new UserQuery.FindUsersByIds(List.of(userId));
    doReturn(List.of(user)).when(userRepository).findUsersByIds(List.of(userId));

    // when
    final List<User> result = target.findUsers(query);

    // then
    assertThat(result).hasSize(1);
    assertThat(result.get(0).id()).isEqualTo(user.id());
    assertThat(result.get(0).name()).isEqualTo(user.name());
    assertThat(result.get(0).createdAt()).isEqualTo(user.createdAt());
    assertThat(result.get(0).updatedAt()).isEqualTo(user.updatedAt());
  }
}