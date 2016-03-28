package parohyapp.mario;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import parohyapp.mario.screens.PlayScreen;

public class TowerClimber extends Game {

	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 800;
	public static final float PPM = 100;


	private SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	public SpriteBatch getBatch() {
		return batch;
	}
}
