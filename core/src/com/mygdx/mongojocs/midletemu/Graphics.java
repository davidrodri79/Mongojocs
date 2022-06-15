package com.mygdx.mongojocs.midletemu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.HashMap;

public class Graphics {

    public static final int HCENTER = 1;
    public static final int VCENTER = 2;
    public static final int LEFT = 4;
    public static final int RIGHT = 8;
    public static final int TOP = 16;
    public static final int BOTTOM = 32;
    public static final int BASELINE = 64;

    public static final int SOLID = 0;
    public static final int DOTTED = 1;


    public Image fromImage = null;
    public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;
    public OrthographicCamera camera, textCamera;
    Font currentFont;
    Color currentColor;
    static int scissorsSet = 0;
    boolean allClipped = false;
    int clipx,clipy,clipw,cliph;

    public static HashMap<String, BitmapFont> bitmapFonts = new HashMap<>();
    public static final String fontChars ="abcçdefghijklmnñopqrstuvwxyzáéíóúABCÇDEFGHIJKLMNÑOPQRSTUVWXYZÁÉÍÓÚ0123456789¡!¿?[]()+-*/=,.;:%&#@|<>_'\"";


    public Graphics()
    {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Display.width, Display.height);
        textCamera = new OrthographicCamera();
        textCamera.setToOrtho(true, Display.width, Display.height);
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        clipx=0; clipy=0; clipw=Display.width; cliph=Display.height;
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

