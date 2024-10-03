package com.hhplus.tdd.clean.architecture.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.hhplus.tdd.clean.architecture.domain.common.error.BusinessException;
import com.hhplus.tdd.clean.architecture.domain.user.dto.UserQuery;
import com.hhplus.tdd.clean.architecture.infrastructure.db.user.UserEntity;
import com.hhplus.tdd.clean.architecture.infrastructure.db.user.UserJpaRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("UserQueryService 통합 테스트")
class UserQueryServiceIntegrationTest {

  @Autowired
  private UserQueryService target;

  @Autowired
  private UserJpaRepository userJpaRepository;

  @BeforeEach
  void setUp() {
    userJpaRepository.deleteAll();
  }

  @Test
  @DisplayName("getUserById 테스트 실패 - 존재하지 않는 사용자")
  void shouldThrowExceptionWhenUserNotFound() {
    // given
    final Long userId = 1L;
    final UserQuery.GetUserById query = new UserQuery.GetUserById(userId);

    // when
    final BusinessException result = assertThrows(BusinessException.class,
        () -> target.getUserById(query));

    // then
    assertThat(result.getMessage()).isEqualTo(UserErrorCode.USER_NOT_FOUND.getMessage());
  }

  @Test
  @DisplayName("getUserById 테스트 성공")
  void shouldSuccessfullyGetUserById() {
    // given
    final UserEntity userEntity = userJpaRepository.save(
        new UserEntity(null, "test", "password", null, null));
    final UserQuery.GetUserById query = new UserQuery.GetUserById(userEntity.getId());

    // when
    final User result = target.getUserById(query);

    // then
    assertThat(result.id()).isEqualTo(userEntity.getId());
    assertThat(result.name()).isEqualTo(userEntity.getName());
    assertThat(result.createdAt()).isNotNull();
    assertThat(result.updatedAt()).isNull();
  }

  @Test
  @DisplayName("findUsersByIds 테스트 성공")
  void shouldSuccessfullyGetUsersByIds() {
    // given
    final List<UserEntity> userEntities = userJpaRepository.saveAll(List.of(
        new UserEntity(null, "test1", "password", null, null),
        new UserEntity(null, "test2", "password", null, null)
    ));
    userJpaRepository.save(new UserEntity(null, "test3", "password", null, null));
    final UserQuery.FindUsersByIds query = new UserQuery.FindUsersByIds(userEntities.stream()
        .map(UserEntity::getId)
        .toList());

    // when
    final List<User> result = target.findUsers(query);

    // then
    assertThat(result).hasSize(2);
    for (int i = 0; i < result.size(); i++) {
      final User user = result.get(i);
      final UserEntity userEntity = userEntities.get(i);
      assertThat(user.id()).isEqualTo(userEntity.getId());
      assertThat(user.name()).isEqualTo(userEntity.getName());
      assertThat(user.createdAt()).isNotNull();
      assertThat(user.updatedAt()).isNull();
    }
  }

}