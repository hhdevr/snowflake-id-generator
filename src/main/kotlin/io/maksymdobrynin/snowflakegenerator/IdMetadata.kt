package io.maksymdobrynin.snowflakegenerator

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "id_metadata")
data class IdMetadata(

    @Id
    val guid: Long,

    @Column(name = "created_at")
    val createdAt: LocalDateTime,

    @Column(name = "datacenter_id")
    val datacenterId: Long,

    @Column(name = "worker_id")
    val workerId: Long

) {
    constructor() : this(0L, LocalDateTime.now(), 0L, 0L)
}
