package com.example.lambda.integration.greeting

import com.example.lambda.Application
import com.example.lambda.greeting.GreetingRequest
import com.example.lambda.greeting.GreetingResponse
import com.example.lambda.health.HealthResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.client.RestTestClient
import org.springframework.test.web.servlet.client.returnResult
import org.springframework.web.context.WebApplicationContext

@ActiveProfiles("test")
@SpringBootTest(classes = [Application::class])
class GreetingControllerIntegrationTests @Autowired constructor(context: WebApplicationContext) {
  private val client = RestTestClient.bindToApplicationContext(context).build()

  @Test
  fun `malformed JSON is rejected before greeting`() {
    client.post().uri("/hello").contentType(MediaType.APPLICATION_JSON).body("{")
      .exchange().expectStatus().isBadRequest()
  }

  @Test
  fun `GET returns the default greeting`() {
    val result = client.get().uri("/hello").exchange().returnResult<GreetingResponse>()
    val response = requireNotNull(result.responseBody)

    assertThat(result.status.value()).isEqualTo(200)
    assertThat(response.message).isEqualTo("Hello, World!")
    assertThat(response.tags).isEmpty()
  }

  @Test
  fun `GET accepts a query parameter`() {
    val result = client.get().uri("/hello?name=Ada").exchange().returnResult<GreetingResponse>()
    val response = requireNotNull(result.responseBody)

    assertThat(result.status.value()).isEqualTo(200)
    assertThat(response.message).isEqualTo("Hello, Ada!")
    assertThat(response.tags).isEmpty()
  }

  @Test
  fun `POST normalizes a name and echoes tags`() {
    val result = client.post().uri("/hello")
      .body(GreetingRequest(name = " Ada ", tags = listOf("kotlin", "native")))
      .exchange().returnResult<GreetingResponse>()
    val response = requireNotNull(result.responseBody)

    assertThat(result.status.value()).isEqualTo(200)
    assertThat(response.message).isEqualTo("Hello, Ada!")
    assertThat(response.tags).containsExactly("kotlin", "native")
  }

  @Test
  fun `POST accepts omitted optional fields`() {
    val result = client.post().uri("/hello").contentType(MediaType.APPLICATION_JSON).body("{}")
      .exchange().returnResult<GreetingResponse>()
    val response = requireNotNull(result.responseBody)

    assertThat(result.status.value()).isEqualTo(200)
    assertThat(response.message).isEqualTo("Hello, World!")
    assertThat(response.tags).isEmpty()
  }

  @Test
  fun `POST uses the default greeting for a blank name`() {
    val result = client.post().uri("/hello").body(GreetingRequest(name = "   "))
      .exchange().returnResult<GreetingResponse>()
    val response = requireNotNull(result.responseBody)

    assertThat(result.status.value()).isEqualTo(200)
    assertThat(response.message).isEqualTo("Hello, World!")
    assertThat(response.tags).isEmpty()
  }

  @Test
  fun `health reports readiness`() {
    val result = client.get().uri("/health").exchange().returnResult<HealthResponse>()

    assertThat(result.status.value()).isEqualTo(200)
    assertThat(requireNotNull(result.responseBody).status).isEqualTo("UP")
  }
}
