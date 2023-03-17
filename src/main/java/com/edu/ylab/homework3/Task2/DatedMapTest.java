package com.edu.ylab.homework3.Task2;

import com.edu.ylab.homework3.Task2.DatedMap.DatedMap;
import com.edu.ylab.homework3.Task2.DatedMap.impl.DatedMapImpl;

public class DatedMapTest {
    public static void main(String[] args) {
        DatedMap datedMap = new DatedMapImpl();
        System.out.println(datedMap.getKeyLastInsertionDate("3").toString());
    }

}
