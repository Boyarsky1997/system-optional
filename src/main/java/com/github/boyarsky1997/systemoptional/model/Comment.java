package com.github.boyarsky1997.systemoptional.model;

import java.sql.Date;

public class Comment {
    private int id;
    private String message;
    private int teacherId;
    private String teacherName;
    private int studentId;
    private Date date;

    public Comment(int id, String message, int teacherId, int studentId, Date date, String teacherName) {
        this.id = id;
        this.message = message;
        this.teacherId = teacherId;
        this.studentId = studentId;
        this.date = date;
        this.teacherName = teacherName;
    }

    public String getNameTeacher() {
        return teacherName;
    }

    public void setNameTeacher(String nameTeacher) {
        this.teacherName = nameTeacher;
    }

    public Comment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", teacherId=" + teacherId +
                ", studentId=" + studentId +
                ", date=" + date +
                ", teacherName=" + teacherName +
                '}';
    }
}