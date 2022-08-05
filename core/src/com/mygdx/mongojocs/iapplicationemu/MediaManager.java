package com.mygdx.mongojocs.iapplicationemu;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.mongojocs.midletemu.MIDlet;

public class MediaManager {
    public static MediaImage getImage(String s) {

        String resLoc = null;

        if(s.startsWith("resource://"))
        {
            resLoc = IApplication.assetsFolder+"/"+s.substring(12);
        }

        MediaImage im = new MediaImage();
        im.texture = new Texture( resLoc );
        return im;
    }

    public static MediaSound getSound(String s) {
        return null;
    }
}
