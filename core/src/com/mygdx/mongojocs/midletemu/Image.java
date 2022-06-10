package com.mygdx.mongojocs.midletemu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

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

    public Graphics getGraphics()
    {
        Graphics g = new Graphics();

        g.camera.setToOrtho(false, getWidth(), getHeight());
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
