package engine.repositories;

import engine.entities.QuizzEntity
import org.springframework.data.jpa.repository.JpaRepository

interface QuizzEntityRepository : JpaRepository<QuizzEntity, Int> {


}