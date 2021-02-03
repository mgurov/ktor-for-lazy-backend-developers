package com.github.mgurov.domain

import domain.Customer
import java.math.BigDecimal
import java.time.Instant
import kotlin.random.Random

val customers = listOf(
    Customer(
        firstName = "First",
        secondName = "Uno",
        registrationDateTime = Instant.EPOCH
    ),
    Customer(
        firstName = "Second",
        secondName = "Dos",
    ),
)

val products = listOf(
    Product(
        name = "The Good Product",
        price = BigDecimal.ONE,
    ),
    Product(
        name = "The Bad Product",
        price = BigDecimal("12.06"),
    ),
    Product(
        name = "The Ugly Product",
        price = BigDecimal("99.98"),
    ),
)

val rnd = Random.Default

val orders = (1..100).map {
    Order(
        customerId = customers[rnd.nextInt(customers.size)].id,
        lines = products.shuffled(rnd).take(rnd.nextInt(products.size + 1)).map { product ->
            Order.Line(
                productId = product.id,
                quantity = rnd.nextInt(100),
                price = product.price * rnd.nextDouble(.9, 1.1).toBigDecimal()
            )
        }
    )
}
