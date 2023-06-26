package engine.entities

import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.Size

@Entity
@Table(name = "user_entity")
open class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @Column(name = "email")
    @Email()
    open var email: String? = null

    @Column(name = "password")
    @Size(min = 5)
    open var password: String? = null

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    open var quizzEntities: MutableList<QuizzEntity> = ArrayList()
}