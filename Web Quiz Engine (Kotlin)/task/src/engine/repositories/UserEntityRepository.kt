package engine.repositories;

import engine.entities.CompletedQuizeEntity
import engine.entities.UserEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface UserEntityRepository : JpaRepository<UserEntity, Long> {
    fun findByEmail(email: String):UserEntity
//    fun findCompletedQuizeEntitybyEmail(email: String, pageable: Pageable): Page<CompletedQuizeEntity>
}