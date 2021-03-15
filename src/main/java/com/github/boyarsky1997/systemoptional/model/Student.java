package com.github.boyarsky1997.systemoptional.model;

public class Student extends User {

    public Student() {
        super(Role.STUDENT);
    }

    public Student(Integer id, String login, String password, String name, String surname) {
        super(id, Role.STUDENT, login, password, name, surname);
    }
}
