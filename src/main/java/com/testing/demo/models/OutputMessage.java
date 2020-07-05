package com.testing.demo.models;

public class OutputMessage extends Message {

    private String time;

    public OutputMessage(final String from, final String text, final String time) {
        super(from, text);
        setFrom(from);
        setText(text);
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}