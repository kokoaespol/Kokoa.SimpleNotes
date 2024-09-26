package io.github.alicarpio.utils;

import io.github.alicarpio.sevices.Apply;
import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Spark;

public class CorsFilter implements Apply {

    @Override
    public void apply() {
        // Filtro para manejar CORS
        Filter corsFilter = (Request request, Response response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, OPTIONS");
            response.header("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With, Content-Length, Accept, Origin");
            response.header("Access-Control-Allow-Credentials", "true");

            if ("OPTIONS".equalsIgnoreCase(request.requestMethod())) {
                Spark.halt(200);
            }
        };

        Spark.before(corsFilter);
        Spark.options("/*", (request, response) -> {
            response.status(200);
            return "";
        });
    }
}
