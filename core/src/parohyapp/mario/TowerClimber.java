package parohyapp.mario;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import parohyapp.mario.screens.PlayScreen;
import parohyapp.mario.screens.ScreenManager;

public class TowerClimber extends Game {

	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 800;
	public static final float PPM = 100;
	public static final float SCALED_WIDTH = V_WIDTH / PPM;
	public static final float SCALED_HEIGHT = V_HEIGHT / PPM;

	public static String parentDirPath;

	private SpriteBatch batch;
	private ScreenManager manager;

	public TowerClimber(String absolutePath) {
		parentDirPath = absolutePath;
	}


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
