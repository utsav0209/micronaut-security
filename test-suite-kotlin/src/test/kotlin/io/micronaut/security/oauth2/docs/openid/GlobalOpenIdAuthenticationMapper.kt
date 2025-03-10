package io.micronaut.security.oauth2.docs.openid

//tag::clazz[]
import io.micronaut.context.annotation.Replaces
import io.micronaut.security.authentication.AuthenticationResponse
import io.micronaut.security.oauth2.endpoint.authorization.state.State
import io.micronaut.security.oauth2.endpoint.token.response.DefaultOpenIdAuthenticationMapper
import io.micronaut.security.oauth2.endpoint.token.response.OpenIdClaims
import io.micronaut.security.oauth2.endpoint.token.response.OpenIdTokenResponse
import io.micronaut.security.oauth2.endpoint.token.response.OpenIdAuthenticationMapper

import jakarta.inject.Singleton

@Singleton
@Replaces(DefaultOpenIdAuthenticationMapper::class)
class GlobalOpenIdAuthenticationMapper : OpenIdAuthenticationMapper {

    override fun createAuthenticationResponse(providerName: String, tokenResponse: OpenIdTokenResponse, openIdClaims: OpenIdClaims, state: State?): AuthenticationResponse {
        return AuthenticationResponse.success("name")
    }
}
//end::clazz[]
