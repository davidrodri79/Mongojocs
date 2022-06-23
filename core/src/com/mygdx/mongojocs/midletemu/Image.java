package com.mygdx.mongojocs.midletemu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

public class Image {

    static class ImageLoadTask implements Runnable {

        Image image;
        String filename;

        ImageLoadTask(Image i, String f)
        {
            image = i;
            filename = f;
        }

        @Override
        public void run() {
            image.texture = new Texture(MIDlet.assetsFolder+"/"+filename.substring(1));
        }
    };

    public Texture texture;
    FrameBuffer fbo = null;

    public static Image createImage(String fileName)
    {
        Image im = new Image();
        ImageLoadTask task = new ImageLoadTask(im, fileName);
        Gdx.app.postRunnable(task);

        return im;
    }

    public static Image createImage(int w, int h)
    {
        Image im = new Image();
        im.fbo = new FrameBuffer(Pixmap.Format.RGBA8888, w, h, false);
        im.texture = im.fbo.getColorBufferTexture();

        im.fbo.begin();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        im.fbo.end();

        return im;
    }

    public static Image createImage(byte[] inbuf, int start, int length)
    {
        Image im = new Image();
        try {

            Pixmap pixMap = new Pixmap(inbuf, start, length);
            im.texture = new Texture(pixMap);

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return im;
    }

    public Graphics getGraphics()
    {
        Graphics g = new Graphics();
        g.fromImage = this;
        g.init();
        g.camera.setToOrtho(false, getWidth(), getHeight());
        g.batch.setProjectionMatrix(g.camera.combined);
        return g;
    }

    public int getWidth() {
        return texture == null ? 0 : texture.getWidth();
    }

    public int getHeight() {
        return texture == null ? 0 : texture.getHeight();
    }

    public void flipVertical()
    {
        if(fbo != null) {

            TextureRegion textureRegion = new TextureRegion(texture);
            textureRegion.flip(false, true);
        }
    }
}
