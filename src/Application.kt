package com.github.mgurov

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.features.*
import org.slf4j.event.*
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.github.mgurov.domain.customers
import com.github.mgurov.domain.orders
import com.github.mgurov.domain.products
import io.ktor.jackson.*
import java.util.concurrent.TimeUnit

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            registerModule(JavaTimeModule())
        }
    }

    routing {
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        // Static feature. Try to access `/static/ktor_logo.svg`
        static("/static") {
            resources("static")
        }

        get("/json/jackson") {
            call.respond(mapOf("hello" to "world"))
        }

        get("/api/customers/") {
            call.respond(customers)
        }

        get("/api/customers/id/{customerId}") {
            TimeUnit.SECONDS.sleep(3L)
            val id = call.parameters["customerId"]
            val customer = customers.find { it.id == id }
            if (customer != null) {
                call.respond(customer)
            } else {
                call.respond(HttpStatusCode.NoContent)
            }
        }

        get("/api/products/") {
            call.respond(products)
        }

        get("/api/products/id/{productId}") {
            val id = call.parameters["productId"]
            val product = customers.find { it.id == id }
            if (product != null) {
                call.respond(product)
            } else {
                call.respond(HttpStatusCode.NoContent)
            }

        }

        get("/api/orders/") {
            val limit = call.request.queryParameters["limit"]?.toInt() ?: 10
            call.respond(orders.take(limit))
        }

        get("/api/orders/id/{orderId}") {
            TimeUnit.SECONDS.sleep(2L)
            val orderId = call.parameters["orderId"]
            val order = orders.find { it.id == orderId }
            if (order != null) {
                call.respond(order)
            } else {
                call.respond(HttpStatusCode.NoContent)
            }
        }
    }
}

