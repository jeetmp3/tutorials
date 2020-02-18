package demo.service;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import tutorials.logging.Logger;

@Component
public class RunnerService implements ApplicationRunner {

    private final Logger logger;

    public RunnerService(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.log("Welcome to SpringBoot auto-configuration!!!!!!!!!!!!!!!");
    }
}
