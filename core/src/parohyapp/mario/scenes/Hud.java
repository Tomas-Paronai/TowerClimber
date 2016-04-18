package parohyapp.mario.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import parohyapp.mario.TowerClimber;
import parohyapp.mario.scenes.custome.MenuTextButton;
import parohyapp.mario.screens.PlayScreen;
import parohyapp.mario.tools.Update;

/**
 * Created by tomas on 3/24/2016.
 */
public class Hud extends ChangeListener implements Disposable, Update {
    private PlayScreen screen;
    private Stage stage;
    private Viewport viewport;

    private int lastScore;
    private int lastTime;
    private int lastLives;

    private Label countDownLabel;
    private Label scoreLabel;
    private Label livesLabel;
    private Label levelNameLabel;

    private TextButton menuButton;

    public Hud(SpriteBatch sb, PlayScreen screen){
        this.screen = screen;
        lastScore = 0;

        viewport = new FitViewport(TowerClimber.V_WIDTH,TowerClimber.V_HEIGHT,new OrthographicCamera());
        stage = new Stage(viewport,sb);
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        countDownLabel = new Label(String.valueOf(0),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("Score: %06d", lastScore),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        livesLabel = new Label("Lives: "+lastLives,new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelNameLabel = new Label("LEVEL NAME",new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        menuButton = MenuTextButton.createMenuButton("MENU", (int) scoreLabel.getWidth(), (int) scoreLabel.getHeight());
        menuButton.addListener(this);

        table.add(countDownLabel).expandX().padTop(10);
        table.add(scoreLabel).expandX().padTop(10);
        table.add(menuButton).padTop(10f);
        table.add(livesLabel).expandX().padTop(10);
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

    public void setLives(int lives){
        if(lastLives != lives){
            lastLives = lives;
            livesLabel.setText("Lives: "+lives);
        }
    }

    @Override
    public void changed(ChangeEvent event, Actor actor) {
        screen.backToMenu();
    }
}
