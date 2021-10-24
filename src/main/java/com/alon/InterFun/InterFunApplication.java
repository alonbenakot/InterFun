package com.alon.InterFun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//exclude = {DataSourceAutoConfiguration.class }
@SpringBootApplication
public class InterFunApplication {

    public static void main(String[] args) {
            SpringApplication.run(InterFunApplication.class, args);
            System.out.println("App is up");


    }

}
