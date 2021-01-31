package domain

import java.time.Instant
import java.util.*

data class Customer(
    val id: String = UUID.randomUUID().toString(),
    val firstName: String,
    val secondName: String,
    val registrationDateTime: Instant = Instant.now(),
)