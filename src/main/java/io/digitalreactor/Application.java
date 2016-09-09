package io.digitalreactor;

import io.digitalreactor.core.ServiceConfig;
import io.digitalreactor.ui.WebApp;
import io.digitalreactor.vendor.yandex.YandexServiceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;

/**
 * Created by MStepachev on 07.09.2016.
 */
@Controller
@EnableAutoConfiguration
@ComponentScan({
        "io.digitalreactor.web.controller",
        "io.digitalreactor.web.ws",
        "io.digitalreactor.config",
        "io.digitalreactor.web.config"
})
public class Application {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(new Class<?>[]{
                Application.class,
                ServiceConfig.class,
                YandexServiceConfig.class
        }, args);
    }
}
