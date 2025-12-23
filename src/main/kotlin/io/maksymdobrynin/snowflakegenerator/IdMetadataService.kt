package io.maksymdobrynin.snowflakegenerator

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class IdMetadataService(private val repository: IdMetadataRepository) {
    fun logIdMetadata(id: Long, settings: GeneratorSettings) {
        repository.save(
            IdMetadata(
                guid = id,
                createdAt = LocalDateTime.now(),
                datacenterId = settings.datacenterId,
                workerId = settings.workedId
            )
        )
    }

    fun getLoggedIdMetadata(id: Long): IdMetadata =
        repository.findById(id).orElseThrow {
            EntityNotFoundException("No Metadata found for $id")
        }

}

