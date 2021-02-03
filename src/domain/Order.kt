package com.github.mgurov.domain

import java.math.BigDecimal
import java.time.Instant
import java.util.*

data class Order(
    val id: String = UUID.randomUUID().toString(),
    val customerId: String,
    val created: Instant = Instant.now(),
    val lines: List<Line>,
) {
    data class Line(
        val id: String = UUID.randomUUID().toString(),
        val productId: String,
        val quantity: Int,
        val price: BigDecimal,
    )
}