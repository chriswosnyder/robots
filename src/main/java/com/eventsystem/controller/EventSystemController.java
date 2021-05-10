package com.eventsystem.controller;


import com.eventsystem.domain.EventSystem;
import com.eventsystem.service.EventSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * event system controller
 * class for rest api
 */

@RestController
@RequestMapping("/events")
public class EventSystemController {

    @Autowired
    private EventSystemService eventSystemService;

    @Resource
    private EventSystem eventSystem;


    @GetMapping("/all")
    public ResponseEntity<String> allEvents() {
        HttpStatus status = HttpStatus.CREATED;
        Integer noOfEvents = eventSystem.getEventsPerUser().size();
        return new ResponseEntity<>("events in number " + noOfEvents, status);
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<String> allEventsByUser(@PathVariable(name = "userId") Long userId) {
        HttpStatus status = HttpStatus.CREATED;
        Integer noOfEvents = eventSystem.getEventsPerUser().get(userId);
        if(noOfEvents == null){
            noOfEvents = 0;
        }
        return new ResponseEntity<>("User with id " + userId + " has events in number " + noOfEvents, status);
    }


    @PostMapping("/createEvent/{userId}")
    public ResponseEntity<String> event(@PathVariable(name = "userId") Long userId) {
        HttpStatus status = HttpStatus.CREATED;
        long now = System.currentTimeMillis();

        eventSystemService.event(now, userId, eventSystem);
        return new ResponseEntity<>("event created for user with id " + userId, status);
    }

    @PostMapping("/robotsCount")
    public ResponseEntity<String> robotsCount(@RequestParam(name = "timeIntervalMilliseconds", required = false) long timeIntervalMilliseconds) {
        HttpStatus status = HttpStatus.CREATED;
        int robots = eventSystemService.robotsCount(timeIntervalMilliseconds, eventSystem);
        return new ResponseEntity<>("robots " + robots, status);
    }
}
