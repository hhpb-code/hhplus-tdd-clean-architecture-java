package com.hhplus.tdd.clean.architecture.domain.user.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.hhplus.tdd.clean.architecture.domain.common.error.BusinessException;
import com.hhplus.tdd.clean.architecture.domain.user.UserErrorCode;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("UserQuery 단위 테스트")
class UserQueryTest {

  @Nested
  @DisplayName("GetUserById 테스트")
  class GetUserByIdTest {

    @Test
    @DisplayName("생성자 테스트 실패 - userId가 null인 경우")
    void shouldThrowExceptionWhenUserIdIsNull() {
      // given
      Long userId = null;

      // when
      BusinessException result = assertThrows(BusinessException.class,
          () -> new UserQuery.GetUserById(userId));

      // then
      assertThat(result.getMessage()).isEqualTo(UserErrorCode.INVALID_USER_ID.getMessage());
    }

    @Test
    @DisplayName("생성자 테스트 실패 - userId가 0인 경우")
    void shouldThrowExceptionWhenUserIdIsZero() {
      // given
      Long userId = 0L;

      // when
      BusinessException result = assertThrows(BusinessException.class,
          () -> new UserQuery.GetUserById(userId));

      // then
      assertThat(result.getMessage()).isEqualTo(UserErrorCode.INVALID_USER_ID.getMessage());
    }

    @Test
    @DisplayName("생성자 테스트 실패 - userId가 음수인 경우")
    void shouldThrowExceptionWhenUserIdIsNegative() {
      // given
      Long userId = -1L;

      // when
      BusinessException result = assertThrows(BusinessException.class,
          () -> new UserQuery.GetUserById(userId));

      // then
      assertThat(result.getMessage()).isEqualTo(UserErrorCode.INVALID_USER_ID.getMessage());
    }

    @Test
    @DisplayName("생성자 테스트 성공")
    void shouldSuccessfullyCreateGetUserById() {
      // given
      Long userId = 1L;

      // when
      UserQuery.GetUserById result = new UserQuery.GetUserById(userId);

      // then
      assertThat(result.userId()).isEqualTo(userId);
    }

  }

  @Nested
  @DisplayName("FindUsersByIds 테스트")
  class FindUsersByIdsTest {

    @Test
    @DisplayName("생성자 테스트 실패 - userIds가 null인 경우")
    void shouldThrowExceptionWhenUserIdsIsNull() {
      // given
      List<Long> userIds = null;

      // when
      BusinessException result = assertThrows(BusinessException.class,
          () -> new UserQuery.FindUsersByIds(userIds));

      // then
      assertThat(result.getMessage()).isEqualTo(UserErrorCode.INVALID_USER_IDS.getMessage());
    }

    @Test
    @DisplayName("생성자 테스트 실패 - userIds가 비어있는 경우")
    void shouldThrowExceptionWhenUserIdsIsEmpty() {
      // given
      List<Long> userIds = List.of();

      // when
      BusinessException result = assertThrows(BusinessException.class,
          () -> new UserQuery.FindUsersByIds(userIds));

      // then
      assertThat(result.getMessage()).isEqualTo(UserErrorCode.INVALID_USER_IDS.getMessage());
    }

    @Test
    @DisplayName("생성자 테스트 실패 - userIds에 null이 포함된 경우")
    void shouldThrowExceptionWhenUserIdsContainsNull() {
      // given
      List<Long> userIds = new ArrayList<>();
      userIds.add(1L);
      userIds.add(null);

      // when
      BusinessException result = assertThrows(BusinessException.class,
          () -> new UserQuery.FindUsersByIds(userIds));

      // then
      assertThat(result.getMessage()).isEqualTo(UserErrorCode.INVALID_USER_IDS.getMessage());
    }

    @Test
    @DisplayName("생성자 테스트 실패 - userIds에 0이 포함된 경우")
    void shouldThrowExceptionWhenUserIdsContainsZero() {
      // given
      List<Long> userIds = List.of(1L, 0L);

      // when
      BusinessException result = assertThrows(BusinessException.class,
          () -> new UserQuery.FindUsersByIds(userIds));

      // then
      assertThat(result.getMessage()).isEqualTo(UserErrorCode.INVALID_USER_IDS.getMessage());
    }

    @Test
    @DisplayName("생성자 테스트 실패 - userIds에 음수가 포함된 경우")
    void shouldThrowExceptionWhenUserIdsContainsNegative() {
      // given
      List<Long> userIds = List.of(1L, -1L);

      // when
      BusinessException result = assertThrows(BusinessException.class,
          () -> new UserQuery.FindUsersByIds(userIds));

      // then
      assertThat(result.getMessage()).isEqualTo(UserErrorCode.INVALID_USER_IDS.getMessage());
    }

    @Test
    @DisplayName("생성자 테스트 성공")
    void shouldSuccessfullyCreateFindUsersByIds() {
      // given
      List<Long> userIds = List.of(1L, 2L);

      // when
      UserQuery.FindUsersByIds result = new UserQuery.FindUsersByIds(userIds);

      // then
      assertThat(result.userIds()).isEqualTo(userIds);
    }
  }


}