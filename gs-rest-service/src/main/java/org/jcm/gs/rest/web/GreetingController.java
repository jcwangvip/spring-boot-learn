package org.jcm.gs.rest.web;

import lombok.AllArgsConstructor;
import org.jcm.gs.rest.service.GreetingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class GreetingController {

    private final GreetingService greetingService;


    @GetMapping("/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return greetingService.greeting(name);
    }
}
