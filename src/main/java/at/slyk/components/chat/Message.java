package at.slyk.components.chat;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Message {
    private String user;
    private String msg;

    @Override
    public String toString() {
        return  user + ": " + msg;
    }
}
