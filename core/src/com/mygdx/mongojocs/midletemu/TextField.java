package com.mygdx.mongojocs.midletemu;

public class TextField {
    public static final int ANY = 1;

    public TextField(String subtitle, String s, int numChars, int any) {
    }

    public void setMaxSize(int numChars) {
    }

    public String getString() {
        return "";
    }

    public void getChars(char[] ch) {
        char fake[] = { 'M', 'O', 'N', 'G', 'O', 'J', 'O', 'C', 'S' };
        for(int i = 0; i < ch.length && i < fake.length; i++)
        {
            ch[i] = fake[i];
        }
    }
}
