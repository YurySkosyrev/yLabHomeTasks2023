package com.edu.ylab.homework3.Task2.DatedMap.impl;

import com.edu.ylab.homework3.Task2.DatedMap.DatedMap;

import java.time.LocalDate;
import java.util.*;

public class DatedMapImpl implements DatedMap {

    private Map<String, Element> data;

    @Override
    public void put(String key, String value) {
        Date putData = new Date();
        data.put(key, new Element(value,putData));
    }

    @Override
    public String get(String key) {
        return data.getOrDefault(key, null).elementValue;
    }

    @Override
    public boolean containsKey(String key) {
        return data.containsKey(key);
    }

    @Override
    public void remove(String key) {
        data.remove(key);
    }

    @Override
    public Set<String> keySet() {
        return data.keySet();
    }

    @Override
    public Date getKeyLastInsertionDate(String key) {
        return data.getOrDefault(key, null).getPutData();
    }

    private class Element{
        private String elementValue;
        private Date putData;

        public Element(String elementValue, Date putData) {
            this.elementValue = elementValue;
            this.putData = putData;
        }

        public String getElementValue() {
            return elementValue;
        }

        public Date getPutData() {
            return putData;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {return true;}
            if (o == null || getClass() != o.getClass()) {return false;}
            Element element = (Element) o;
            return Objects.equals(elementValue, element.elementValue) && Objects.equals(putData, element.putData);
        }

        @Override
        public int hashCode() {
            return Objects.hash(elementValue, putData);
        }
    }
}
