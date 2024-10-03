package com.hhplus.tdd.clean.architecture.domain.user.dto;

import com.hhplus.tdd.clean.architecture.domain.common.error.BusinessException;
import com.hhplus.tdd.clean.architecture.domain.user.UserErrorCode;
import java.util.List;

public class UserQuery {

  public record GetUserById(Long userId) {

    public GetUserById {
      if (userId == null || userId <= 0) {
        throw new BusinessException(UserErrorCode.INVALID_USER_ID);
      }
    }

  }

  public record FindUsersByIds(List<Long> userIds) {

    public FindUsersByIds {
      if (userIds == null || userIds.isEmpty() || userIds.stream()
          .anyMatch(id -> id == null || id <= 0)) {
        throw new BusinessException(UserErrorCode.INVALID_USER_IDS);
      }
    }
    
  }

}
