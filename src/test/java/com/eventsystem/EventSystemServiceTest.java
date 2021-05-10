package com.eventsystem;


import com.eventsystem.domain.EventSystem;
import com.eventsystem.service.EventSystemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * test class for event system
 */
public class EventSystemServiceTest extends com.eventsystem.BaseTest {

    /**
     * Testable instance.
     */
    @Autowired
    private EventSystemService eventSystemService;

    EventSystem eventSystem;

    @Value("${EVENT_LIMIT}")
    private Integer limit;
    /**
     * number of robots
     */
    private int numberOfRobots;

    @Test
    public void testThereExistEventsForUser() throws Exception {
        givenEventSystemWithLimitOfEvents();
        givenMoreEventsProduced(10000);
        whenRobotCount(360000);
        thenExistEventsStore();
    }

    @Test
    public void testRobotCountCalledOnce() throws Exception {
        givenEventSystemWithLimitOfEvents();
        givenMoreEventsProduced(10000);
        whenRobotCount(360000);
        thenExistRobotsInSystem();
    }

    @Test
    public void testRobotCountCalledRegularBasis() throws Exception {
        givenEventSystemWithLimitOfEvents();
        givenMoreEventsProduced(10000);
        whenRobotCountARegularBasis(360000, 10);
        thenExistRobotsInSystem();
    }

//        @Test
//    public void tryToFailWeakHashmap() {
//        givenEventSystemWithLimitOfEvents(100);
//        givenMoreEventsProduced(Integer.MAX_VALUE);
//        thenMapDidNotFail();
//    }

    private void thenMapDidNotFail() {
        System.out.println("DID NOT FAIL!!! size is :" + eventSystem.getEventsPerUser().size());
        assertTrue(eventSystem.getEventsPerUser().size() > 0);
    }

    private void thenExistRobotsInSystem() {
        assertTrue(numberOfRobots > 0);

    }

    private void thenExistEventsStore() {
        Map.Entry<Long, Integer> entry = eventSystem.getEventsPerUser().entrySet().iterator().next();
        int value = entry.getValue();
        assertTrue(value > 0);

    }

    private void whenRobotCount(int pastMilliseconds) {
        numberOfRobots = eventSystemService.robotsCount(pastMilliseconds,eventSystem);
        System.out.println("There are " + numberOfRobots + "  robots ! We found them in the latest " + pastMilliseconds + " milliseconds ");

    }

    private void whenRobotCountARegularBasis(int pastMilliseconds, int noOfTimes) {
        IntStream.range(0, noOfTimes).forEach(i ->
                {
                    numberOfRobots = eventSystemService.robotsCount(pastMilliseconds,eventSystem);
                    System.out.println("There are " + numberOfRobots + "  robots ! We found them in the latest " + pastMilliseconds + " milliseconds ");

                }
        );

    }

    private void givenMoreEventsProduced(int noOfEvents) {
        Random generator = new Random(5);
        long startTime = System.currentTimeMillis();
        IntStream.range(0, noOfEvents).forEach(i ->
                {
                    eventSystemService.event(startTime + generator.nextInt(5000), generator.nextLong(),eventSystem);
                }
        );

    }



    private void givenEventSystemWithLimitOfEvents() {
        Map<Long, Integer> eventsPerUser = new WeakHashMap<>();
        Map<Long, Long> timeWhenAUserBecameRobot = new WeakHashMap<>();
         eventSystem = new EventSystem(limit, eventsPerUser, timeWhenAUserBecameRobot);
    }

}
