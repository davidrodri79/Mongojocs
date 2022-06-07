package com.mygdx.mongojocs.midletemu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Font {

    public static final int FACE_PROPORTIONAL = 1;

    public static final int STYLE_PLAIN = 0;
    public static final int STYLE_BOLD = 1;

    public static final int SIZE_SMALL = 0;
    public static final int SIZE_MEDIUM = 1;
    public static final int SIZE_LARGE = 2;

    BitmapFont bmpFont;

    Font()
    {

    }

    public static Font getFont(int p1, int p2, int p3)
    {
        Font f = new Font();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("8bitOperatorPlus-Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 10;
        params.borderWidth = 1;
        params.borderColor = Color.BLACK;
        params.color = Color.WHITE;
        f.bmpFont = generator.generateFont(params); // font size 12 pixels

        return f;
    }

    public int getHeight()
    {
        return (int)bmpFont.getCapHeight();
    }

    public int stringWidth(String s)
    {
        return s.length() * 10;
    }

    public int charWidth(char c) {
        return 10;
    }
}
