package com.edu.ylab.homework3.Task2;

import com.edu.ylab.homework3.Task2.DatedMap.DatedMap;
import com.edu.ylab.homework3.Task2.DatedMap.impl.DatedMapImpl;


public class DatedMapAssertTest {
    public static void main(String[] args) {
        DatedMap datedMap = new DatedMapImpl();
        datedMap.put("1","Test 1");
        datedMap.put("2","Test 2");

        assert datedMap.get("1").equals("Test 1") : "Get test failed";
        assert datedMap.get("3") == null : "Element not exist test failed";
        assert datedMap.containsKey("3") == false : "Element exist test failed";
        datedMap.remove("2");
        assert datedMap.get("2") == null : "Remove test failed";
        assert datedMap.getKeyLastInsertionDate("2") == null : "LastInsertionDate null test failed";
        assert datedMap.getKeyLastInsertionDate("1") != null : "LastInsertionDate test failed";

        //Assert-тесты в классе DatedMapAssertTest
    }

}
