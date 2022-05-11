package com.example.messagerie_app_android.api.structure;

public class User {
    private String pseudo;
    private int id;

    @Override
    public String toString() {
        return "User{" +
                "pseudo='" + pseudo + '\'' +
                ", id=" + id +
                '}';
    }

    public int getId() {
        return id;
    }
}
