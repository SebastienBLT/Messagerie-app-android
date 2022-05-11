package com.example.messagerie_app_android.api.structure;

public class Message {
    String id;
    String contenu;
    String auteur;
    String couleur;
    String userId;

    @Override
    public String toString() {
        return "Conversation{" +
                "id='" + id + '\'' +
                ", contenu='" + contenu + '\'' +
                ", auteur='" + auteur + '\'' +
                ", couleur='" + couleur + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getContenu() {
        return contenu;
    }

    public String getAuteur() {
        return auteur;
    }

    public String getCouleur() {
        return couleur;
    }

    public String getUserId() {
        return userId;
    }
}
