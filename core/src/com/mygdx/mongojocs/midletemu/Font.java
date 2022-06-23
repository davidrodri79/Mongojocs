package com.mygdx.mongojocs.midletemu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;

public class Font {

    public static final int FACE_PROPORTIONAL = 1;

    public static final int STYLE_PLAIN = 0;
    public static final int STYLE_BOLD = 1;

    public static final int SIZE_SMALL = 0;
    public static final int SIZE_MEDIUM = 1;
    public static final int SIZE_LARGE = 2;

    public static final int pixelWidths[] = { 8, 10, 12};
    public static final int pixelHeights[] = { 10, 12, 14};

    int face;
    int style;
    int size;

    GlyphLayout layout;

    Font()
    {
        layout = new GlyphLayout();
    }

    public static Font getFont(int face, int style, int size)
    {
        Font f = new Font();

       f.face = face;
       f.style = style;
       f.size = size;

        return f;
    }

    public int getHeight()
    {
        return pixelHeights[size];
    }

    public int stringWidth(String s)
    {
        String hash = face+"-"+ style+"-"+size;
        BitmapFont fnt = null;

        if(Graphics.bitmapFonts.containsKey(hash))
        {
            fnt = Graphics.bitmapFonts.get(hash);
        }
        else
        {
            fnt = Graphics.fontGenerate(face, style, size, Color.WHITE);
        }

        layout.setText( fnt, s);
        return (int)layout.width;

    }

    public int charWidth(char c) {

        char array[]={c};
        return stringWidth(new String(array));

    }
}
