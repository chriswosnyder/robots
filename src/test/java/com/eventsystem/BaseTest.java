package com.eventsystem;



import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@EnableAutoConfiguration(exclude = FlywayAutoConfiguration.class)
public abstract class BaseTest {
}
