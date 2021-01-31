package com.github.mgurov.domain

import domain.Customer
import java.time.Instant

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
        name = "The Good Product"
    ),
    Product(
        name = "The Bad Product"
    ),
    Product(
        name = "The Ugly Product"
    ),
)

// TODO: orders.
