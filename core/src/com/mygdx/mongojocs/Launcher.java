package com.mygdx.mongojocs;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.mongojocs.midletemu.Canvas;
import com.mygdx.mongojocs.midletemu.Display;
import com.mygdx.mongojocs.midletemu.MIDlet;
import com.mygdx.mongojocs.numa.BeastMain;
import com.mygdx.mongojocs.pingpoyo.GameCanvas;
import com.mygdx.mongojocs.qblast.QBlastMain;

public class Launcher extends Game {
	/*SpriteBatch batch;
	Texture img;*/
	BitmapFont bitmapFont;
	SpriteBatch batch;
	static MyInterface myInterface;

	public Launcher(MyInterface myInt) {
		myInterface = myInt;
		MIDlet.setAppFilesFolder(myInterface.getAppFilesFolder().toString());
	}

	@Override
	public void create () {
		batch = new SpriteBatch();

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("8bitOperatorPlus-Bold.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
		params.minFilter = Texture.TextureFilter.Nearest;
		params.magFilter = Texture.TextureFilter.Nearest;
		params.size = 42;
		params.borderWidth = 2;
		params.borderColor = Color.BLACK;
		params.color = Color.WHITE;
		bitmapFont = generator.generateFont(params); // font size 12 pixels
		generator.dispose();
		//img = new Texture("badlogic.jpg");

		this.setScreen(new CatalogScreen(this));
	}

	@Override
	public void render () {

		super.render();
		//ScreenUtils.clear(1, 0, 0, 1);
		/*batch.begin();
		batch.draw(img, 0, 0);
		batch.end();*/



	}
	
	@Override
	public void dispose () {



		//batch.dispose();
		//img.dispose();
	}
}
