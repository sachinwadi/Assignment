package com.cs.assignment;

import com.cs.assignment.service.EventPersistence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class Main implements ApplicationRunner {
    @Autowired
    private EventPersistence persistanceService;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(args.getSourceArgs().length ==0 ){
            String error = "Input file path is not specified.";
            log.error(error);
            throw new IllegalArgumentException(error);
        }
        log.info("Input File: " + args.getSourceArgs()[0]);

        persistanceService.persistEvents(args.getSourceArgs()[0]);
    }
}
