package engine

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
class Controller {

    val quizzes = mutableListOf<Quizz>()

    @PostMapping("/api/quizzes")
    fun createNewQuiz(@RequestBody quizz: Quizz): ResponseEntity<String> {
        Quizz.Quantity.count()
        quizzes.add(quizz)

        val objectMapper = ObjectMapper()
        val postAsString: String = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(quizz)
        return ResponseEntity(postAsString, HttpStatus.OK)
    }

    @GetMapping("/api/quizzes")
    fun getAllquizzes(): ResponseEntity<String> {
        val objectMapper = ObjectMapper()
        val getAsString: String = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(quizzes)



        return ResponseEntity(getAsString, HttpStatus.OK)


    }


    @GetMapping("/api/quizzes/{id}")
    fun GetQuizzById(@PathVariable id: String): ResponseEntity<String> {

        val filter = quizzes.stream().filter { c -> c.id == id.toInt() }.findAny()

        if (filter.isEmpty) {
            return ResponseEntity("404 NOT FOUND", HttpStatus.NOT_FOUND)
        }

        val objectMapper = ObjectMapper()
        val getAsString: String = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(filter.get())
        return ResponseEntity(getAsString, HttpStatus.OK)
    }


    @PostMapping("/api/quizzes/{id}/solve")
    fun solvingQuizz(@PathVariable id: String, @RequestBody answer: Answer): ResponseEntity<String> {
        val filter = quizzes.stream().filter { c -> c.id == id.toInt() }.findAny()
        var res: String = ""
        if (filter.isEmpty) {
            return ResponseEntity("404 NOT FOUND", HttpStatus.NOT_FOUND)
        }

        val objectMapper = ObjectMapper()

        val answer1 = filter.get().answer
        val answer2 = answer.answer

        if (!answer1 .equals(answer2)) {
            res =
                """{
  "success": false,
  "feedback": "Wrong answer! Please, try again."
}"""


        }

        if (answer1 .equals(answer2)) {
            res =
                """{
  "success": true,
  "feedback": "Congratulations, you're right!"
}"""


        }

        return ResponseEntity(res, HttpStatus.OK)
    }
}