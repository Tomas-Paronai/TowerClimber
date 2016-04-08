package parohyapp.mario.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import javax.naming.Name;

import parohyapp.mario.GameMaster;
import parohyapp.mario.TowerClimber;
import parohyapp.mario.scenes.Hud;
import parohyapp.mario.tools.WorldManager;

/**
 * Created by tomas on 3/24/2016.
 */
public class PlayScreen implements Screen {
    private final static String TAG = "PlayScreen";

    //main variables
    private TowerClimber game;
    private GameMaster gameMaster;
    private WorldManager worldManager;
    private OrthographicCamera gameCam;
    private Viewport gamePort;

    //hud related
    private Hud hud;

    //tiled map renderer
    private OrthogonalTiledMapRenderer mapRenderer;
    private Name lights;

    public PlayScreen(TowerClimber game){
        //main variables
        this.game = game;
        gameMaster = new GameMaster(this);
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(TowerClimber.SCALED_WIDTH,TowerClimber.SCALED_HEIGHT,gameCam);


        //hud related
        hud = new Hud(game.getBatch());

        loadGame();

    }

    public void loadGame(){
        worldManager = new WorldManager(game,gameMaster,this);
        worldManager.loadWorld();

        //map renderer
        mapRenderer = new OrthogonalTiledMapRenderer(worldManager.getMap(), 1 / TowerClimber.PPM);

        hud.setName(worldManager.getMapName());

        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
    }


    @Override
    public void show() {

    }

    public void update(float delta){
        worldManager.update(delta);
        gameMaster.update(delta);

        hud.setWorldTimer(gameMaster.getTime());
        hud.setScore(gameMaster.getScore());
        hud.setLives(gameMaster.getLives());

        moveCamera();
        gameCam.update();

        mapRenderer.setView(gameCam);
    }


    @Override
    public void render(float delta) {
        update(delta);

        //clear screen
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glActiveTexture(GL20.GL_COLOR_BUFFER_BIT);

        //render map
        mapRenderer.render();

        //render world
        worldManager.render(game.getBatch(), gameCam);


        //render HUD
        game.getBatch().setProjectionMatrix(hud.getStage().getCamera().combined);
        hud.getStage().draw();
    }

    private void moveCamera(){
        gameCam.position.x = worldManager.getClimber().getB2Body().getPosition().x;
        gameCam.position.y = worldManager.getClimber().getB2Body().getPosition().y + 1.5f;
        gameCam.position.x = MathUtils.clamp(gameCam.position.x, gameCam.viewportWidth * .5f, worldManager.getMapWidth() - gameCam.viewportWidth * .5f);
        gameCam.position.y = MathUtils.clamp(gameCam.position.y, gameCam.viewportHeight * .5f, worldManager.getMapHeight() - gameCam.viewportHeight * .5f);
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
        worldManager.dispose();
        mapRenderer.dispose();
        hud.dispose();
        gameMaster.dispose();
        game.getBatch().dispose();
    }

    public WorldManager getWorldManager(){
        return worldManager;
    }

    public GameMaster getGameMaster() {
        return gameMaster;
    }

}
