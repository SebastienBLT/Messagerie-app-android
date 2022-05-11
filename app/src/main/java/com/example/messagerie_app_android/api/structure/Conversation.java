package com.example.messagerie_app_android.api.structure;

public class Conversation {
    String id;
    String active;
    String theme;

    // {"id":"23","active":"0","theme":"test"

    @Override
    public String toString() {
        return "Conversation{" +
                "id='" + id + '\'' +
                ", active='" + active + '\'' +
                ", theme='" + theme + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public boolean getActive() {
        return active.equals("1");
    }

    public String getTheme() {
        return theme;
    }
}