    public static BitmapFont fontGenerate(int face, int style, int size, Color color)
    {
        String hash = face+"-"+ style+"-"+size+"-"+color;
        String hashNoColor = face+"-"+ style+"-"+size;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("8bitOperatorPlus-Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.minFilter = Texture.TextureFilter.Nearest;
        params.magFilter = Texture.TextureFilter.Nearest;
        params.size = Font.pixelWidths[size];
        params.borderWidth = 0;
        //params.borderColor = Color.BLACK;
        params.color = color;
        params.characters = fontChars;
        BitmapFont f = generator.generateFont(params); // font size 12 pixels
        generator.dispose();

        bitmapFonts.put(hash,f);
        bitmapFonts.put(hashNoColor, f);

        return f;
    }
    
    public void drawString(String str, int x, int y, int flags) {


        String hash = currentFont.face+"-"+ currentFont.style+"-"+currentFont.size+"-"+currentColor;
        BitmapFont f;

        if(bitmapFonts.containsKey(hash))
        {
            f = bitmapFonts.get(hash);
        }
        else
        {
            f = fontGenerate(currentFont.face, currentFont.style, currentFont.size, currentColor);

        }

        if((flags & HCENTER) != 0)
        {
            x -= currentFont.stringWidth(str)/2;
        }

        if(fromImage == null)
            Display.fbo.begin();
        else
            fromImage.fbo.begin();

        batch.setProjectionMatrix(textCamera.combined);
        batch.begin();
        f.draw(batch, str, x,  Display.height - y);
        batch.end();

        if(fromImage == null)
            Display.fbo.end();
        else
            fromImage.fbo.end();
    }

    public void setClip(int destX, int destY, int sizeX, int sizeY) {

        allClipped = false;

        int maxx = fromImage == null ? Display.width : fromImage.getWidth();
        int maxy = fromImage == null ? Display.height : fromImage.getHeight();

        if(destX < 0) { sizeX += destX; destX = 0; }
        if(destX >= maxx) destX = maxx;
        if(destY < 0) { sizeY+= destY; destY = 0; }
        if(destY >= maxy) destY = maxy;
        if(destX + sizeX >= maxx) sizeX = maxx - destX;
        if(destY + sizeY >= maxy) sizeY = maxy - destY;

        clipx=destX; clipy=destY; clipw=sizeX; cliph=sizeY;

        if(sizeX <= 0) { allClipped = true; return;}
        if(sizeY <= 0) { allClipped = true; return;}

    }

    public void clipRect(int i, int i1, int sizeX, int sizeY) {
    }

    public void drawImage(Image img, int x, int y, int flags)
    {
        if(allClipped) return;

        {

            int w = img.texture.getWidth();
            int h = img.texture.getHeight();
            float u = 0;
            float v = 0;
            float u2 = 1;
            float v2 = 1;

            //Clipping
            if(x < clipx)
            {
                int dx = clipx - x;
                x += dx; w -= dx; u+= (float)dx/img.texture.getWidth();
            }
            if(x + w > clipx + clipw)
            {
                int dx = (x + w) - (clipx + clipw);
                w -= dx; u2-= (float)dx/img.texture.getWidth();
            }
            if(y < clipy)
            {
                int dy = clipy - y;
                y += dy; h -= dy; v+= (float)dy/img.texture.getHeight();
            }
            if(y + h > clipy + cliph)
            {
                int dy = (y + h) - (clipy + cliph);
                h -= dy; v2-= (float)dy/img.texture.getHeight();
            }


            int finaly = y;

            if(fromImage == null)
                Display.fbo.begin();
            else
                fromImage.fbo.begin();

            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            batch.draw(img.texture, x, finaly, w, h, u, v, u2, v2 );
            batch.end();

            if(fromImage == null)
                Display.fbo.end();
            else
                fromImage.fbo.end();
        }

    }
    
    public void drawRect(int x, int y, int w, int h)
    {
        if(allClipped) return;

        if(fromImage == null)
            Display.fbo.begin();
        else
            fromImage.fbo.begin();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(currentColor);
        shapeRenderer.rect(x, y, w, 1);
        shapeRenderer.rect(x, y, 1, h);
        shapeRenderer.rect(x, y + h, w, 1);
        shapeRenderer.rect(x + w - 1, y, 1, h);
        shapeRenderer.end();

        if(fromImage == null)
            Display.fbo.end();
        else
            fromImage.fbo.end();
    }

    public void fillRect(int x, int y, int w, int h) {

        if(allClipped) return;

        int finaly = y;

        if(fromImage == null)
            Display.fbo.begin();
        else
            fromImage.fbo.begin();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(currentColor);
        shapeRenderer.rect(x, finaly, w, h);
        shapeRenderer.end();

        if(fromImage == null)
            Display.fbo.end();
        else
            fromImage.fbo.end();
    }

    public void fillArc(int cx, int cy, int rx, int ry, int a0, int a1)
    {
        if(allClipped) return;

        if(fromImage == null)
            Display.fbo.begin();
        else
            fromImage.fbo.begin();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(currentColor);
        shapeRenderer.ellipse(cx, cy, rx, ry);
        shapeRenderer.end();

        if(fromImage == null)
            Display.fbo.end();
        else
            fromImage.fbo.end();
    }

    public void drawArc(int cx, int cy, int rx, int ry, int a0, int a1)
    {
        if(allClipped) return;

        if(fromImage == null)
            Display.fbo.begin();
        else
            fromImage.fbo.begin();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(currentColor);
        shapeRenderer.ellipse(cx, cy, rx, ry);
        shapeRenderer.end();

        if(fromImage == null)
            Display.fbo.end();
        else
            fromImage.fbo.end();
    }

    public void fillRoundRect(int i, int i1, int i2, int i3, int i4, int i5) {
        fillRect(i,i1,i2,i3);
    }

    public void drawRoundRect(int i, int i1, int i2, int i3, int i4, int i5) {
        drawRect(i,i1,i2,i3);
    }

    public void drawLine(int x1, int y1, int x2, int y2) {
        if(allClipped) return;

        if(fromImage == null)
            Display.fbo.begin();
        else
            fromImage.fbo.begin();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(currentColor);
        shapeRenderer.rectLine(x1+1,y1+1,x2+1,y2+1,1);
        shapeRenderer.end();

        if(fromImage == null)
            Display.fbo.end();
        else
            fromImage.fbo.end();
    }
}
