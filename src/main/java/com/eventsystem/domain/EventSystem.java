package com.eventsystem.domain;



import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.WeakHashMap;


@Data
@AllArgsConstructor
public class EventSystem {

    /** limit for the maximum number of events per user **/
    private int limit;

    /** collection to store the number of events produced on user **/
    /** avoid OutOfMemoryError by using weak hash map for caching
     * see this https://devlearnings.wordpress.com/2010/07/05/maps-can-avoid-java-lang-outofmemoryerror/
     *  **/
    private Map<Long, Integer> eventsPerUser = new WeakHashMap<>();
    /** collection to store timestamp when a user became robot ( the number of events was greater than the admited limit ) **/
    private Map<Long, Long> timeWhenAUserBecameRobot = new WeakHashMap<>();



}
