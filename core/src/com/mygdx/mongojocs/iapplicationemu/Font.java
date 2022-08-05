package com.mygdx.mongojocs.iapplicationemu;

public class Font {
    public static final int FACE_PROPORTIONAL = 1;
    public static final int STYLE_PLAIN = 4;
    public static final int STYLE_BOLD = 8;
    public static final int SIZE_SMALL = 32;
    public static final int SIZE_MEDIUM = 64;
    public static final int SIZE_LARGE = 128;

    public static Font getFont(int flags) {
        return new Font();
    }

    public int getAscent() {
        return 0;
    }

    public int stringWidth(String str) {
        return 0;
    }

    public int getHeight() {
        return 0;
    }
}
