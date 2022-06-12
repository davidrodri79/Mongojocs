package com.mygdx.mongojocs.midletemu;

public class Canvas extends Displayable {
    public static final int KEY_NUM0 = 48;
    public static final int KEY_NUM1 = 49;
    public static final int KEY_NUM2 = 50;
    public static final int KEY_NUM3 = 51;
    public static final int KEY_NUM4 = 52;
    public static final int KEY_NUM5 = 53;
    public static final int KEY_NUM6 = 54;
    public static final int KEY_NUM7 = 55;
    public static final int KEY_NUM8 = 56;
    public static final int KEY_NUM9 = 57;
    public static final int KEY_POUND = 35;
    public static final int KEY_STAR = 42;

    public static Canvas theCanvas = null;
    public Graphics graphics;

    public Canvas()
    {
        theCanvas = this;
        graphics = new Graphics();
    }

    public void repaint()
    {
        graphics.camera.update();
        paint(graphics);

    }

    public void paint(Graphics g)
    {

    }

    public void serviceRepaints()
    {

    }

    public void keyPressed(int keycode)
    {

    }

    public void keyReleased(int keycode)
    {

    }

}
