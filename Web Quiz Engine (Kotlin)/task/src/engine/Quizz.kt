package engine

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty


data class Quizz(
    var id: Int = 0,
    val title: String, val text: String, val options: MutableList<String>,
) {
    var answer:MutableList<Int> = mutableListOf()
        @JsonIgnore
        get() {return field}
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



