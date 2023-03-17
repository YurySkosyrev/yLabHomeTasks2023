package com.edu.ylab.homework3.Task1;

import com.edu.ylab.homework3.Task1.Transliterator.Transliterator;
import com.edu.ylab.homework3.Task1.Transliterator.impl.TransliteratorImpl;

public class TransliteratorAssertTest {
    public static void main(String[] args) {
        Transliterator transliterator = new TransliteratorImpl();
        assert transliterator.transliterate("ВИНИШКО СТАЛО ДОРОЖАТЬ").equals("VINISHKO STALO DOROZHAT") : "Test1 failed";
        assert transliterator.transliterate("HELLO! Go, boy!").equals("HELLO! Go, boy!") : "Test2 failed";
        assert transliterator.transliterate("HELLO! ПРИВЕТ! Go, boy!").equals("HELLO! PRIVET! Go, boy!") : "Test3 failed";
        assert transliterator.transliterate("").equals("") : "Test4 failed";
        assert transliterator.transliterate("123").equals("123") : "Test5 failed";
    }
}