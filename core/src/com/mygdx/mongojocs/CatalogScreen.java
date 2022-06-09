package com.mygdx.mongojocs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.mongojocs.midletemu.MIDlet;

public class CatalogScreen implements Screen {

    Launcher launcher;

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

            new MongoGame("Numa", "numa", com.mygdx.mongojocs.numa.BeastMain.class),
            new MongoGame("Ping Poyo", "pingpoyo", com.mygdx.mongojocs.pingpoyo.Game.class),
            new MongoGame("QBlast Ironball", "qblast", com.mygdx.mongojocs.qblast.QBlastMain.class)
    };

    CatalogScreen(Launcher launcher)
    {
        this.launcher = launcher;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0.2f, 1);

        if(Gdx.input.justTouched())
        {
            int selectedGame = 2;

            MIDlet.setAssetsFolder(catalog[selectedGame].assetsFolder);

            launcher.setScreen(new MIDletRunScreen(launcher, catalog[selectedGame].midletClass));
            dispose();
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
