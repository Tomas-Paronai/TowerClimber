package parohyapp.mario.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import parohyapp.mario.TowerClimber;

/**
 * Created by tomas on 3/31/2016.
 */
public class MenuScreen implements Screen {

    private Viewport gamePort;
    private Stage menuStage;
    private SpriteBatch batch;

    private Button playButton,exitButton;

    public MenuScreen(){
        gamePort = new ScreenViewport(new OrthographicCamera());
        menuStage = new Stage(gamePort,batch);

        Table table = new Table();
        table.center();
        table.setFillParent(true);


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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
