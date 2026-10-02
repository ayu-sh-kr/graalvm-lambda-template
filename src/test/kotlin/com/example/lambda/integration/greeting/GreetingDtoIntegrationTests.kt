package com.example.lambda.integration.greeting

import com.example.lambda.Application
import com.example.lambda.greeting.GreetingRequest
import com.example.lambda.greeting.GreetingResponse
import com.example.lambda.health.HealthResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import tools.jackson.databind.json.JsonMapper

@ActiveProfiles("test")
@SpringBootTest(classes = [Application::class])
class GreetingDtoIntegrationTests @Autowired constructor(private val mapper: JsonMapper) {
  @Test
  fun `request round trip preserves every field`() {
    val expected = GreetingRequest(name = "Ada", tags = listOf("kotlin", "native"))
    val json = mapper.writeValueAsString(expected)
    val actual = mapper.readValue(json, GreetingRequest::class.java)

    assertThat(actual.name).isEqualTo(expected.name)
    assertThat(actual.tags).containsExactlyElementsOf(expected.tags)
    assertThat(mapper.readTree(json).path("name").stringValue()).isEqualTo("Ada")
    assertThat(mapper.readTree(json).path("tags")).hasSize(2)
  }

  @Test
  fun `request defaults are available to Jackson`() {
    val actual = mapper.readValue("{}", GreetingRequest::class.java)

    assertThat(actual.name).isEqualTo("World")
    assertThat(actual.tags).isEmpty()
  }

  @Test
  fun `response round trip preserves every field`() {
    val expected = GreetingResponse(message = "Hello, Ada!", tags = listOf("native"))
    val json = mapper.writeValueAsString(expected)
    val actual = mapper.readValue(json, GreetingResponse::class.java)

    assertThat(actual.message).isEqualTo(expected.message)
    assertThat(actual.tags).containsExactlyElementsOf(expected.tags)
    assertThat(mapper.readTree(json).path("message").stringValue()).isEqualTo("Hello, Ada!")
    assertThat(mapper.readTree(json).path("tags")).hasSize(1)
  }

  @Test
  fun `response tags default when omitted`() {
    val actual = mapper.readValue("""{"message":"Hello!"}""", GreetingResponse::class.java)

    assertThat(actual.message).isEqualTo("Hello!")
    assertThat(actual.tags).isEmpty()
  }

  @Test
  fun `health status defaults when omitted`() {
    val actual = mapper.readValue("{}", HealthResponse::class.java)

    assertThat(actual.status).isEqualTo("UP")
  }

  @Test
  fun `health round trip preserves every field`() {
    val expected = HealthResponse(status = "UP")
    val json = mapper.writeValueAsString(expected)
    val actual = mapper.readValue(json, HealthResponse::class.java)

    assertThat(actual.status).isEqualTo(expected.status)
    assertThat(mapper.readTree(json).path("status").stringValue()).isEqualTo("UP")
  }
}
