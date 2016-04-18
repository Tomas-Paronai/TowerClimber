package parohyapp.mario.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import parohyapp.mario.TowerClimber;
import parohyapp.mario.screens.tools.MenuControl;
import parohyapp.mario.screens.tools.ScreenManager;
import parohyapp.mario.screens.tools.ScreenSet;

/**
 * Created by tomas on 3/31/2016.
 */
public class MenuScreen implements Screen,MenuControl {
    private static final String TAG = "MenuScreen";

    private ScreenManager manager;

    private Viewport gamePort;
    private Stage menuStage;
    private SpriteBatch batch;

    private Skin skin;
    private TextButton.TextButtonStyle textButtonStyle;

    private TextButton playButton,exitButton;

    public MenuScreen(ScreenManager manager){
        this.manager = manager;
        gamePort = new ScreenViewport(new OrthographicCamera());
        batch = new SpriteBatch();
        menuStage = new Stage(gamePort,batch);

        initSkin();
        initMenu();
    }

    @Override
    public void initSkin(){
        skin = new Skin();
        Pixmap pixmap = new Pixmap((int) (Gdx.app.getGraphics().getWidth() * MenuControl.SCALE_W), (int) (Gdx.app.getGraphics().getHeight() * MenuControl.SCALE_H), Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();

        skin.add("white", new Texture(pixmap));

        BitmapFont bfont = new BitmapFont();
        bfont.getData().setScale(1.5f);
        skin.add("default",bfont);

        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white",Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white",Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("white",Color.CYAN);
        textButtonStyle.over = skin.newDrawable("white",Color.LIGHT_GRAY);

        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);
    }

    @Override
    public void initMenu(){
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        playButton = new TextButton("PLAY",textButtonStyle);
        table.add(playButton);
        table.row();

        exitButton = new TextButton("EXIT",textButtonStyle);
        table.add(exitButton).padTop(10f);

        menuStage.addActor(table);

        initListener();
    }

    @Override
    public void initListener(){
        Gdx.input.setInputProcessor(menuStage);

        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeScreen(ScreenSet.PLAY);
            }
        });

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeScreen(ScreenSet.EXIT);
            }
        });
    }

    @Override
    public void changeScreen(ScreenSet nextScreen){
        manager.setCurrentScreen(nextScreen);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        menuStage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        menuStage.draw();
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
        skin.dispose();
    }
}
