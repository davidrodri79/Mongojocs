package com.mygdx.mongojocs.iapplicationemu;

import com.mygdx.mongojocs.astro.GamePlay;
import com.mygdx.mongojocs.iapplicationemu.Graphics;

public class Canvas {

    public Graphics graphics;
    public boolean repaintAsked = false;

    public Canvas()
    {
        graphics = new Graphics();
        graphics.init();
    }

    public void processEvent(int type, int param)
    {

    }

    protected void paint(Graphics g)
    {

    }

    public void repaint()
    {
        repaintAsked = true;
    }

    protected void setSoftLabel(int k, String s)
    {

    }

    public int getWidth()
    {
        return Display.width;
    }

    public int getHeight()
    {
        return Display.height;
    }

    public void flushRepaints() {

        if(repaintAsked) {
            graphics.camera.update();
            paint(graphics);
            repaintAsked = false;
        }
    }
}
