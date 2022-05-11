package com.example.messagerie_app_android.api.structure;

public class ConnectionStatus {
    String version;
    String success;
    String status;
    String hash;
    String id;

    @Override
    public String toString() {
        return "ConnectionStatus{" +
                "version='" + version + '\'' +
                ", success='" + success + '\'' +
                ", status='" + status + '\'' +
                ", hash='" + hash + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public String getHash() {
        return hash;
    }

    public String getId() {
        return id;
    }


}
