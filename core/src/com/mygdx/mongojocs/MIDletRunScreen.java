package com.mygdx.mongojocs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.mygdx.mongojocs.midletemu.Canvas;
import com.mygdx.mongojocs.midletemu.Display;
import com.mygdx.mongojocs.midletemu.Graphics;
import com.mygdx.mongojocs.midletemu.MIDlet;
import com.mygdx.mongojocs.midletemu.Runnable;
import com.mygdx.mongojocs.midletemu.Thread;

public class MIDletRunScreen implements Screen {

    Launcher launcher;
    OrthographicCamera camera;
    MIDlet game;

    class VirtualKey
    {
        int x;
        int y;
        int w;
        int h;
        int code;
        VirtualKey(int xx, int yy, int ww, int hh, int c)
        {
            x=xx; y=yy; w=ww; h=hh; code = c;
        }
        boolean inside(int xx, int yy)
        {
            if(xx >= x && xx <= x+w && yy >= y && yy <= y+h )
                return true;
            else
                return false;
        }
    }

    VirtualKey vkeys[] =
            {
                    new VirtualKey(0,0,59, 52, Canvas.KEY_NUM1),
                    new VirtualKey(59,0,59, 52, Canvas.KEY_NUM2),
                    new VirtualKey(118,0,59, 52, Canvas.KEY_NUM3),
                    new VirtualKey(0,52,59, 52, Canvas.KEY_NUM4),
                    new VirtualKey(59,52,59, 52, Canvas.KEY_NUM5),
                    new VirtualKey(118,52,59, 52, Canvas.KEY_NUM6),
                    new VirtualKey(0,104,59, 52, Canvas.KEY_NUM7),
                    new VirtualKey(59,104,59, 52, Canvas.KEY_NUM8),
                    new VirtualKey(118,104,59, 52, Canvas.KEY_NUM9),
                    new VirtualKey(0,156,59, 52, -6),
                    new VirtualKey(59,156,59, 52, Canvas.KEY_NUM0),
                    new VirtualKey(118,156,59, 52, -7)
            };
    VirtualKey pressedKey = null;

    MIDletRunScreen(Launcher launcher, Class midletClass)
    {
        this.launcher = launcher;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 176, 208);

        Gdx.input.setInputProcessor(new InputAdapter(){

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {

                for(int i = 0; i < vkeys.length; i++)
                {
                    Vector3 touchPos = new Vector3();
                    touchPos.set(screenX, screenY, 0);
                    Canvas.theCanvas.graphics.camera.unproject(touchPos);
                    //game.gameCanvas.g.camera.unproject(touchPos);

                    if(vkeys[i].inside((int)touchPos.x, 208 - (int)touchPos.y)) {
                        Canvas.theCanvas.keyPressed(vkeys[i].code);
                        pressedKey = vkeys[i];
                    }
                }
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {

                if(pressedKey != null) {
                    Canvas.theCanvas.keyReleased(pressedKey.code);
                    pressedKey = null;
                }
                return true;
            }

            @Override
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
            }


        });


        Display.setSize(176,208);
        MIDlet.appClosed = false;

        try {
            game = (MIDlet) midletClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        game.startApp();
        Thread.currentRunning.runInit();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(MIDlet.appClosed)
        {
            launcher.setScreen(new CatalogScreen(launcher));
            dispose();
        }
        else
            Thread.currentRunning.runTick();

        Graphics.emptyScissors();

        // KEYBOARD LAYOUT
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        for(int i = 0; i<vkeys.length; i++)
        {
            VirtualKey vk = vkeys[i];
            launcher.shapeRenderer.setProjectionMatrix(camera.combined);
            launcher.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            launcher.shapeRenderer.setColor(new Color(1,1,1,0.25f));
            launcher.shapeRenderer.rect(vk.x+4, 208 - vk.h - vk.y+4, vk.w-8, vk.h-8);
            launcher.shapeRenderer.end();

            if(vk == pressedKey) {
                launcher.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                launcher.shapeRenderer.setColor(new Color(1, 1, 1, 0.25f));
                launcher.shapeRenderer.rect(vk.x + 4, 208 - vk.h - vk.y + 4, vk.w - 8, vk.h - 8);
                launcher.shapeRenderer.end();
            }
        }
        Gdx.gl.glDisable(GL20.GL_BLEND);

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
        Thread.currentRunning.runEnd();
    }
}
