package com.mygdx.mongojocs.midletemu;

import com.mygdx.mongojocs.pingpoyo.Bios;

public class Display {

    public static Display theDisplay = new Display();
    public FullCanvas canvas;
    public static int width;
    public static int height;

    public static Display getDisplay(MIDlet m)
    {
       return theDisplay;
    }

    public void setCurrent(FullCanvas c) {
        canvas = c;
    }

    public static void setSize(int w, int h)
    {
        width = w; height = h;
    }
}
