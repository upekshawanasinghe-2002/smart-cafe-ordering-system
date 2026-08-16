package cafe.com.api_gateway.config;

import cafe.com.api_gateway.filter.ApiKeyFilter;
import cafe.com.api_gateway.filter.JwtFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
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
    // AUTH SERVICE (Public - No JWT/API Key)
    // /api/auth/** → :8081
    // =========================
    @Bean
    public RouterFunction<ServerResponse> authRoute() {

        return route("auth-service")
                .route(
                        RequestPredicates.path("/api/auth/**"),
                        http()
                )
                .before(uri("http://localhost:8081"))
                .filter(rewritePath("/api/(?<segment>.*)", "/${segment}"))
                .build();
    }

    // =========================
    // MENU SERVICE (Protected)
    // /api/menu/** → :8082
    // =========================
    @Bean
    public RouterFunction<ServerResponse> menuRoute() {

        return route("menu-service")
                .route(
                        RequestPredicates.path("/api/menu/**"),
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

    // =========================
    // ORDER SERVICE (Protected)
    // /api/orders/** → :8083
    // =========================
    @Bean
    public RouterFunction<ServerResponse> orderRoute() {

        return route("order-service")
                .route(
                        RequestPredicates.path("/api/orders/**"),
                        http()
                )
                .before(uri("http://localhost:8083"))
                .filter(rewritePath("/api/(?<segment>.*)", "/${segment}"))

                .filter(apiKeyFilter)
                .filter(jwtFilter)

                .build();
    }

    // =========================
    // PAYMENT SERVICE (Protected)
    // /api/payments/** → :8084
    // =========================
    @Bean
    public RouterFunction<ServerResponse> paymentRoute() {

        return route("payment-service")
                .route(
                        RequestPredicates.path("/api/payments/**"),
                        http()
                )
                .before(uri("http://localhost:8084"))
                .filter(rewritePath("/api/(?<segment>.*)", "/${segment}"))

                .filter(apiKeyFilter)
                .filter(jwtFilter)

                .build();
    }

    // =========================
    // FEEDBACK SERVICE (Protected)
    // /api/feedback/** → :8085
    // =========================
    @Bean
    public RouterFunction<ServerResponse> feedbackRoute() {

        return route("feedback-service")
                .route(
                        RequestPredicates.path("/api/feedback/**"),
                        http()
                )
                .before(uri("http://localhost:8085"))
                .filter(rewritePath("/api/(?<segment>.*)", "/${segment}"))

                .filter(apiKeyFilter)
                .filter(jwtFilter)

                .build();
    }
}