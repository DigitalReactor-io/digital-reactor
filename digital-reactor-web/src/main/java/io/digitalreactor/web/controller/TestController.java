package io.digitalreactor.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by MStepachev on 07.09.2016.
 */
@RestController
public class TestController {
    @RequestMapping("/hi")
    @ResponseBody
    String home() {
        return "Hello World!";
    }
}
