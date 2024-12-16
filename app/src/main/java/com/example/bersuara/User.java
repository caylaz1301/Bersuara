package com.example.bersuara;

public class User {
    private String email;
    private String studentId;

    // Constructor kosong diperlukan untuk Firestore
    public User() {
    }

    public User(String email, String studentId) {
        this.email = email;
        this.studentId = studentId;
    }

    // Getter dan Setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}

