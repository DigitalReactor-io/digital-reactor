package io.digitalreactor;

import io.digitalreactor.ui.WebApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;

/**
 * Created by MStepachev on 07.09.2016.
 */
@Controller
@EnableAutoConfiguration
@ComponentScan("io.digitalreactor.web.controller")
public class Application {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
