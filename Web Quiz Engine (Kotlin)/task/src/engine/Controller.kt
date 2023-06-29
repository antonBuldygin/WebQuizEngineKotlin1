package engine

import com.fasterxml.jackson.databind.ObjectMapper
import engine.dtos.QuizzDto
import engine.entities.QuizzEntity
import engine.mappers.QuizzEntityMapper

import engine.repositories.QuizzEntityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.stream.Collector
import javax.validation.Valid


@RestController
@Validated

class Controller(
    private val quizzEntityRepository: QuizzEntityRepository,

) {

//    val quizzes = mutableListOf<Quizz>()


    @PostMapping("/api/quizzes")
    fun createNewQuiz(@RequestBody @Valid quizz: QuizzEntity): ResponseEntity<String> {
//        Quizz.Quantity.count()
//        quizzes.add(quizz)

        val save = quizzEntityRepository.save(quizz)

        val dto: QuizzDto = QuizzDto(save.id, save.title, save.text,save.options)

        val objectMapper = ObjectMapper()
        val postAsString: String = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dto)
        return ResponseEntity(postAsString, HttpStatus.OK)
    }

    @GetMapping("/api/quizzes")
    fun getAllquizzes(): ResponseEntity<String> {
        val objectMapper = ObjectMapper()
        var map = mutableListOf<QuizzDto>()
        var getAsString: String =""

        val findAll = quizzEntityRepository.findAll()
        if (!findAll.isEmpty()) {
             map= findAll.toList().stream().map { it -> QuizzDto(it.id, it.title, it.text, it.options) }.toList()

            getAsString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(map)



        }

        if (findAll.isEmpty()) {getAsString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(map)}

        return ResponseEntity(getAsString, HttpStatus.OK)
    }

    @GetMapping("/api/quizzes/{id}")
    fun GetQuizzById(@PathVariable id: String): ResponseEntity<String> {
        var map :QuizzDto = QuizzDto()
        var getAsString: String =""
        val filter = quizzEntityRepository.findAll().stream().filter { c -> c.id == id.toInt() }.findAny()

        if (filter.isEmpty) {
            return ResponseEntity("404 NOT FOUND", HttpStatus.NOT_FOUND)
        }

        if (filter.isPresent) {
            map=  QuizzDto(filter.get().id, filter.get().title, filter.get().text, filter.get().options)

        }

        val objectMapper = ObjectMapper()
        getAsString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(map)
        return ResponseEntity(getAsString, HttpStatus.OK)
    }


    @PostMapping("/api/quizzes/{id}/solve")
    fun solvingQuizz(@PathVariable id: String, @RequestBody answer: Answer): ResponseEntity<String> {
        val filter = quizzEntityRepository.findAll().stream().filter { c -> c.id == id.toInt() }.findAny()
        var res: String = ""
        if (filter.isEmpty) {
            return ResponseEntity("404 NOT FOUND", HttpStatus.NOT_FOUND)
        }

        val objectMapper = ObjectMapper()

        val answer1 = filter.get().answer.toIntArray()
        val answer2 = answer.answer.toIntArray()

        if (!answer1 .contentEquals(answer2)) {
            res =
                """{
  "success": false,
  "feedback": "Wrong answer! Please, try again."
}"""


        }

        if (answer1 .contentEquals(answer2)) {
            res =
                """{
  "success": true,
  "feedback": "Congratulations, you're right!"
}"""


        }

        return ResponseEntity(res, HttpStatus.OK)
    }
}