package com.mygdx.mongojocs.midletemu;

public class FullCanvas extends Canvas {

    public Graphics graphics;

    public FullCanvas()
    {
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

    public int getWidth()
    {
        return 176;
    }

    public int getHeight()
    {
        return 208;
    }
}
