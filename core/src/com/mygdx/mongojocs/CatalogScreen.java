package com.mygdx.mongojocs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.mongojocs.midletemu.Display;
import com.mygdx.mongojocs.midletemu.Font;
import com.mygdx.mongojocs.midletemu.MIDlet;

public class CatalogScreen implements Screen {

    Launcher launcher;
    OrthographicCamera camera;
    int selectedGame = 0;

    class MongoGame
    {
        String name;
        String assetsFolder;
        Class midletClass;

        MongoGame(String name, String folder, Class c)
        {
            this.name = name;
            this.assetsFolder = folder;
            midletClass = c;
        }
    }

    MongoGame catalog[]={

            new MongoGame("Cerberus Lair", "cerberus", com.mygdx.mongojocs.cerberus.CerberusMain.class),
            new MongoGame("Escape", "escape", com.mygdx.mongojocs.escape.EscapeNMain.class),
            new MongoGame("Numa", "numa", com.mygdx.mongojocs.numa.BeastMain.class),
            new MongoGame("Ping Poyo", "pingpoyo", com.mygdx.mongojocs.pingpoyo.Game.class),
            new MongoGame("QBlast Ironball", "qblast", com.mygdx.mongojocs.qblast.QBlastMain.class),
            new MongoGame("Ultranaus", "ultranaus", com.mygdx.mongojocs.ultranaus.UltranausMain.class)
    };

    CatalogScreen(Launcher l)
    {
        this.launcher = l;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 400, 800);

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {


                Vector3 touchPos = new Vector3();
                touchPos.set(screenX, screenY, 0);
                camera.unproject(touchPos);
                //game.gameCanvas.g.camera.unproject(touchPos);
                if (screenY < 300) {
                    selectedGame += 1;
                    if (selectedGame >= catalog.length)
                        selectedGame = 0;
                } else if (screenY < 500) {
                    MIDlet.setAssetsFolder(catalog[selectedGame].assetsFolder);

                    launcher.setScreen(new MIDletRunScreen(launcher, catalog[selectedGame].midletClass));
                    dispose();
                } else {
                    selectedGame -= 1;
                    if (selectedGame < 0)
                        selectedGame = catalog.length - 1;
                }

                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {

                return true;
            }
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0.2f, 1);

        launcher.batch.setProjectionMatrix(camera.combined);
        launcher.batch.begin();
        launcher.bitmapFont.draw(launcher.batch, catalog[selectedGame].name, 20, 400);
        launcher.batch.end();


        if(Gdx.input.justTouched())
        {
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

    }
}
