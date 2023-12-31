package engine.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

//import javax.persistence.*
//import javax.validation.Valid
//import javax.validation.constraints.NotBlank
//import javax.validation.constraints.NotEmpty
//import javax.validation.constraints.Size

@Entity
@Table(name = "quizz_entity")
open class QuizzEntity  {
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


    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    open var user: UserEntity? = null

    @ElementCollection
    @Valid
    @Size(min=2)
    open var options: MutableList<String> = mutableListOf()

    @ElementCollection
    @Valid
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    open var answer: MutableList<Int> = mutableListOf()
    override fun toString(): String {
        return "QuizzEntity(id=$id, title=$title, text=$text, user=$user, options=$options, answer=$answer)"
    }

}