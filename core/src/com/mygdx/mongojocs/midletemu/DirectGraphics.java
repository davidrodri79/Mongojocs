package com.mygdx.mongojocs.midletemu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class DirectGraphics {

    public static final int FLIP_HORIZONTAL = 1;
    public static final int FLIP_VERTICAL = 2;
    Graphics g;

    DirectGraphics(Graphics g)
    {
        this.g = g;
    }

    public static DirectGraphics getDirectGraphics(Graphics g) {
        return new DirectGraphics(g);
    }

    public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3, int c) {

        if(g.allClipped) return;

        g.shapeRenderer.setProjectionMatrix(g.camera.combined);
        g.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        g.shapeRenderer.setColor(new Color(c));
        g.shapeRenderer.triangle(x1,y1,x2,y2,x3,y3,g.currentColor,g.currentColor,g.currentColor);
        g.shapeRenderer.end();
    }

    public void drawImage(Image im, int x, int y, int z, int flags) {

        if(g.allClipped) return;

        int u = 0, v = 1, u2 = 1, v2 = 0;

        if((flags & FLIP_HORIZONTAL) != 0)
        {
            u = 1; u2 = 0;
        }

        if((flags & FLIP_VERTICAL) != 0)
        {
            v = 0; v2 = 1;
        }

        if(g.fromImage != null)
            g.fromImage.fbo.begin();

        g.batch.setProjectionMatrix(g.camera.combined);
        g.batch.begin();
        g.batch.draw(im.texture, x, 208 - y - im.getHeight(), im.getWidth(), im.getHeight(), u, v, u2, v2);
        g.batch.end();

        if(g.fromImage != null)
            g.fromImage.fbo.end();

    }
}
