package io.github.alicarpio;

import io.github.alicarpio.features.users.InMemoryUserRepository;
import io.github.alicarpio.features.users.UserRepository;
import spark.Spark;

public class NoteTakingApp {
    public static void main(String[] args){
        Spark.port(5315);


    }
}
