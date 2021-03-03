package com.github.boyarsky1997.systemoptional.model;

import java.util.Objects;

public class Course {
    private int id;
    private String title;
    private int teacherId;
    private User teacher;
    private String description;

    public Course() {
    }

    public Course(int id, String title, int teacherId, String description) {
        this.id = id;
        this.title = title;
        this.teacherId = teacherId;
        this.teacher = new Teacher();
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", teacherId=" + teacherId +
                ", teacher=" + teacher +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id &&
                teacherId == course.teacherId &&
                Objects.equals(title, course.title) &&
                Objects.equals(teacher, course.teacher) &&
                Objects.equals(description, course.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, teacherId, teacher, description);
    }
}
