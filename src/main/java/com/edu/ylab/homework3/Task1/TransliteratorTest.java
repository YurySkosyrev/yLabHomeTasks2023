package com.edu.ylab.homework3.Task1;

import com.edu.ylab.homework3.Task1.Transliterator.Transliterator;
import com.edu.ylab.homework3.Task1.Transliterator.impl.TransliteratorImpl;

public class TransliteratorTest {
    public static void main(String[] args) {
        Transliterator transliterator = new TransliteratorImpl();
        String res = transliterator
                .transliterate("HELLO! ПРИВЕТ! Go, boy!");
        System.out.println(res);

        //Assert-тесты в классе TransliteratorAssertTest
    }
}