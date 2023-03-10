package com.edu.ylab.homework2.Task1;

import com.edu.ylab.homework2.Task1.Sequences.impl.SequencesImpl;

/**
 Задача 1
 Последовательности A - J заданы в виде нескольких значений следующим образом
 A. 2, 4, 6, 8, 10...
 B. 1, 3, 5, 7, 9...
 C. 1, 4, 9, 16, 25...
 D. 1, 8, 27, 64, 125...
 E. 1, -1, 1, -1, 1, -1...
 F. 1, -2, 3, -4, 5, -6...
 G. 1, -4, 9, -16, 25....
 H. 1, 0, 2, 0, 3, 0, 4....
 I. 1, 2, 6, 24, 120, 720...
 J. 1, 1, 2, 3, 5, 8, 13, 21…

 Необходимо найти закономерности, по которым эти последовательности
 сформированы и реализовать следующий интерфейс, каждый метод которого
 принимает число N и выводит в консоль N элементов соответствующей
 последовательности. Каждый элемент можно выводить с новой строки
 */

public class SequencesTest {
    public static void main(String[] args) {
        SequencesImpl sequences = new SequencesImpl();

        sequences.a(5);
        sequences.b(5);
        sequences.c(5);
        sequences.d(5);
        sequences.e(6);
        sequences.f(6);
        sequences.g(5);
        sequences.h(7);
        sequences.i(6);
        sequences.j(8);
    }
}
