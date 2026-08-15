package cafe.com.api_gateway.config;

import cafe.com.api_gateway.filter.ApiKeyFilter;
import cafe.com.api_gateway.filter.JwtFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.rewritePath;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration
public class RouteConfig {

    private final ApiKeyFilter apiKeyFilter;
    private final JwtFilter jwtFilter;

    public RouteConfig(
            ApiKeyFilter apiKeyFilter,
            JwtFilter jwtFilter) {

        this.apiKeyFilter = apiKeyFilter;
        this.jwtFilter = jwtFilter;
    }

    // =========================
    // AUTH REGISTER
    // =========================
    @Bean
    public RouterFunction<ServerResponse> authRegisterRoute() {

        return route("auth-register")
                .POST(
                        "/api/auth/register",
                        http()
                )
                .before(uri("http://localhost:8081"))
                .filter(rewritePath("/api/(?<segment>.*)", "/${segment}"))
                .build();
    }

    // =========================
    // AUTH LOGIN
    // =========================
    @Bean
    public RouterFunction<ServerResponse> authLoginRoute() {

        return route("auth-login")
                .POST(
                        "/api/auth/login",
                        http()
                )
                .before(uri("http://localhost:8081"))
                .filter(rewritePath("/api/(?<segment>.*)", "/${segment}"))
                .build();
    }

    // =========================
    // MENU SERVICE
    // API KEY + JWT
    // =========================
    @Bean
    public RouterFunction<ServerResponse> menuRoute() {

        return route("menu-service")
                .GET(
                        "/api/menu",
                        http()
                )
                .before(uri("http://localhost:8082"))
                .filter(rewritePath("/api/(?<segment>.*)", "/${segment}"))

                // First API Key
                .filter(apiKeyFilter)

                // Then JWT
                .filter(jwtFilter)

                .build();
    }
}