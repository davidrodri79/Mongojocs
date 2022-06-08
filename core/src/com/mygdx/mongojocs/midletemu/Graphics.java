package com.mygdx.mongojocs.midletemu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;

import java.util.HashMap;

public class Graphics {

    public static final int TOP = 1;
    public static final int LEFT = 2;

    public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;
    public OrthographicCamera camera;
    Font currentFont;
    Color currentColor;
    boolean scissorsSet = false;

    static HashMap<String, BitmapFont> bitmapFonts = new HashMap<>();
    public static final String fontChars ="abcçdefghijklmnñopqrstuvwxyzáéíóúABCÇDEFGHIJKLMNÑOPQRSTUVWXYZ0123456789¡!¿?[]()+-*/=,.;:%&#@|<>_";


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


        String hash = currentFont.face+"-"+ currentFont.style+"-"+currentFont.size+"-"+currentColor;
        BitmapFont f;

        if(bitmapFonts.containsKey(hash))
        {
            f = bitmapFonts.get(hash);
        }
        else
        {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("8bitOperatorPlus-Bold.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
            params.minFilter = Texture.TextureFilter.Nearest;
            params.magFilter = Texture.TextureFilter.Nearest;
            params.size = Font.pixelWidths[currentFont.size];
            params.borderWidth = 0;
            //params.borderColor = Color.BLACK;
            params.color = currentColor;
            params.characters = fontChars;
            f = generator.generateFont(params); // font size 12 pixels
            generator.dispose();

            bitmapFonts.put(hash,f);
        }

        batch.begin();
        f.draw(batch, str, x, 208 - y - currentFont.getHeight()/4 );
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
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(currentColor);
        shapeRenderer.rect(x, 208 - y - h, w, 1);
        shapeRenderer.rect(x, 208 - y - h, 1, h);
        shapeRenderer.rect(x, 208 - y, w, 1);
        shapeRenderer.rect(x + w - 1, 208 - y - h, 1, h);
        shapeRenderer.end();
    }

    public void fillRect(int x, int y, int w, int h) {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(currentColor);
        shapeRenderer.rect(x, 208 - y - h, w, h);
        shapeRenderer.end();
    }

    public void fillArc(int cx, int cy, int rx, int ry, int a0, int a1) {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(currentColor);
        shapeRenderer.ellipse(cx, 208 - cy, rx, ry);
        shapeRenderer.end();
    }

    public void fillRoundRect(int i, int i1, int i2, int i3, int i4, int i5) {
        fillRect(i,i1,i2,i3);
    }

    public void drawRoundRect(int i, int i1, int i2, int i3, int i4, int i5) {
        drawRect(i,i1,i2,i3);
    }
}
