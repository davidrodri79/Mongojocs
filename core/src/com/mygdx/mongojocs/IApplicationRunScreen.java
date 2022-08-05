package com.mygdx.mongojocs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

import com.mygdx.mongojocs.iapplicationemu.IApplication;
import com.mygdx.mongojocs.iapplicationemu.Display;
import com.mygdx.mongojocs.iapplicationemu.Canvas;
import com.mygdx.mongojocs.iapplicationemu.Graphics;





public class IApplicationRunScreen implements Screen {

    Launcher launcher;
    OrthographicCamera camera;
    IApplication game;
    Thread thread;

    class VirtualKey
    {
        int x;
        int y;
        int w;
        int h;
        int code;
        String label;
        VirtualKey(int xx, int yy, int ww, int hh, int c, String l)
        {
            x=xx; y=yy; w=ww; h=hh; code = c; label=l;
        }
        boolean inside(int xx, int yy)
        {
            if(xx >= x && xx <= x+w && yy >= y && yy <= y+h )
                return true;
            else
                return false;
        }
    }

    class LayoutConf
    {
        float alpha;
        boolean numbers;
        LayoutConf(float a, boolean num)
        {
            alpha = a; numbers = num;
        }
    }

    LayoutConf layouts[] = {
            new LayoutConf(0.5f, true),
            new LayoutConf(0.5f, false),
            new LayoutConf(0.25f, false),
            new LayoutConf(0.0f, false)
    };

    VirtualKey vkeys[] =
            {
                    new VirtualKey(176-35,5,30, 30, -1, "o"),
                    new VirtualKey(0,39,59, 39, Display.KEY_1, "1"),
                    new VirtualKey(59,39,59, 39, Display.KEY_2, "2"),
                    new VirtualKey(118,39,59, 39, Display.KEY_3, "3"),
                    new VirtualKey(0,78,59, 39, Display.KEY_4, "4"),
                    new VirtualKey(59,78,59, 39, Display.KEY_5, "5"),
                    new VirtualKey(118,78,59, 39, Display.KEY_6, "6"),
                    new VirtualKey(0,117,59, 39, Display.KEY_7, "7"),
                    new VirtualKey(59,117,59, 39, Display.KEY_8, "8"),
                    new VirtualKey(118,117,59, 39, Display.KEY_9, "9"),
                    new VirtualKey(0,156,59, 39, Display.KEY_SOFT1, ""),
                    new VirtualKey(59,156,59, 39, Display.KEY_0, "0"),
                    new VirtualKey(118,156,59, 39, Display.KEY_SOFT2, "")
            };
    VirtualKey pressedKey = null;

    IApplicationRunScreen(Launcher l, Class iappClass, int screenWidth, int screenHeight)
    {
        this.launcher = l;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 176, 208);

        Gdx.input.setInputProcessor(new InputAdapter(){

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {

                for(int i = 0; i < vkeys.length; i++)
                {
                    Vector3 touchPos = new Vector3();
                    touchPos.set(screenX, screenY, 0);
                    camera.unproject(touchPos);
                    //game.gameCanvas.g.camera.unproject(touchPos);

                    if(vkeys[i].inside((int)touchPos.x, 208 - (int)touchPos.y)) {
                        if(vkeys[i].code == -1)
                        {
                            launcher.currentLayout = (launcher.currentLayout+1)%layouts.length;
                        }
                        else {
                            Display.theCanvas.processEvent(Display.KEY_PRESSED_EVENT, vkeys[i].code);
                            pressedKey = vkeys[i];
                        }
                    }
                }
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {

                if(pressedKey != null) {
                    Display.theCanvas.processEvent(Display.KEY_RELEASED_EVENT, pressedKey.code);
                    pressedKey = null;
                }
                return true;
            }

            /*@Override
            public boolean keyDown(int keycode)
            {
                if(keycode == Input.Keys.RIGHT)
                    Canvas.theCanvas.keyPressed(Canvas.KEY_NUM6);
                return true;
            }

            @Override
            public boolean keyUp(int keycode)
            {
                if(keycode == Input.Keys.RIGHT)
                    Canvas.theCanvas.keyReleased(Canvas.KEY_NUM6);
                return true;
            }*/


        });


        Display.setSize(screenWidth,screenHeight);
        IApplication.appClosed = false;

        try {
            game = (IApplication) iappClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        thread = new Thread(game);
        thread.start();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.app.log("MIDletRunScreen", "render()");
        if(IApplication.appClosed)
        {
            launcher.setScreen(new CatalogScreen(launcher));
            dispose();
        }
        //else
        //   Thread.currentRunning.runTick();

        //Graphics.emptyScissors();
        if(Display.theCanvas != null)
            Display.theCanvas.flushRepaints();

        int px = (176 - Display.width) / 2;
        int py = (208 - Display.height) / 2;

        launcher.shapeRenderer.setProjectionMatrix(camera.combined);
        launcher.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        launcher.shapeRenderer.setColor(Color.BLACK);
        launcher.shapeRenderer.rect(0, 0, 176, 208);
        launcher.shapeRenderer.end();

        launcher.batch.setProjectionMatrix(camera.combined);
        launcher.batch.begin();
        launcher.batch.draw(Display.screenBuffer, px, py, Display.width, Display.height, 0, 1, 1, 0);
        launcher.batch.end();

        // KEYBOARD LAYOUT

        for(int i = 0; i<vkeys.length; i++)
        {
            VirtualKey vk = vkeys[i];

            int x = vk.x+4;
            int y = 208 - vk.y - vk.h - 4;
            int w = vk.w - 8;
            int h = vk.h - 8;
            float alpha = vk.code == -1 ? 0.5f : (vk == pressedKey ? 0.25f : layouts[launcher.currentLayout].alpha);

            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            launcher.shapeRenderer.setProjectionMatrix(camera.combined);
            launcher.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            launcher.shapeRenderer.setColor(new Color(1,1,1,alpha));
            launcher.shapeRenderer.rect(x, y, w, h);
            if(vk.code == Display.KEY_2)
                launcher.shapeRenderer.triangle(x+4,y+4,x+w-4,y+4,x+w/2,y+h-4);
            if(vk.code == Display.KEY_8)
                launcher.shapeRenderer.triangle(x+4,y+h-4,x+w-4,y+h-4,x+w/2,y+4);
            if(vk.code == Display.KEY_4)
                launcher.shapeRenderer.triangle(x+4,y+h/2,x+w-4,y+h-4,x+w-4,y+4);
            if(vk.code == Display.KEY_6)
                launcher.shapeRenderer.triangle(x+w-4,y+h/2,x+4,y+h-4,x+4,y+4);
            if(vk.code == Display.KEY_5)
                launcher.shapeRenderer.circle(x+w/2, y+h/2, h/2 - 4);
            launcher.shapeRenderer.end();

            if(vk == pressedKey) {
                launcher.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                launcher.shapeRenderer.setColor(new Color(1, 1, 1, 0.25f));
                launcher.shapeRenderer.rect(x, y, w, h);
                launcher.shapeRenderer.end();
            }
            Gdx.gl.glDisable(GL20.GL_BLEND);

            if(layouts[launcher.currentLayout].numbers || vk.code == -1) {
                launcher.shapeRenderer.setColor(new Color(1,1,1,alpha));
                launcher.batch.setProjectionMatrix(camera.combined);
                launcher.batch.begin();
                launcher.keysFont.draw(launcher.batch, vk.label, x + w / 2 - 4, y + h / 2 + 10);
                launcher.batch.end();
            }
        }

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        //Thread.currentRunning.runEnd();
        Graphics.bitmapFonts.clear();
    }
}