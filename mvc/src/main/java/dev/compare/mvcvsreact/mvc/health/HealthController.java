package dev.compare.mvcvsreact.mvc.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("health")
public class HealthController {

    @GetMapping
    public HealthDto healthStatus() {
        return new HealthDto("ok");
    }
}
