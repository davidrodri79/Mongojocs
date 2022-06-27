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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.mongojocs.midletemu.Display;
import com.mygdx.mongojocs.midletemu.Font;
import com.mygdx.mongojocs.midletemu.MIDlet;

public class CatalogScreen implements Screen {

    Launcher launcher;
    OrthographicCamera camera;
    Vector3 lastTouch;

    class MongoGame
    {
        String name;
        String assetsFolder;
        String platform;
        int year;
        Class midletClass;
        Texture icon;

        MongoGame(String name, int year, String platform, String folder, Class c)
        {
            this.name = name;
            this.year = year;
            this.platform = platform;
            this.assetsFolder = folder;
            midletClass = c;
        }
    }

    MongoGame catalog[]={

            new MongoGame("3D QBlast 2.0", 2005, "Nokia series60", "qblast20", com.mygdx.mongojocs.qblast20.Game.class),
            new MongoGame("Astro 3003", 2003, "Nokia series60","astro", com.mygdx.mongojocs.astro.Game.class),
            new MongoGame("BraveWar", 2003, "Nokia series60","bravewar", com.mygdx.mongojocs.bravewar.GameMidletLogic.class),
            new MongoGame("Club Football 2006", 2006, "Nokia series60","clubfootball2006", com.mygdx.mongojocs.clubfootball2006.Game.class),
            new MongoGame("Cerberus Lair", 2003, "Nokia series60","cerberus", com.mygdx.mongojocs.cerberus.CerberusMain.class),
            new MongoGame("Escape", 2003, "Nokia series60","escape", com.mygdx.mongojocs.escape.EscapeNMain.class),
            new MongoGame("LMA 2007", 2006, "Nokia series60","lma2007", com.mygdx.mongojocs.lma2007.Game.class),
            new MongoGame("Numa the beast", 2004, "Nokia series60","numa", com.mygdx.mongojocs.numa.BeastMain.class),
            new MongoGame("Ping Poyo", 2004, "Nokia series60","pingpoyo", com.mygdx.mongojocs.pingpoyo.Game.class),
            new MongoGame("QBlast Ironball", 2005, "Nokia series60","qblast", com.mygdx.mongojocs.qblast.QBlastMain.class),
            new MongoGame("Sanfermines 2006", 2006, "Nokia series60","sanfermines2006", com.mygdx.mongojocs.sanfermines2006.Game.class),
            new MongoGame("Ultranaus", 2003, "Nokia series60","ultranaus", com.mygdx.mongojocs.ultranaus.UltranausMain.class)
    };

    CatalogScreen(Launcher l)
    {
        this.launcher = l;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 400, 800);

        for(int i=0; i< catalog.length; i++)
        {
            catalog[i].icon = new Texture(catalog[i].assetsFolder+"/icon.png");
        }

        if(launcher.scrollY == -1) launcher.scrollY = catalog.length * 200 - 800;

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {


                Vector3 touchPos = new Vector3();
                touchPos.set(screenX, screenY, 0);
                camera.unproject(touchPos);

                lastTouch = touchPos;
                //game.gameCanvas.g.camera.unproject(touchPos);

                for(int i = 0; i < catalog.length; i++)
                {
                    Rectangle r = new Rectangle(20, catalog.length*200- launcher.scrollY-i*200-200, 400, 200);
                    if(r.contains(touchPos.x, touchPos.y))
                    {
                        int clicked = i;

                        if(clicked == launcher.selectedGame)
                        {
                            MIDlet.setAssetsFolder(catalog[launcher.selectedGame].assetsFolder);

                            launcher.setScreen(new MIDletRunScreen(launcher, catalog[launcher.selectedGame].midletClass));
                            dispose();
                        }
                        else
                        {
                            launcher.selectedGame = clicked;
                        }
                    }
                }



                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {

                return true;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {

                Vector3 touchPos = new Vector3();
                touchPos.set(screenX, screenY, 0);
                camera.unproject(touchPos);

                launcher.scrollY-=touchPos.y - lastTouch.y;

                lastTouch = touchPos;

                if(launcher.scrollY < 0) launcher.scrollY = 0;
                if(launcher.scrollY >= catalog.length * 200 - 800) launcher.scrollY = catalog.length * 200 - 800 - 1;

                return super.touchDragged(screenX, screenY, pointer);
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

        for(int i = 0; i < catalog.length; i++)
        {
            MongoGame g = catalog[i];

            launcher.batch.begin();
            launcher.shapeRenderer.setProjectionMatrix(camera.combined);
            launcher.shapeRenderer.setColor(i == launcher.selectedGame ? Color.GOLD : Color.NAVY);
            launcher.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            launcher.shapeRenderer.rect(20, catalog.length*200 - launcher.scrollY - i*200 - 200 + 20, 360, 160);
            launcher.shapeRenderer.end();
            launcher.batch.end();
            launcher.batch.begin();
            launcher.batch.draw(catalog[i].icon, 40, catalog.length*200 - launcher.scrollY - i*200 - 200 + 40, 64, 64);
            launcher.bigFont.draw(launcher.batch, catalog[i].name, 40, catalog.length*200 - launcher.scrollY - i*200 - 200  + 160);
            launcher.smallFont.draw(launcher.batch, catalog[i].platform, 120, catalog.length*200 - launcher.scrollY - i*200 - 200  + 60);
            launcher.smallFont.draw(launcher.batch, ""+catalog[i].year, 120, catalog.length*200 - launcher.scrollY - i*200 - 200  + 100);
            launcher.batch.end();
        }

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
