package com.edu.ylab.homework3.Task1.Transliterator.impl;

import com.edu.ylab.homework3.Task1.Transliterator.Transliterator;

import java.util.HashMap;
import java.util.Map;

public class TransliteratorImpl implements Transliterator {
    @Override
    public String transliterate(String sourse) {
        Map<Character,String > translit = new HashMap<>();
        translit.put('А',"A");
        translit.put('Б',"B");
        translit.put('В',"V");
        translit.put('Г',"G");
        translit.put('Д',"D");
        translit.put('Е',"E");
        translit.put('Ё',"E");
        translit.put('Ж',"ZH");
        translit.put('З',"Z");
        translit.put('И',"I");
        translit.put('Й',"I");
        translit.put('К',"K");
        translit.put('Л',"L");
        translit.put('М',"M");
        translit.put('Н',"N");
        translit.put('О',"O");
        translit.put('П',"P");
        translit.put('О',"O");
        translit.put('Р',"R");
        translit.put('С',"S");
        translit.put('Р',"R");
        translit.put('Т',"T");
        translit.put('Ф',"F");
        translit.put('Х',"KH");
        translit.put('Ц',"TS");
        translit.put('Ч',"CH");
        translit.put('Ш',"SH");
        translit.put('Щ',"SHCH");
        translit.put('Ы',"Y");
        translit.put('Ь',"");
        translit.put('Ъ',"IE");
        translit.put('Э',"E");
        translit.put('Ю',"IU");
        translit.put('Я',"IA");

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < sourse.length(); i++){
            sb.append(translit.getOrDefault(sourse.charAt(i), Character.toString(sourse.charAt(i))));
        }

        return sb.toString();
    }
}
