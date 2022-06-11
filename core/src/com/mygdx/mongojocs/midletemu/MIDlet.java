package com.mygdx.mongojocs.midletemu;

import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;

public class MIDlet {


    public static String appFilesFolder = "";
    public static String assetsFolder = "";
    public boolean appClosed = false;

    public MIDlet()
    {
        System.out.println("MIDlet create");
    }

    public static void setAppFilesFolder(String appFilesFolder) {
        MIDlet.appFilesFolder = appFilesFolder;
    }

    public static void setAssetsFolder(String f)
    {
        assetsFolder = f;
    }

    public void notifyDestroyed()
    {
        while(Graphics.scissorsSet>0)
        {
            ScissorStack.popScissors();
            Graphics.scissorsSet--;
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
