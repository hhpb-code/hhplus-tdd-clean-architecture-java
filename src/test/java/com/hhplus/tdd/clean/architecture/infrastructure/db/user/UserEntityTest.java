package com.hhplus.tdd.clean.architecture.infrastructure.db.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.hhplus.tdd.clean.architecture.domain.user.User;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("UserEntity 단위 테스트")
class UserEntityTest {

  @Test
  @DisplayName("toUser 테스트")
  void shouldSuccessfullyConvertToUser() {
    // given
    final Long id = 1L;
    final String name = "test";
    final String password = "password";
    final UserEntity userEntity = new UserEntity(id, name, password, LocalDateTime.now(), null);

    // when
    final User user = userEntity.toUser();

    // then
    assertThat(user.id()).isEqualTo(userEntity.getId());
    assertThat(user.name()).isEqualTo(userEntity.getName());
    assertThat(user.createdAt()).isEqualTo(userEntity.getCreatedAt());
    assertThat(user.updatedAt()).isEqualTo(userEntity.getUpdatedAt());
  }

}