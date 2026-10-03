package com.example.lambda

import com.amazonaws.serverless.proxy.model.AwsProxyResponse
import com.amazonaws.serverless.proxy.model.HttpApiV2ProxyRequest
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestStreamHandler
import java.io.InputStream
import java.io.OutputStream

/** Adapts HTTP API v2 and Function URL events to the Spring MVC servlet pipeline. */
class LambdaHandler : RequestStreamHandler {
  override fun handleRequest(input: InputStream, output: OutputStream, context: Context) {
    handler.proxyStream(input, output, context)
  }

  private companion object {
    val handler: SpringBootLambdaContainerHandler<HttpApiV2ProxyRequest, AwsProxyResponse> =
      SpringBootLambdaContainerHandler.getHttpApiV2ProxyHandler(Application::class.java)
  }
}
