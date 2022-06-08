package com.mygdx.mongojocs.midletemu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;

public class Font {

    public static final int FACE_PROPORTIONAL = 1;

    public static final int STYLE_PLAIN = 0;
    public static final int STYLE_BOLD = 1;

    public static final int SIZE_SMALL = 0;
    public static final int SIZE_MEDIUM = 1;
    public static final int SIZE_LARGE = 2;

    BitmapFont bmpFont;
    static HashMap<String,Font> fonts = new HashMap<>();

    Font()
    {

    }

    public static final String fontChars ="abcçdefghijklmnñopqrstuvwxyzáéíóúABCÇDEFGHIJKLMNÑOPQRSTUVWXYZ0123456789¡!¿?[]()+-*/=,.;:%&#@|<>_";

    public static Font getFont(int face, int style, int size)
    {
        Font f;

        String hash = face+"-"+style+"-"+size;

        if(fonts.containsKey(hash))
        {
            f = fonts.get(hash);
        }
        else
        {
            f = new Font();

            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("8bitOperatorPlus-Bold.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
            params.minFilter = Texture.TextureFilter.Nearest;
            params.magFilter = Texture.TextureFilter.Nearest;
            switch(size)
            {
                case SIZE_SMALL: params.size = 8; break;
                default:
                case SIZE_MEDIUM: params.size = 10; break;
                case SIZE_LARGE: params.size = 14; break;
            }
            params.borderWidth = 0;
            params.borderColor = Color.BLACK;
            params.color = Color.WHITE;
            params.characters = fontChars;
            f.bmpFont = generator.generateFont(params); // font size 12 pixels
            generator.dispose();

            fonts.put(hash,f);
        }

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
