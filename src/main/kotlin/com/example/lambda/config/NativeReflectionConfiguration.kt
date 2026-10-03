package com.example.lambda.config

import com.example.lambda.greeting.GreetingRequest
import com.example.lambda.greeting.GreetingResponse
import com.example.lambda.health.HealthResponse
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding
import org.springframework.context.annotation.Configuration

/**
 * Central binding manifest for application DTOs, entities, and nested types.
 * Spring AOT consumes this configuration before GraalVM compiles the executable.
 */
@Configuration(proxyBeanMethods = false)
@RegisterReflectionForBinding(
  classes = [GreetingRequest::class, GreetingResponse::class, HealthResponse::class],
  // Kotlin's internal empty collection also crosses the Jackson response boundary.
  classNames = ["kotlin.collections.EmptyList"],
)
class NativeReflectionConfiguration
