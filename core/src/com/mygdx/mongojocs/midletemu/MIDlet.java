package com.mygdx.mongojocs.midletemu;

import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;

public class MIDlet {

    public static String assetsFolder = "";
    public boolean appClosed = false;

    public MIDlet()
    {
        System.out.println("MIDlet create");
    }

    public static void setAssetsFolder(String f)
    {
        assetsFolder = f;
    }

    public void notifyDestroyed()
    {
        if(Display.theDisplay.canvas.graphics.scissorsSet)
        {
            ScissorStack.popScissors();
            Display.theDisplay.canvas.graphics.scissorsSet = false;
        }
        appClosed = true;
    }

    public void startApp()
    {

    }

    public void run()
    {

    }

    public void runInit()
    {

    }

    public void runTick()
    {

    }

    public void runEnd()
    {

    }
}
