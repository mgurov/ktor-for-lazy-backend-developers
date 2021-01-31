package com.github.mgurov.domain

import java.util.*

data class Product(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
)