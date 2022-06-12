package com.mygdx.mongojocs.midletemu;

import com.mygdx.mongojocs.bravewar.GameCanvas;
import com.mygdx.mongojocs.pingpoyo.Bios;

public class Display {

    public static Display theDisplay = new Display();
    public static Displayable current;
    public static int width;
    public static int height;

    public static Display getDisplay(MIDlet m)
    {
       return theDisplay;
    }

    public void setCurrent(Displayable c) {
        current = c;
    }

    public static void setSize(int w, int h)
    {
        width = w; height = h;
    }

    public Displayable getCurrent() {
        return current;
    }
}
