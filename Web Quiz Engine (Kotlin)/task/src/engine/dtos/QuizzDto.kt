package engine.dtos

import java.io.Serializable
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

/**
 * DTO for {@link engine.entities.QuizzEntity}
 */
data class QuizzDto(
    val id: Int? = null,
    @field:NotEmpty @field:NotBlank val title: String? = null,
    @field:NotEmpty @field:NotBlank val text: String? = null,
    @field:Size(min = 2) val options: MutableList<String> = mutableListOf()
) : Serializable