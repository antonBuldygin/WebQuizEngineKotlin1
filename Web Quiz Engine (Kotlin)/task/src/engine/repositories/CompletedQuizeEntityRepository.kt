package engine.repositories;

import engine.entities.CompletedQuizeEntity
import engine.entities.UserEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface CompletedQuizeEntityRepository : JpaRepository<CompletedQuizeEntity, Long> {

    fun findAllById(id: Long, pageable:Pageable): Page<CompletedQuizeEntity>
    fun findByUserOrderByCompletedAtDesc(user: UserEntity, pageable: Pageable): Page<CompletedQuizeEntity>
}