package com.github.boyarsky1997.systemoptional.model;

public class Teacher extends User {

    public Teacher(Integer id, String login, String password, String name, String surname) {
        super(id,Role.TEACHER, login, password, name, surname);
    }

    public Teacher() {
        super(Role.TEACHER);
    }
}
