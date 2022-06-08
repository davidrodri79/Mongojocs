package com.mygdx.mongojocs;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.mongojocs.midletemu.Canvas;
import com.mygdx.mongojocs.midletemu.Display;
import com.mygdx.mongojocs.pingpoyo.Game;

public class Launcher extends ApplicationAdapter {
	/*SpriteBatch batch;
	Texture img;*/

	Game game;

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
	
	@Override
	public void create () {
		//batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");

		Gdx.input.setInputProcessor(new InputAdapter(){

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {

				for(int i = 0; i < vkeys.length; i++)
				{
					Vector3 touchPos = new Vector3();
					touchPos.set(screenX, screenY, 0);
					game.gc.graphics.camera.unproject(touchPos);

					if(vkeys[i].inside((int)touchPos.x, (int)touchPos.y)) {
						Display.theDisplay.canvas.keyPressed(vkeys[i].code);
						pressedKey = vkeys[i];
					}
				}
				return true;
			}

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {

				if(pressedKey != null) {
					Display.theDisplay.canvas.keyReleased(pressedKey.code);
					pressedKey = null;
				}
				return true;
			}

		});

		game = new Game();
		game.runInit();
	}

	@Override
	public void render () {

		//ScreenUtils.clear(1, 0, 0, 1);
		/*batch.begin();
		batch.draw(img, 0, 0);
		batch.end();*/

		game.runTick();

	}
	
	@Override
	public void dispose () {

		game.runEnd();

		//batch.dispose();
		//img.dispose();
	}
}
