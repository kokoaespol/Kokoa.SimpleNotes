package io.github.alicarpio;

import io.github.alicarpio.domain.data.repositories.PsqlUserRepository;
import io.github.alicarpio.domain.use_cases.UserLogInUseCase;
import io.github.alicarpio.domain.use_cases.UserRegistrationUseCase;
import io.github.alicarpio.repositories.UserRepository;
import io.github.alicarpio.routes.UserRoutes;
import io.github.alicarpio.utils.HibernateUtil;
import spark.Spark;

public class NoteTakingApi {
    public static void main(String[] args){
        Spark.port(5315);
        HibernateUtil.initialize();

        UserRepository userRepository = new PsqlUserRepository();
        UserRegistrationUseCase userRegistrationUseCase = new UserRegistrationUseCase(userRepository);
        UserLogInUseCase userLogInUseCase = new UserLogInUseCase(userRepository);
        UserRoutes userRoutes = new UserRoutes(userRegistrationUseCase, userLogInUseCase);
        userRoutes.setupRoutes();

        Spark.init();
        System.out.println("Spark is running on port 5315.");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Spark.stop();
            HibernateUtil.shutdown();
        }));
    }
}
