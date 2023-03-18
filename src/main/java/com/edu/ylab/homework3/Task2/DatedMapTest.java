package com.edu.ylab.homework3.Task2;

import com.edu.ylab.homework3.Task2.DatedMap.DatedMap;
import com.edu.ylab.homework3.Task2.DatedMap.impl.DatedMapImpl;

public class DatedMapTest {
    public static void main(String[] args) {
        DatedMap datedMap = new DatedMapImpl();
        datedMap.put("1","Privet");
        System.out.println(datedMap.getKeyLastInsertionDate("1"));
        System.out.println(datedMap.containsKey("2"));
    }

}
