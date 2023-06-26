package engine.repositories;

import engine.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserEntityRepository : JpaRepository<UserEntity, Long> {
}