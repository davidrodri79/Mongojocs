package com.mygdx.mongojocs.midletemu;

import com.mygdx.mongojocs.pingpoyo.Bios;

public class Display {

    public static Display theDisplay = new Display();
    public FullCanvas canvas;

    public static Display getDisplay(MIDlet m)
    {
       return theDisplay;
    }

    public void setCurrent(FullCanvas c) {
        canvas = c;
    }
}
