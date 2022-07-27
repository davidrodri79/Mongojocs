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

    public static final int NOKIA_SERIES_40 = 0;
    public static final int NOKIA_SERIES_60 = 1;

    class Device
    {
        String name;
        int screenWidth;
        int screenHeight;

        Device(String n, int sw, int sh)
        {
            this.name = n;
            this.screenWidth = sw;
            this.screenHeight = sh;
        }
    }

    Device devices[] = {
            new Device("Nokia series40", 128, 128),
            new Device("Nokia series60", 176, 208)
    };

    class MongoGame
    {
        String name;
        String assetsFolder;
        int platform;
        int year;
        Class midletClass;
        Texture icon;

        MongoGame(String name, int year, int platform, String folder, Class c)
        {
            this.name = name;
            this.year = year;
            this.platform = platform;
            this.assetsFolder = folder;
            midletClass = c;
        }
    }

    MongoGame catalog[]={

            new MongoGame("3D QBlast 2.0", 2005, NOKIA_SERIES_60, "qblast20", com.mygdx.mongojocs.qblast20.Game.class),
            new MongoGame("Aminoid X", 2003, NOKIA_SERIES_60,"aminoid", com.mygdx.mongojocs.aminoid.Game.class),
            new MongoGame("Astro 3003", 2003, NOKIA_SERIES_60,"astro", com.mygdx.mongojocs.astro.Game.class),
            new MongoGame("BraveWar", 2003, NOKIA_SERIES_60,"bravewar", com.mygdx.mongojocs.bravewar.GameMidletLogic.class),
            new MongoGame("Club Football 2006", 2006, NOKIA_SERIES_60,"clubfootball2006", com.mygdx.mongojocs.clubfootball2006.Game.class),
            new MongoGame("Cerberus Lair", 2003, NOKIA_SERIES_60,"cerberus", com.mygdx.mongojocs.cerberus.CerberusMain.class),
            new MongoGame("Domino", 2005, NOKIA_SERIES_60,"domino", com.mygdx.mongojocs.domino.Game.class),
            new MongoGame("Escape", 2003, NOKIA_SERIES_60,"escape", com.mygdx.mongojocs.escape.EscapeNMain.class),
            new MongoGame("Frozen World", 2004, NOKIA_SERIES_60,"frozenworld", com.mygdx.mongojocs.frozenworld.Game.class),
            new MongoGame("K-Insectors", 2003, NOKIA_SERIES_60,"kinsectors", com.mygdx.mongojocs.kinsectors.KInsectors.class),
            new MongoGame("LMA 2007", 2006, NOKIA_SERIES_60,"lma2007", com.mygdx.mongojocs.lma2007.Game.class),
            new MongoGame("Movistar Racing 2", 2005, NOKIA_SERIES_60,"mr2", com.mygdx.mongojocs.mr2.Game.class),
            //new MongoGame("Movistar Racing 3", 2005, NOKIA_SERIES_60,"mr3", com.mygdx.mongojocs.mr3.Game.class),
            new MongoGame("Ninja Run", 2004, NOKIA_SERIES_60,"ninjarun", com.mygdx.mongojocs.ninjarun.ITGame01.class),
            new MongoGame("No Passeu", 2005, NOKIA_SERIES_40,"nopasseu", com.mygdx.mongojocs.nopasseu.Game.class),
            new MongoGame("Numa the beast", 2004, NOKIA_SERIES_60,"numa", com.mygdx.mongojocs.numa.BeastMain.class),
            //new MongoGame("Petanca", 2004, NOKIA_SERIES_60,"petanca", com.mygdx.mongojocs.petanca.Game.class),
            new MongoGame("Ping Poyo", 2004, NOKIA_SERIES_60,"pingpoyo", com.mygdx.mongojocs.pingpoyo.Game.class),
            new MongoGame("QBlast Ironball", 2005, NOKIA_SERIES_60,"qblast", com.mygdx.mongojocs.qblast.QBlastMain.class),
            new MongoGame("Rescue", 2003, NOKIA_SERIES_60,"rescue", com.mygdx.mongojocs.rescue.rescue.class),
            new MongoGame("Sabotage", 2003, NOKIA_SERIES_60,"sabotage", com.mygdx.mongojocs.sabotage.Game.class),
            new MongoGame("Sanfermines 2006", 2006, NOKIA_SERIES_60,"sanfermines2006", com.mygdx.mongojocs.sanfermines2006.Game.class),
            new MongoGame("Sextron", 2004, NOKIA_SERIES_60,"sextron", com.mygdx.mongojocs.sextron.Game.class),
            new MongoGame("The Mute", 2003, NOKIA_SERIES_60,"themute", com.mygdx.mongojocs.themute.Game.class),
            new MongoGame("Toro 2", 2005, NOKIA_SERIES_60,"toro2", com.mygdx.mongojocs.toro2.Game.class),
            new MongoGame("Ultranaus", 2003, NOKIA_SERIES_60,"ultranaus", com.mygdx.mongojocs.ultranaus.UltranausMain.class),
            new MongoGame("Wrath of Sekhmet", 2003, NOKIA_SERIES_60,"sekhmet", com.mygdx.mongojocs.sekhmet.Sekhmet.class)
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

                            launcher.setScreen(new MIDletRunScreen(launcher, catalog[launcher.selectedGame].midletClass,
                                                                    devices[catalog[launcher.selectedGame].platform].screenWidth,
                                                                    devices[catalog[launcher.selectedGame].platform].screenHeight));
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
            launcher.smallFont.draw(launcher.batch, devices[catalog[i].platform].name, 120, catalog.length*200 - launcher.scrollY - i*200 - 200  + 60);
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
