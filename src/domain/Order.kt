package com.github.mgurov.domain

import java.math.BigDecimal
import java.time.Instant

data class Order(
    val id: String,
    val userId: String,
    val created: Instant,
    val lines: List<Line>,
) {
    data class Line(
        val id: String,
        val productId: String,
        val quantity: Int,
        val price: BigDecimal,
    )
}