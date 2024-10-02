package com.hhplus.tdd.clean.architecture.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.hhplus.tdd.clean.architecture.domain.common.error.BusinessException;
import com.hhplus.tdd.clean.architecture.infrastructure.db.user.UserEntity;
import com.hhplus.tdd.clean.architecture.infrastructure.db.user.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("UserRepository 통합 테스트")
class UserRepositoryTest {

  @Autowired
  private UserRepository target;

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

    // when
    final BusinessException result = assertThrows(BusinessException.class,
        () -> target.getUserById(userId));

    // then
    assertThat(result.getMessage()).isEqualTo(UserErrorCode.USER_NOT_FOUND.getMessage());
  }

  @Test
  @DisplayName("getUserById 테스트 성공")
  void shouldSuccessfullyGetUserById() {
    // given
    final UserEntity userEntity = userJpaRepository.save(
        new UserEntity(null, "test", "password", null, null));

    // when
    final User result = target.getUserById(userEntity.getId());

    // then
    assertThat(result.id()).isEqualTo(userEntity.getId());
    assertThat(result.name()).isEqualTo(userEntity.getName());
    assertThat(result.createdAt()).isNotNull();
    assertThat(result.updatedAt()).isNull();
  }

}