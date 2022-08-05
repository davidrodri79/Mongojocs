package com.mygdx.mongojocs.iapplicationemu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.mygdx.mongojocs.iapplicationemu.Graphics;

public class Font {

    public static final int FACE_PROPORTIONAL = 1;
    public static final int STYLE_PLAIN = 4;
    public static final int STYLE_BOLD = 8;
    public static final int SIZE_SMALL = 32;
    public static final int SIZE_MEDIUM = 64;
    public static final int SIZE_LARGE = 128;

    public int flags;

    GlyphLayout layout;

    public Font(int f)
    {
        layout = new GlyphLayout();
        flags = f;
    }

    public static Font getFont(int f) {
        return new Font(f);
    }

    public int getAscent() {
        return 8;
    }

    public int stringWidth(String s)
    {
        String hash = flags+"";
        BitmapFont fnt = null;

        if(!Graphics.bitmapFonts.containsKey(hash))
        {
           Graphics.fontGenerate(flags, Color.WHITE);
        }
        fnt = Graphics.bitmapFonts.get(hash);

        if(fnt != null) {
            layout.setText(fnt, s);
            return (int) layout.width;
        }
        else
            return 0;

    }

    public int _stringWidth(String s)
    {
        String hash = flags+"";
        BitmapFont fnt = null;

        if(!com.mygdx.mongojocs.midletemu.Graphics.bitmapFonts.containsKey(hash))
        {
            Graphics._fontGenerate(flags, Color.WHITE);
        }
        fnt = Graphics.bitmapFonts.get(hash);

        if(fnt != null) {
            layout.setText(fnt, s);
            return (int) layout.width;
        }
        else
            return 0;

    }

    public int getHeight() {
        return 10;
    }
}