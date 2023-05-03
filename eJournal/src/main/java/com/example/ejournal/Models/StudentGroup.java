package com.example.ejournal.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "student_group")
public class StudentGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String groupNumber;

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String number) {
        this.groupNumber = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}