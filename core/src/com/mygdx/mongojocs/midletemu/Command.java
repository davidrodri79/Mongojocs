package com.mygdx.mongojocs.midletemu;

public class Command {
    public static final int OK = 1;
    public static final int SCREEN = 1;
    public static final int CANCEL = 2;

    String label;

    public Command(String lab, int ok, int i) {
        label = lab;
    }

    public String getLabel() {
        return label;
    }
}
