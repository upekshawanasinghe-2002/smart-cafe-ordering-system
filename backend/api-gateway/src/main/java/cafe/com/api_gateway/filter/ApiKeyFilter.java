package cafe.com.api_gateway.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.HandlerFilterFunction;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

@Component
public class ApiKeyFilter
        implements HandlerFilterFunction<ServerResponse, ServerResponse> {

    @Value("${api.key}")
    private String validApiKey;

    @Override
    public ServerResponse filter(
            ServerRequest request,
            HandlerFunction<ServerResponse> next)
            throws Exception {

        String apiKey =
                request.headers().firstHeader("X-API-KEY");

        System.out.println("API KEY RECEIVED: " + apiKey);

        if (validApiKey.equals(apiKey)) {

            System.out.println("API KEY VALID");

            return next.handle(request);
        }

        System.out.println("API KEY INVALID");

        return ServerResponse
                .status(HttpStatus.FORBIDDEN)
                .build();
    }
}