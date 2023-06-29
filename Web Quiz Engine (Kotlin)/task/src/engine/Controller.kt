package engine

import com.fasterxml.jackson.databind.ObjectMapper
import engine.dtos.QuizzDto
import engine.entities.QuizzEntity
import engine.entities.UserEntity
import engine.repositories.QuizzEntityRepository
import engine.repositories.UserEntityRepository
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

//import javax.validation.Valid


@RestController
@Validated

class Controller(
    private val quizzEntityRepository: QuizzEntityRepository,
    private val userEntityRepository: UserEntityRepository,
    private val encoder: PasswordEncoder
) {

    //    val quizzes = mutableListOf<Quizz>()
    @PostMapping("/api/register")
    fun userRegister(@Valid @RequestBody user: UserEntity): ResponseEntity<String> {
        val findAll = userEntityRepository.findAll()
        val findAny = findAll.stream().filter { s -> s.email.equals(user.email) }.findAny()
        if (findAny.isPresent) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "the email address is already taken")
        }

        val regex = Regex("[\\w\\d]+[@][\\w\\d]+[.][\\w\\d]+")

        if (!regex.matches(user.email.toString())) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "the email address is invalid")
        }
//        println(findAll)
        user.password = encoder.encode(user.password)
        user.quizzEntities = mutableListOf()
        val save = userEntityRepository.save(user)
        val objectMapper = ObjectMapper()
        val postAsString: String = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(save)
        return ResponseEntity(postAsString, HttpStatus.OK)
    }

    @DeleteMapping("/api/quizzes/{id}")
    fun quizzeDelete(@AuthenticationPrincipal details: UserDetails, @PathVariable id: String): ResponseEntity<String> {
        val filter = quizzEntityRepository.findAll().stream().filter { c -> c.id == id.toInt() }.findAny()

        if (filter.isEmpty) {
            return ResponseEntity("404 NOT FOUND", HttpStatus.NOT_FOUND)
        }

        if (filter.isPresent) {
            var user = filter.get().user
            val email = filter.get().user?.email

            println("email $email ${filter.get().user?.password} ${filter.get().user?.id} ${filter.get().user?.quizzEntities}")

            val get = filter.get()
            if (get.user?.email.equals(details.username)) {

//                userEntityRepository.delete(user)
                quizzEntityRepository.deleteById(id.toInt())

                return ResponseEntity(HttpStatus.NO_CONTENT)

            } else return ResponseEntity(HttpStatus.FORBIDDEN)

        }
        return ResponseEntity("404 NOT FOUND", HttpStatus.NOT_FOUND)
    }

    @PostMapping("/actuator/shutdown")
    fun actShutDown() {

    }


    @PostMapping("/api/quizzes")
    fun createNewQuiz(
        @AuthenticationPrincipal details: UserDetails,
        @RequestBody @Valid quizz: QuizzEntity
    ): ResponseEntity<String> {
//        Quizz.Quantity.count()
//        quizzes.add(quizz)

        val findAll = userEntityRepository.findAll()
        val findAny = findAll.stream().filter { s -> s.email.equals(details.username) }.findAny()
        if (findAny.isPresent) {
            val user = findAny.get()
            quizz.user = user
        }

        val save = quizzEntityRepository.save(quizz)

        val dto: QuizzDto = QuizzDto(save.id, save.title, save.text, save.options)

        val objectMapper = ObjectMapper()
        val postAsString: String = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dto)
        return ResponseEntity(postAsString, HttpStatus.OK)
    }

    @GetMapping("/api/quizzes")
    fun getAllquizzes(@AuthenticationPrincipal details: UserDetails): ResponseEntity<String> {
        val objectMapper = ObjectMapper()
        var map = mutableListOf<QuizzDto>()
        var getAsString: String = ""

        val findAll = quizzEntityRepository.findAll()
        if (!findAll.isEmpty()) {
            map = findAll.toList().stream().map { it -> QuizzDto(it.id, it.title, it.text, it.options) }.toList()

            getAsString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(map)


        }

        if (findAll.isEmpty()) {
            getAsString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(map)
        }

        return ResponseEntity(getAsString, HttpStatus.OK)
    }

    @GetMapping("/api/quizzes/{id}")
    fun GetQuizzById(@AuthenticationPrincipal details: UserDetails, @PathVariable id: String): ResponseEntity<String> {
        var map: QuizzDto = QuizzDto()
        var getAsString: String = ""
        val filter = quizzEntityRepository.findAll().stream().filter { c -> c.id == id.toInt() }.findAny()

        if (filter.isEmpty) {
            return ResponseEntity("404 NOT FOUND", HttpStatus.NOT_FOUND)
        }


        val email = filter.get().user?.email

        println("email $email ${filter.get().user?.password} ${filter.get().user?.id} ${filter.get().user?.quizzEntities}")
        map = QuizzDto(filter.get().id, filter.get().title, filter.get().text, filter.get().options)

        val get = filter.get()

        val objectMapper = ObjectMapper()
        getAsString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(map)
        return ResponseEntity(getAsString, HttpStatus.OK)
    }

    @PostMapping("/api/quizzes/{id}/solve")
    fun solvingQuizz(
        @AuthenticationPrincipal details: UserDetails,
        @PathVariable id: String,
        @RequestBody answer: Answer
    ): ResponseEntity<String> {
        val filter = quizzEntityRepository.findAll().stream().filter { c -> c.id == id.toInt() }.findAny()
        var res: String = ""
        if (filter.isEmpty) {
            return ResponseEntity("404 NOT FOUND", HttpStatus.NOT_FOUND)
        }

        val objectMapper = ObjectMapper()

        val answer1 = filter.get().answer.toIntArray()
        val answer2 = answer.answer.toIntArray()

        if (!answer1.contentEquals(answer2)) {
            res =
                """{
  "success": false,
  "feedback": "Wrong answer! Please, try again."
}"""


        }

        if (answer1.contentEquals(answer2)) {
            res =
                """{
  "success": true,
  "feedback": "Congratulations, you're right!"
}"""


        }

        return ResponseEntity(res, HttpStatus.OK)
    }
}