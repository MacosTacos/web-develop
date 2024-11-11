package org.web.dev.repositories;

import org.springframework.stereotype.Repository;
import org.web.dev.domain.entities.UserEntity;

@Repository
public interface UserRepository extends BaseRepository<UserEntity, Long> {
}
