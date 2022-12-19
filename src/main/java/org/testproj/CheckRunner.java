package org.testproj;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CheckRunner implements CommandLineRunner {
    private static Logger LOG = LoggerFactory.getLogger(CheckRunner.class);

    public static void main(String... args) {
        SpringApplication.run(CheckRunner.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info("EXECUTING : command line runner");
        LOG.info("Parsing command line args...");
    }
}