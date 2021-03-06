package com.github.mgurov

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.mgurov.domain.Order
import com.github.mgurov.domain.orders
import domain.Customer
import io.ktor.http.*
import kotlin.test.*
import io.ktor.server.testing.*
import org.assertj.core.api.Assertions.assertThat

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("HELLO WORLD!", response.content)
            }
        }
    }

    @Test
    fun testCustomers() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/api/customers/").apply {
                assertEquals(HttpStatusCode.OK, response.status())

                val parsed: List<Customer> = mapper.readValue(response.byteContent!!)
                assertThat(parsed.map { it.firstName to it.secondName }).containsExactlyInAnyOrder(
                    "First" to "Uno", "Second" to "Dos"
                )
            }
        }
    }

    @Test
    fun `should limit the size of data returned`() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/api/orders/?limit=2").apply {
                assertEquals(HttpStatusCode.OK, response.status())

                val parsed: List<Order> = mapper.readValue(response.byteContent!!)
                assertThat(parsed).hasSize(2)
            }
        }
    }

    @Test
    fun `should be able to get an order by its id`() {

        val targetOrder = orders[0]

        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/api/orders/id/${targetOrder.id}").apply {
                assertEquals(HttpStatusCode.OK, response.status())

                val parsed: Order = mapper.readValue(response.byteContent!!)
                assertThat(parsed).isEqualTo(targetOrder)
            }
        }
    }
}

private val mapper = jacksonObjectMapper().registerModule(JavaTimeModule())