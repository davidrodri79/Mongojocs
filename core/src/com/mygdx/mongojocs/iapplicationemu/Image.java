package com.mygdx.mongojocs.iapplicationemu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.mongojocs.midletemu.MIDlet;

public class Image {

    static class ImageLoadTask implements Runnable {

        Image image;
        String filename;
        boolean finished;

        ImageLoadTask(Image i, String f) {
            image = i;
            filename = f;
            finished = false;
        }

        @Override
        public void run() {
            try {
                image._createImage(filename);
            } catch (Exception e) {
            }
            finished = true;
        }
    }

    public Texture texture = null;

    public static Image createImage(String fileName)
    {
        Image im = new Image();
        ImageLoadTask task = new ImageLoadTask(im, fileName);
        Gdx.app.postRunnable(task);

        while(!task.finished)
        {
            try {
                Gdx.app.log("Image","Waiting for main thread to create image from file...");
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return im;
    }

    public void _createImage(String fileName)
    {
        texture = new Texture(fileName);
        Gdx.app.log("Image", "createImage "+fileName.toString());
    }

    public int getWidth() {
        return texture != null ? texture.getWidth() : 0;
    }

    public int getHeight() {
        return texture != null ? texture.getHeight() : 0;
    }

    public void dispose() {
    }
}
