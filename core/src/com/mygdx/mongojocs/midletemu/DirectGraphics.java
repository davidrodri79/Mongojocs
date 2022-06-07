package com.mygdx.mongojocs.midletemu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class DirectGraphics {

    Graphics g;

    DirectGraphics(Graphics g)
    {
        this.g = g;
    }

    public static DirectGraphics getDirectGraphics(Graphics g) {
        return new DirectGraphics(g);
    }

    public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3, int c) {

        g.shapeRenderer.setProjectionMatrix(g.camera.combined);
        g.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        g.shapeRenderer.setColor(new Color(c));
        g.shapeRenderer.triangle(x1,y1,x2,y2,x3,y3,g.currentColor,g.currentColor,g.currentColor);
        g.shapeRenderer.end();
    }
}
