package io.github.alicarpio;

import io.github.alicarpio.utils.HibernateUtil;
import spark.Spark;

public class NoteTakingApp {
    public static void main(String[] args){
        Spark.port(5315);
        HibernateUtil.initialize();


        HibernateUtil.shutdown();


    }
}
