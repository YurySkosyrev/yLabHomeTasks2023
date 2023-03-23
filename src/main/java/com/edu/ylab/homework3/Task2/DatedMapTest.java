package com.edu.ylab.homework3.Task2;

import com.edu.ylab.homework3.Task2.DatedMap.DatedMap;
import com.edu.ylab.homework3.Task2.DatedMap.impl.DatedMapImpl;

/**
 Задание 2
 DatedMap - это структура данных, очень похожая на Map, но содержащая
 дополнительную информацию: время добавления каждого ключа. При этом время
 хранится только для тех ключей, которые присутствуют в Map.
 Реализовать DatedMap.
 */


public class DatedMapTest {
    public static void main(String[] args) {
        DatedMap datedMap = new DatedMapImpl();
        datedMap.put("1","Message 1");
        datedMap.put("2","Message 2");
        System.out.println(datedMap.get("1"));
        System.out.println(datedMap.getKeyLastInsertionDate("1"));
        datedMap.remove("1");
        System.out.println(datedMap.containsKey("1"));

        //Assert-тесты в классе TransliteratorAssertTest
    }

}
