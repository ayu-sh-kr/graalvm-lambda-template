package com.example.lambda.greeting

import org.springframework.stereotype.Service

@Service
class GreetingService {
  fun greet(request: GreetingRequest): GreetingResponse {
    val name = request.name.trim().ifEmpty { "World" }
    return GreetingResponse(message = "Hello, $name!", tags = request.tags)
  }
}
