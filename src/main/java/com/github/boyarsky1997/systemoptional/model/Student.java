package com.github.boyarsky1997.systemoptional.model;

public class Student extends User {

    public Student() {
        super(Role.STUDENT);
    }

    public Student(String login, String password, String name, String surname) {
        super(Role.STUDENT, login, password, name, surname);
    }
}
