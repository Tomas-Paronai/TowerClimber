package parohyapp.mario.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import parohyapp.mario.tools.data.GameProgress;

/**
 * Created by tomas on 4/11/2016.
 */
public class LevelSelectScreen implements Screen {

    private ScreenManager manager;
    private GameProgress gameProgress;

    private Viewport gamePort;
    private Stage menuStage;
    private SpriteBatch batch;

    public LevelSelectScreen(ScreenManager manager) {
        this.manager = manager;
        gamePort = new ScreenViewport(new OrthographicCamera());
        batch = new SpriteBatch();
        menuStage = new Stage(gamePort,batch);

        initMenu();
    }

    private void initMenu(){
        
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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
        menuStage.dispose();
    }
}
