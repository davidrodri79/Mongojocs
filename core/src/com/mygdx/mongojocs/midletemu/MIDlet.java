package com.mygdx.mongojocs.midletemu;

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
