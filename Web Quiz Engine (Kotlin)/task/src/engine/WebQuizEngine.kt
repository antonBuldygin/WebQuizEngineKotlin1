package engine

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@SpringBootApplication
open class WebQuizEngine {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<WebQuizEngine>(*args)
        }
    }

}
