package com.example.lambda.health

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/** Adapter readiness checks use this dependency-free route after Spring has started. */
@RestController
class HealthController {
  @GetMapping("/health")
  fun health(): HealthResponse = HealthResponse()
}

/** Reports application readiness only; add dependency checks when the application needs them. */
data class HealthResponse(
  /** UP means the application can accept HTTP requests. */
  val status: String = "UP",
)
