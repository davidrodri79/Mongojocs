package com.mygdx.mongojocs.midletemu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;

public class Graphics {

    public static final int TOP = 1;
    public static final int LEFT = 2;

    public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;
    public OrthographicCamera camera;
    Font currentFont;
    Color currentColor;
    boolean scissorsSet = false;

    public Graphics()
    {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 176, 208);
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
    }

    public void setFont(Font f) {

        currentFont = f;
    }

    public void setColor(int c) {
        currentColor = new Color(((c & 0xff0000) >> 16)/255.f,
                ((c & 0xff00) >> 8)/255.f,
                (c & 0xff)/255.f,
                1);
    }

    public void setColor(int r, int g, int b) {
        currentColor = new Color(r/255.f, g/255.f, b/255.f, 1);
    }
    
    public void drawString(String str, int x, int y, int z) {

        batch.begin();
        currentFont.bmpFont.draw(batch, str, x, 208 - y - currentFont.getHeight() );
        batch.end();
    }

    public void setClip(int destX, int destY, int sizeX, int sizeY) {

        if(scissorsSet) ScissorStack.popScissors();
        Rectangle scissors = new Rectangle();
        Rectangle clipBounds = new Rectangle(destX,208 - destY - sizeY, sizeX, sizeY);
        ScissorStack.calculateScissors(camera, batch.getTransformMatrix(), clipBounds, scissors);
        if (ScissorStack.pushScissors(scissors)) {
            scissorsSet = true;
        }
    }

    public void clipRect(int i, int i1, int sizeX, int sizeY) {
    }

    public void drawImage(Image img, int x, int y, int flags)
    {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(img.texture, x, 208 - y - img.getHeight());
        batch.end();
    }
    
    public void drawRect(int x, int y, int w, int h)
    {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(currentColor);
        shapeRenderer.rect(x, 208 - y - h, w, h);
        shapeRenderer.end();
    }

    public void fillRect(int x, int y, int w, int h) {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(currentColor);
        shapeRenderer.rect(x, 208 - y - h, w, h);
        shapeRenderer.end();
    }

    public void fillArc(int i, int i1, int sw, int sh, int i2, int i3) {
    }

    public void fillRoundRect(int i, int i1, int i2, int i3, int i4, int i5) {
        fillRect(i,i1,i2,i3);
    }

    public void drawRoundRect(int i, int i1, int i2, int i3, int i4, int i5) {
        drawRect(i,i1,i2,i3);
    }
}
