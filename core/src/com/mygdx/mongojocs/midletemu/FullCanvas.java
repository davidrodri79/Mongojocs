package com.mygdx.mongojocs.midletemu;

public class FullCanvas extends Canvas {


    public FullCanvas()
    {
        super();
    }

    public int getWidth()
    {
        return Display.width;
    }

    public int getHeight()
    {
        return Display.height;
    }
}
