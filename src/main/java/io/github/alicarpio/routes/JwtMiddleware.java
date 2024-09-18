package io.github.alicarpio.routes;

import static spark.Spark.*;
import io.github.alicarpio.utils.JwtUtil;

public class JwtMiddleware {
    public static void setupJwtAuth(){
        before("api/*", ((request, response) -> {
            String token = request.headers("Authorization");

            if (token == null || !token.startsWith("Bearer ")){
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
