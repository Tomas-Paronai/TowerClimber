package parohyapp.mario.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

import parohyapp.mario.GameMaster;
import parohyapp.mario.TowerClimber;
import parohyapp.mario.scenes.Hud;
import parohyapp.mario.sprites.animated.Climber;
import parohyapp.mario.sprites.parent.InteractiveSpriteEntity;
import parohyapp.mario.tools.WorldContactListener;
import parohyapp.mario.tools.WorldFactory;

/**
 * Created by tomas on 3/24/2016.
 */
public class PlayScreen implements Screen {

    //main variables
    private TowerClimber game;
    private GameMaster gameMaster;
    private OrthographicCamera gameCam;
    private Viewport gamePort;

    //hud related
    private Hud hud;

    //tiled map
//    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    //box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private Climber climber;
    private ArrayList<InteractiveSpriteEntity> entities;

    //textures
    //private TextureAtlas textureAtlas;

    public PlayScreen(TowerClimber game){
        //main variables
        this.game = game;
        gameMaster = new GameMaster(this);
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(TowerClimber.V_WIDTH / TowerClimber.PPM,TowerClimber.V_HEIGHT / TowerClimber.PPM,gameCam);

        //hud related
        hud = new Hud(game.getBatch());
        loadGame();

    }

    public void loadGame(){
        //tiled map related
        map = gameMaster.getLevelMap();
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / TowerClimber.PPM);

        if(map.getProperties().containsKey("Name")){
            hud.setName((String) map.getProperties().get("Name"));
        }
        else{
            hud.setName("unknown");
        }


        gameCam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2,0);

        //textures
        //textureAtlas = new TextureAtlas("climber.pack");

        //box2d variables
        world = new World(new Vector2(0,-15),true);
        b2dr = new Box2DDebugRenderer();

        entities = new ArrayList<InteractiveSpriteEntity>();
        new WorldFactory(world,map,this);

        //collision detection setup
        world.setContactListener(new WorldContactListener());
    }


    @Override
    public void show() {

    }

    public void update(float delta){
        world.step(1 / 60f, 6, 2);
        gameCam.position.y = climber.getB2Body().getPosition().y + 3.5f;


        gameMaster.update(delta);
        updateEntities(delta);

        hud.setWorldTimer(gameMaster.getTime());
        hud.setScore(gameMaster.getScore());

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

        //render objects
        b2dr.render(world, gameCam.combined);

        //render climber
        game.getBatch().setProjectionMatrix(gameCam.combined);
        renderEntities();


        //render HUD
        game.getBatch().setProjectionMatrix(hud.getStage().getCamera().combined);
        hud.getStage().draw();
    }

    private void renderEntities(){
        game.getBatch().begin();
        for(InteractiveSpriteEntity ent : entities){
            ent.draw(game.getBatch());
        }
        game.getBatch().end();
    }

    private void updateEntities(float delta){
        for(InteractiveSpriteEntity ent : entities){
            ent.update(delta);
        }
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
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
        map.dispose();
        mapRenderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
        gameMaster.dispose();
        game.getBatch().dispose();
    }

    public Climber getClimber() {
        return climber;
    }

    public void setClimber(Climber climber) {
        this.climber = climber;
        entities.add(climber);
    }

    public ArrayList<InteractiveSpriteEntity> getEntities() {
        return entities;
    }

    public GameMaster getGameMaster() {
        return gameMaster;
    }

}
