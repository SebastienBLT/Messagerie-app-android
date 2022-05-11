package com.example.messagerie_app_android.api.structure;

import java.util.ArrayList;

public class ListMessages {
    String version;
    String success;
    String status;
    ArrayList<Message> messages;

    public ArrayList<Message> getMessages() {
        return messages;
    }

    @Override
    public String toString() {
        return "ListMessages{" +
                "version='" + version + '\'' +
                ", success='" + success + '\'' +
                ", status='" + status + '\'' +
                ", messages=" + messages +
                '}';
    }
}
