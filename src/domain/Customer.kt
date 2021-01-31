package domain

import java.time.Instant

data class Customer(
    val id: String,
    val firstName: String,
    val secondName: String,
    val registrationDateTime: Instant,
)