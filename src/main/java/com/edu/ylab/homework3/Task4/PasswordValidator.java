package com.edu.ylab.homework3.Task4;

import com.edu.ylab.homework3.Task4.Exceptions.WrongPasswordException;
import com.edu.ylab.homework3.Task4.Exceptions.WrongLoginException;

public class PasswordValidator {
    public static void main(String[] args) throws WrongLoginException {

        System.out.println(validate("", "123*", "123*"));
    }

    static boolean validate(String login, String password, String confirmPassword) {
        String regex = "[a-zA-Z0-9_]+";
        try{
            if (login.length() == 0) {
                throw new WrongLoginException("Логин не может быть пустым");
            } else if (password.length() == 0) {
                throw new WrongPasswordException("Пароль не может быть пустым");
            } else if (login.length() > 19) {
                throw new WrongLoginException("Логин слишком длинный");
            } else if (!login.matches(regex)){
                throw new WrongLoginException("Логин содержит недопустимые символы");
            } else if
            (password.length() > 19) {
                throw new WrongPasswordException("Пароль слишком длинный");
            } else if (!password.matches(regex)){
                throw new WrongPasswordException("Пароль содержит недопустимые символы");
            } else if(!password.equals(confirmPassword)) {
                throw new WrongPasswordException("Пароль и подтверждение не совпадают");
            }
        } catch (WrongLoginException | WrongPasswordException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
