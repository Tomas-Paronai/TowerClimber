package parohyapp.mario.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import parohyapp.mario.TowerClimber;
import parohyapp.mario.tools.Update;

/**
 * Created by tomas on 3/24/2016.
 */
public class Hud implements Disposable, Update{
    private Stage stage;
    private Viewport viewport;

    private int lastScore;
    private int lastTime;

    private Label countDownLabel;
    private Label scoreLabel;
    private Label levelNameLabel;

    public Hud(SpriteBatch sb){
        lastScore = 0;

        viewport = new FitViewport(TowerClimber.V_WIDTH,TowerClimber.V_HEIGHT,new OrthographicCamera());
        stage = new Stage(viewport,sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        countDownLabel = new Label(String.valueOf(0),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("Score: %06d", lastScore),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelNameLabel = new Label("LEVEL NAME",new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(countDownLabel).expandX().padTop(10);
        table.add(scoreLabel).expandX().padTop(10);
        table.add(levelNameLabel).expandX().padTop(10);

        stage.addActor(table);
    }

    public Stage getStage() {
        return stage;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void update(float delta) {

    }

    public void setScore(int score) {
        if(lastScore != score){
            lastScore = score;
            scoreLabel.setText(String.format("Score: %06d", score));
        }

    }

    public void setWorldTimer(int worldTimer) {
        if(worldTimer != lastTime){
            countDownLabel.setText(String.valueOf(worldTimer));
            lastTime = worldTimer;
        }
    }

    public void setName(String name){
        levelNameLabel.setText(name);
    }
}
