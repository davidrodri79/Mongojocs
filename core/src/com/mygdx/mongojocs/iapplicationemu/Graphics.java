package com.mygdx.mongojocs.iapplicationemu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.mongojocs.iapplicationemu.Display;

import java.util.HashMap;

public class Graphics {

    public static boolean generatingFont = false;

    static class FontGenerateTask implements Runnable {

        int flags;
        Color color;

        FontGenerateTask(int f, Color c) {
            flags = f;
            color = c;
        }

        @Override
        public void run() {
            Graphics._fontGenerate(flags, color);
            generatingFont = false;
        }
    }

    public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;
    public OrthographicCamera camera, textCamera;
    Font currentFont;
    Color currentColor;

    public static HashMap<String, BitmapFont> bitmapFonts = new HashMap<>();
    public static final String fontChars = "abcçdefghijklmnñopqrstuvwxyzáéíóúABCÇDEFGHIJKLMNÑOPQRSTUVWXYZÁÉÍÓÚ0123456789¡!¿?[]()+-*/=,.;:%&#@|<>_'\"";

    public void init() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Display.width, Display.height);
        textCamera = new OrthographicCamera();
        textCamera.setToOrtho(true, Display.width, Display.height);
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        //currentFont = Font.getDefaultFont();
    }

    public void lock() {
    }

    public void unlock(boolean b) {
    }

    public void drawImage(Image img, int x, int y)
    {

        System.out.println("Pinta una imagen");
        if (img.texture == null) return;

        {
            int w = img.texture.getWidth();
            int h = img.texture.getHeight();
            float u = 0;
            float v = 0;
            float u2 = 1;
            float v2 = 1;

            int finaly = y;

            Display.fbo.begin();

            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            batch.draw(img.texture, x, finaly, w, h, u, v, u2, v2);
            batch.end();

            Display.fbo.end();

        }
    }

    public void setColor(int c) {
        currentColor = new Color(((c & 0xff0000) >> 16) / 255.f,
                ((c & 0xff00) >> 8) / 255.f,
                (c & 0xff) / 255.f,
                1);
    }

    public void fillRect(int x, int y, int w, int h)
    {
        if(w<0 || h<0) return;

        Display.fbo.begin();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        int finaly = y;

        shapeRenderer.setColor(currentColor);
        shapeRenderer.rect(x, finaly, w, h);

        shapeRenderer.end();

        Display.fbo.end();
    }

    public void setFont(Font f)
    {
        currentFont = f;
    }

    public static void fontGenerate(int flags, Color color) {
        generatingFont = true;
        Gdx.app.postRunnable(new FontGenerateTask(flags, color));
        while (generatingFont) {
            try {
                Gdx.app.log("Graphics", "Waiting for main thread to generate font...");
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static BitmapFont _fontGenerate(int flags, Color color) {
        String hash = flags + "-" + color;
        String hashNoColor = ""+flags;

        String fontFile;

        /*if ((style & com.mygdx.mongojocs.midletemu.Font.STYLE_ITALIC) != 0)
            fontFile = "prazo-cursiva.otf";
        else {*/
            if ((flags & Font.STYLE_BOLD) != 0)
                fontFile = "8bitOperatorPlus-Bold.ttf";
            else
                fontFile = "8bitOperatorPlus-Regular.ttf";
        //}

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontFile));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.minFilter = Texture.TextureFilter.Nearest;
        params.magFilter = Texture.TextureFilter.Nearest;
        params.size = Font.getPixelWidth(flags);
        params.borderWidth = 0; //style == Font.STYLE_BOLD ? 1 : 0;
        params.borderColor = color;
        params.color = color;
        params.characters = fontChars;
        BitmapFont f = generator.generateFont(params); // font size 12 pixels
        generator.dispose();

        bitmapFonts.put(hash, f);
        bitmapFonts.put(hashNoColor, f);

        return f;
    }

    public void drawString(String str, int x, int y) {

        String hash = currentFont.flags + "-" + currentColor;
        BitmapFont f = null;

        // Generate font if needed
        if (!bitmapFonts.containsKey(hash)) {
            _fontGenerate(currentFont.flags, currentColor);
        }
        f = bitmapFonts.get(hash);

        // Draw text

        Display.fbo.begin();

        batch.setProjectionMatrix(textCamera.combined);
        batch.begin();
        if (f != null) f.draw(batch, str, x, Display.height - y - 2);
        batch.end();

        Display.fbo.end();

    }

    public void drawLine(int x1, int y1, int x2, int y2) {

        /*int minx, maxx, miny, maxy;

        if (x1 < x2) {
            minx = x1;
            maxx = x2;
        } else {
            minx = x2;
            maxx = x1;
        }

        if (y1 < y2) {
            miny = y1;
            maxy = y2;
        } else {
            miny = y2;
            maxy = y1;
        }*/

        Display.fbo.begin();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(currentColor);
        shapeRenderer.rectLine(x1 + 1, y1 + 1, x2 + 1, y2 + 1, 1);
        shapeRenderer.end();

        Display.fbo.end();
    }

    public void drawRect(int x, int y, int w, int h) {

        Display.fbo.begin();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(currentColor);
        shapeRenderer.rect(x, y, w, 1);
        shapeRenderer.rect(x, y, 1, h);
        shapeRenderer.rect(x, y + h, w, 1);
        shapeRenderer.rect(x + w - 1, y, 1, h);
        shapeRenderer.end();

        Display.fbo.end();

    }
}
