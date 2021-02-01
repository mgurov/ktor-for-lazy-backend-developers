package com.github.mgurov

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
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
                val mapper = jacksonObjectMapper()
                mapper.registerModule(JavaTimeModule())
                val parsed: List<Customer> = mapper.readValue(response.byteContent!!)
                assertThat(parsed.map { it.firstName to it.secondName }).containsExactlyInAnyOrder(
                    "First" to "Uno", "Second" to "Dos"
                )
                assertEquals("HELLO WORLD!", parsed)
            }
        }
    }
}
