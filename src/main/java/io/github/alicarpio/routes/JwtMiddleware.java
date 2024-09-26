package io.github.alicarpio.routes;

import io.github.alicarpio.domain.use_cases.UserLogInUseCase;
import io.github.alicarpio.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.before;
import static spark.Spark.halt;

public class JwtMiddleware {

    private static final Logger logger = LoggerFactory.getLogger(UserLogInUseCase.class);

    public static void setupJwtAuth() {
        before("api/auth/*", ((request, response) -> {
            logger.debug("Processing request for path: " + request.pathInfo());

            if (request.requestMethod().equalsIgnoreCase("OPTIONS")) {
                logger.debug("Allowing OPTIONS request to pass through");
                return;
            }


            String token = request.headers("Authorization");

            if (token == null || !token.startsWith("Bearer ")) {
                halt(401, "No token provided");
            }

            token = token.substring(7);

            if (!JwtUtil.validateToken(token)) {
                halt(401, "Invalid token");
            }

            request.attribute("user", JwtUtil.getSubjectFromToken(token));
        }));
    }
}
