package com.example.danielgalarza.chatdummy;

/**
 * Created by danielgalarza on 11/18/15.
 */

public class Chat {
    String message;
    String name;

    //Firebase use this to map "message" and "name" to online database.
    public Chat() {
    }

    public Chat(String name, String message) {

        this.message = message;
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }
}