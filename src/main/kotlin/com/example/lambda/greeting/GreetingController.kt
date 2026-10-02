package com.example.lambda.greeting

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/** Uses ordinary Spring MVC routes locally and behind Lambda Web Adapter. */
@RestController
class GreetingController(private val service: GreetingService) {
  @GetMapping("/hello")
  fun hello(@RequestParam(defaultValue = "World") name: String): GreetingResponse =
    service.greet(GreetingRequest(name = name))

  @PostMapping("/hello")
  fun hello(@RequestBody request: GreetingRequest): GreetingResponse = service.greet(request)
}
