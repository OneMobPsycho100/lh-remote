package com.simple.shell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * SimpleShellApplication
 *
 * @author wuji
 * @date 2020-11-03 21:34
 */
@SpringBootApplication
@EnableScheduling
public class SimpleShellApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleShellApplication.class);
    }

}
