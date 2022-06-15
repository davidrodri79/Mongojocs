package com.mygdx.mongojocs.midletemu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

import java.io.File;
import java.io.FileOutputStream;

public class Image {

    public Texture texture;
    FrameBuffer fbo = null;

    public static Image createImage(String fileName)
    {
        Image im = new Image();
        im.texture = new Texture(MIDlet.assetsFolder+"/"+fileName.substring(1));
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
        File file = new File(MIDlet.appFilesFolder+"/PNGfrombytes.png");
        try {
            if (!file.exists()) {
                File dirs = new File(MIDlet.appFilesFolder);
                dirs.mkdirs();
                file.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(inbuf, start, length);
            fos.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }

        Image im = new Image();
        im.texture = new Texture(file.toString());
        return im;
    }

    public Graphics getGraphics()
    {
        Graphics g = new Graphics();

        g.camera.setToOrtho(false, getWidth(), getHeight());
        g.batch.setProjectionMatrix(g.camera.combined);
        g.fromImage = this;

        return g;
    }

    public int getWidth() {
        return texture.getWidth();
    }

    public int getHeight() {
        return texture.getHeight();
    }

    public void flipVertical()
    {
        if(fbo != null) {

            TextureRegion textureRegion = new TextureRegion(texture);
            textureRegion.flip(false, true);
        }
    }
}
