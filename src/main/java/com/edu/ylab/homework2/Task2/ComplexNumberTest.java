package com.edu.ylab.homework2.Task2;

/**
 Задача 2
 Реализовать класс, описывающий комплексное число (действительная и мнимая часть
 должны иметь точность double). Должны быть доступны следующие операции:
 1. Cоздание нового числа по действительной части (конструктор с 1
 параметром)
 2. Создание нового числа по действительной и мнимой части (конструктор
 с 2 параметрами)
 3. Сложение
 4. Вычитание
 5. Умножение
 6. Операция получения модуля
 7. преобразование в строку (toString)
 (арифметические действия должны создавать новый экземпляр класса)
 Написать код, демонстрирующий работу с созданными классами
 */


public class ComplexNumberTest {
    public static void main(String[] args) {

        ComplexNumber x = new ComplexNumber(2, -1);
        ComplexNumber y = new ComplexNumber(3, 4);

        System.out.println("x= " + x);
        System.out.println("y= " + y);
        System.out.println("x + y = " + x.sum(y));
        System.out.println("x - y = " + x.sub(y));
        System.out.println("x * y = " + x.mult(y));
        System.out.printf("Mod(x) = %.3f", x.mod());

        //Assert-тесты в классе ComplexNumberAssertTest
    }
}
