package com.example.lambda.greeting

/** Demonstrates Kotlin defaults and collection binding at the HTTP request boundary. */
data class GreetingRequest(
  /** The service trims this value and uses World when it is blank. */
  val name: String = "World",
  /** Returned unchanged so the sample exercises nested collection serialization. */
  val tags: List<String> = emptyList(),
)

/** Returned by both greeting endpoints to demonstrate typed JSON responses. */
data class GreetingResponse(
  /** Human-readable greeting after request normalization. */
  val message: String,
  /** Echoes request tags; GET requests return an empty list. */
  val tags: List<String> = emptyList(),
)
