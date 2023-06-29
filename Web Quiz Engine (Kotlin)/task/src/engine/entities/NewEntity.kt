package engine.entities

import javax.persistence.*

@Entity
@Table(name = "new_entity")
open class NewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null
}