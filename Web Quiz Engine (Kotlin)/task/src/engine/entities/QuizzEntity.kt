package engine.entities

import javax.persistence.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

@Entity
@Table(name = "quizz_entity")
open class QuizzEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = false, nullable = false)
    open var id: Int? = null

    @Column(name = "title")
    @NotBlank
    @NotEmpty
    open var title: String? = null

    @Column(name = "text")
    @NotBlank
    @NotEmpty
    open var text: String? = null

    @ElementCollection
    @Valid
    @Size(min=2)
    open var options: MutableList<String> = mutableListOf()

    @ElementCollection
    @Valid
    open var answer: MutableList<Int> = mutableListOf()
}