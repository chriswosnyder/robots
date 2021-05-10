package com.eventsystem.service;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import jdk.jfr.Event;
import lombok.*;
/**
 * * You need to design an app that will store events for users and check stored
 * events for robot activity. You can consider the user as a robot if there are
 * more than {LIMIT} events for the user per given amount of time. {LIMIT} is an
 * app setting and could be a constructor argument.
 *
 * Two methods to be implemented: void event(long timestamp, long userId) -
 * event storing method. You are guaranteed that timestamps will increase
 * constantly. We need those events only to test them against robot activity and
 * no more further.
 *
 *
 *
 * int robotsCount(long time) - how many robots we have seen for the past {time}
 * milliseconds
 *
 * Consider that robotsCount will be called time after time on a regular basis.
 *
 * You need to design this app as efficiently as possible to prevent
 * OutOfMemoryError.
 *
 * Use Java for coding.
 *
 */
import com.eventsystem.domain.EventSystem;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
//import org.springframework.transaction.annotation.Transactional;


//@Transactional

@Service
public class EventSystemService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventSystemService.class);


    /**
     * the method will populate the collections with events for every user and it will store the timestamp when a user became a robot
     * ( the user surpassed the limit admited for the number of events )
     *
     * @param timestamp the timestamp when the event is produced
     * @param userId    the user id of the user that uses the system
     */
    public void event(long timestamp, long userId, EventSystem eventSystem) {

        int noOfEventsPerUser = 0;
        Map<Long, Integer> eventsPerUser = eventSystem.getEventsPerUser();
        Map<Long, Long> timeWhenAUserBecameRobot = eventSystem.getTimeWhenAUserBecameRobot();
        if (eventsPerUser.containsKey(userId)) {
            noOfEventsPerUser = eventsPerUser.get(userId);
            noOfEventsPerUser++;
            eventsPerUser.put(userId, noOfEventsPerUser);
            LOGGER.info("added event for user  " + userId);
        } else {
            noOfEventsPerUser = 1;
            eventsPerUser.put(userId, 1);
            LOGGER.info("added user  " + userId);
        }
        LOGGER.info("limit is   " + eventSystem.getLimit());
        if (noOfEventsPerUser > eventSystem.getLimit() && !timeWhenAUserBecameRobot.containsKey(userId)) {
            timeWhenAUserBecameRobot.put(userId, timestamp);
        }

    }

    /**
     * the method will count the robots existent in a system in a given time interval
     *
     * @param time the past time interval in millisecond
     * @return the number of robots existent in system
     */
    public int robotsCount(long time, EventSystem eventSystem) {
        Map<Long, Long> timeWhenAUserBecameRobot = eventSystem.getTimeWhenAUserBecameRobot();
        long startTimeInterval = System.currentTimeMillis() - time;
        AtomicInteger counter = new AtomicInteger(0);
        timeWhenAUserBecameRobot.entrySet().stream().forEach(entry -> {
            if (entry.getValue() > startTimeInterval) {
                counter.incrementAndGet();
            }
        });
        LOGGER.info("robots are now " + counter.get());
        return counter.get();
    }


}
