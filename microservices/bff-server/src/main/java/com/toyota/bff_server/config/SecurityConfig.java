package com.toyota.bff_server.config;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.function.Supplier;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, ClientRegistrationRepository clientRegistrationRepository)
            throws Exception {
        http
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new SpaCsrfTokenRequestHandler()))
                .addFilterAfter(new CsrfCookieFilter(), CsrfFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/**").permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(withDefaults())
                .logout(logout -> logout.logoutSuccessHandler(spaLogoutSuccessHandler(clientRegistrationRepository)));

        return http.build();
    }

    private LogoutSuccessHandler spaLogoutSuccessHandler(ClientRegistrationRepository clientRegistrationRepository) {
        OidcClientInitiatedLogoutSuccessHandler delegate =
                new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
        delegate.setPostLogoutRedirectUri("{baseUrl}");

        // A fetch()-driven SPA logout can't follow a 302 redirect to Keycloak the way a browser
        // navigation would, so we convert the redirect into 200 + Location and let the SPA
        // perform the navigation itself.
        return (request, response, authentication) ->
                delegate.onLogoutSuccess(request, new SpaRedirectResponseWrapper(response), authentication);
    }

    /**
     * Standard Spring Security pattern for SPA CSRF handling: the token is always written using
     * the XOR-masked encoding (BREACH-safe), but when reading the token back, a raw header value
     * (as sent by a SPA that read the XSRF-TOKEN cookie verbatim) is honored as-is instead of
     * requiring XOR decoding.
     */
    private static final class SpaCsrfTokenRequestHandler extends CsrfTokenRequestAttributeHandler {

        private final CsrfTokenRequestHandler delegate = new XorCsrfTokenRequestAttributeHandler();

        @Override
        public void handle(jakarta.servlet.http.HttpServletRequest request, HttpServletResponse response,
                            Supplier<CsrfToken> csrfToken) {
            this.delegate.handle(request, response, csrfToken);
        }

        @Override
        public String resolveCsrfTokenValue(jakarta.servlet.http.HttpServletRequest request, CsrfToken csrfToken) {
            String headerValue = request.getHeader(csrfToken.getHeaderName());
            return StringUtils.hasText(headerValue)
                    ? super.resolveCsrfTokenValue(request, csrfToken)
                    : this.delegate.resolveCsrfTokenValue(request, csrfToken);
        }
    }

    private static final class SpaRedirectResponseWrapper extends HttpServletResponseWrapper {

        SpaRedirectResponseWrapper(HttpServletResponse response) {
            super(response);
        }

        @Override
        public void sendRedirect(String location) throws IOException {
            setStatus(HttpServletResponse.SC_OK);
            setHeader("Location", location);
        }
    }
}
