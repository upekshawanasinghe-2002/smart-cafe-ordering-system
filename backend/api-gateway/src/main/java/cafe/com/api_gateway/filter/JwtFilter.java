package cafe.com.api_gateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import org.springframework.web.servlet.function.HandlerFilterFunction;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtFilter
        implements HandlerFilterFunction<ServerResponse, ServerResponse> {

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey getSigningKey() {

        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }

    @Override
    public ServerResponse filter(
            ServerRequest request,
            HandlerFunction<ServerResponse> next)
            throws Exception {

        String authHeader =
                request.headers()
                        .firstHeader("Authorization");

        System.out.println("Authorization: " + authHeader);

        // No Authorization header
        if (authHeader == null ||
                !authHeader.startsWith("Bearer ")) {

            System.out.println("JWT MISSING");

            return ServerResponse
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        String token =
                authHeader.substring(7);

        try {

            Claims claims = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            System.out.println(
                    "JWT VALID FOR: "
                            + claims.getSubject()
            );

            return next.handle(request);

        } catch (Exception e) {

            System.out.println("JWT INVALID");

            return ServerResponse
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }
    }
}