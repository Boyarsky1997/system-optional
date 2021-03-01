package com.github.boyarsky1997.systemoptional.model;

public class Teacher extends User {

    public Teacher(String login, String password, String name, String surname) {
        super(Role.TEACHER, login, password, name, surname);
    }

    public Teacher() {
        super(Role.TEACHER);
    }
}
