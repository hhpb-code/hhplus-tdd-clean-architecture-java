package com.hhplus.tdd.clean.architecture.domain.common.error;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

  String getCode();

  HttpStatus getStatus();

  String getMessage();
}