package com.hhplus.tdd.clean.architecture.infrastructure.db.user;

import com.hhplus.tdd.clean.architecture.domain.user.User;
import com.hhplus.tdd.clean.architecture.infrastructure.db.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "\"user\"")
@Getter
@NoArgsConstructor
public class UserEntity extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String password;

  public User toUser() {
    return new User(id, name, getCreatedAt(), getUpdatedAt());
  }

  public UserEntity(Long id, String name, String password, LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    super(createdAt, updatedAt);
    this.id = id;
    this.name = name;
    this.password = password;
  }

  public UserEntity(Long id, String name, String password) {
    this(id, name, password, null, null);
  }

}
