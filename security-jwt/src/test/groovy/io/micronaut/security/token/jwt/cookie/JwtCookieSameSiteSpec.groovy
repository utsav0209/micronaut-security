package io.micronaut.security.token.jwt.cookie

import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.security.testutils.EmbeddedServerSpecification
import io.micronaut.security.testutils.authprovider.MockAuthenticationProvider
import io.micronaut.security.testutils.authprovider.SuccessAuthenticationScenario
import jakarta.inject.Singleton

class JwtCookieSameSiteSpec extends EmbeddedServerSpecification {

    @Override
    String getSpecName() {
        'JwtCookieSameSiteSpec'
    }

    @Override
    Map<String, Object> getConfiguration() {
        super.configuration + [
                'micronaut.http.client.followRedirects': false,
                'micronaut.security.authentication': 'cookie',
                'micronaut.security.token.jwt.cookie.cookie-max-age': '5m',
                'micronaut.security.token.jwt.cookie.cookie-same-site': 'None',
                'micronaut.security.redirect.login-failure': '/login/authFailed',
                'micronaut.security.token.jwt.signatures.secret.generator.secret': 'qrD6h8K6S9503Q06Y6Rfk21TErImPYqa',
        ]
    }

    void "test same-site is set from jwt cookie settings"() {
        when:
        HttpRequest loginRequest = HttpRequest.POST('/login', new LoginForm(username: 'sherlock', password: 'password'))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_TYPE)

        HttpResponse loginRsp = client.exchange(loginRequest, String)

        then:
        noExceptionThrown()

        when:
        String cookie = loginRsp.getHeaders().get('Set-Cookie')

        then:
        cookie.contains('SameSite=None')
    }

    @Requires(property = "spec.name", value = "JwtCookieSameSiteSpec")
    @Singleton
    static class AuthenticationProviderUserPassword extends MockAuthenticationProvider  {
        AuthenticationProviderUserPassword() {
            super([new SuccessAuthenticationScenario( "sherlock")])
        }
    }
}
