package com.hhplus.tdd.clean.architecture.domain.user.dto;

import com.hhplus.tdd.clean.architecture.domain.common.error.BusinessException;
import com.hhplus.tdd.clean.architecture.domain.user.UserErrorCode;

public class UserQuery {

  public record GetUserById(Long userId) {

    public GetUserById {
      if (userId == null || userId <= 0) {
        throw new BusinessException(UserErrorCode.INVALID_USER_ID);
      }
    }

  }

}
