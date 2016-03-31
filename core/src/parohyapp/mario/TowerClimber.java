package parohyapp.mario;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import parohyapp.mario.screens.PlayScreen;
import parohyapp.mario.screens.ScreenManager;

public class TowerClimber extends Game {

	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 800;
	public static final float PPM = 100;

	private SpriteBatch batch;
	private ScreenManager manager;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new ScreenManager(this);
		setScreen(manager.getScreen());
	}

	@Override
	public void render () {
		super.render();
	}

	public SpriteBatch getBatch() {
		return batch;
	}
}
