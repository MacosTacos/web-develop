package org.web.dev.repositories;

import org.springframework.stereotype.Repository;
import org.web.dev.domain.entities.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<UserEntity, Long> {
    Optional<UserEntity> findByName(String name);
    Optional<UserEntity> findByEmail(String email);
}
