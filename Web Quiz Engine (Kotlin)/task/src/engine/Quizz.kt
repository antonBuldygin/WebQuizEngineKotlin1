package engine

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size



/**
 * DTO for {@link engine.entities.QuizzEntity}
 */
data class Quizz(
    var id: Int = 0,

    ) {

    @NotEmpty
    @NotBlank
    var title: String = "3"

    @NotEmpty
    @NotBlank
    var text: String = ""

    @Size(min = 2)
    val options: MutableList<String> = mutableListOf()


    var answer: MutableList<Int> = mutableListOf()
        @JsonIgnore
        get() {
            return field
        }
        @JsonProperty("answer")
        set(value) {
            field = value
        }

    object Quantity {
        var number = 0

        fun count(): Int {

            return number++
        }
    }

    init {
        id = Quantity.number


    }

    override fun toString(): String {
        return "Quizz(title='$title', text='$text', options=$options, id=$id)"
    }

}



