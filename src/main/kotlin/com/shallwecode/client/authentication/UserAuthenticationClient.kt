package com.shallwecode.client.authentication

import com.shallwecode.client.authentication.request.UserAuthenticationRequest
import io.netty.handler.codec.http.HttpHeaderValues.APPLICATION_JSON
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class UserAuthenticationClient(
    @Value("\${services.url.certificate-service}")
    val certificateServiceUrl: String
) {

    val webClient: WebClient = WebClient.create(certificateServiceUrl)

    fun saveUserAuthentication(request: UserAuthenticationRequest): UserAuthenticationSaveResponseBody? {
        return webClient.put()
            .uri("/authentication/user")
            .header(CONTENT_TYPE, APPLICATION_JSON.toString())
            .body(BodyInserters.fromValue(request))
            .retrieve()
            .bodyToMono(UserAuthenticationSaveResponseBody::class.java)
            // TODO 에러 처리 필요
            .extractResult()
    }
}


fun <T> Mono<T>.extractResult(): T? {
    return this.flux()
        .toStream()
        .findFirst()
        .orElse(null)
}

data class UserAuthenticationSaveResponseBody(val userId: Long)