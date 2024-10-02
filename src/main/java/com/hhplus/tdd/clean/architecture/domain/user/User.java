package com.hhplus.tdd.clean.architecture.domain.user;

import java.time.LocalDateTime;

public record User(
    Long id,
    String name,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

}
