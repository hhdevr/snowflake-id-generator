package io.maksymdobrynin.snowflakegenerator

import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory.getLogger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1")
class SnowflakeController(
    private val generator: Generator,
    private val service: IdMetadataService
) {

    private val logger = getLogger(SnowflakeController::class.java)

    @GetMapping("/next-id")
    fun generate(): Long = runBlocking {
        val id = generator.nextId()
        service.logIdMetadata(id, generator.getKubeSettings())
        logger.info("\n" + generator.getKubeDescription())
        logger.info("RESULT ID: $id")
        id
    }

    @GetMapping("/meta/{id}")
    fun getMetadata(@PathVariable id: Long): ResponseEntity<String> {
        val body = 	service.getLoggedIdMetadata(id).toString()
        return ResponseEntity.ok(body)
    }

    @GetMapping("/ping")
    fun describe(): String =
        generator.getKubeDescription()

}
