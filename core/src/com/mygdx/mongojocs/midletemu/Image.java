package com.mygdx.mongojocs.midletemu;

import com.badlogic.gdx.graphics.Texture;

public class Image {

    public Texture texture;

    public static Image createImage(String fileName)
    {
        Image im = new Image();
        im.texture = new Texture(fileName.substring(1));
        return im;
    }

    public int getWidth() {
        return texture.getWidth();
    }

    public int getHeight() {
        return texture.getHeight();
    }
}
