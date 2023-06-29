package engine.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "completed_quize_entity")
open class CompletedQuizeEntity(
    @Column(name = "id", nullable = false)

    open var id: Long? = null,
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    open var user: UserEntity? = null,

    @Column(name = "completed_at")
    open var completedAt: LocalDateTime? = null
) {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_complited", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    open var id_complited: Long? = null
}