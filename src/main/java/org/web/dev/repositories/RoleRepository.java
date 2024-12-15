package org.web.dev.repositories;

import org.web.dev.domain.entities.RoleEntity;
import org.web.dev.domain.enums.UserRole;

import java.util.Optional;

public interface RoleRepository extends BaseRepository<RoleEntity, Long>{
    Optional<RoleEntity> findRoleByName(UserRole name);
}
