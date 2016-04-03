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

import box2dLight.RayHandler;
import parohyapp.mario.GameMaster;
import parohyapp.mario.TowerClimber;
import parohyapp.mario.scenes.Hud;
import parohyapp.mario.sprites.Switchable;
import parohyapp.mario.sprites.animated.Climber;
import parohyapp.mario.sprites.lights.tools.LightSource;
import parohyapp.mario.sprites.parent.InteractiveSpriteEntity;
import parohyapp.mario.sprites.standing.Ground;
import parohyapp.mario.tools.WorldContactListener;
import parohyapp.mario.tools.WorldFactory;

/**
 * Created by tomas on 3/24/2016.
 */
public class PlayScreen implements Screen {
    private final static String TAG = "PlayScreen";

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
    private ArrayList<Ground> platforms;
    private ArrayList<LightSource> lights;

    //light
    private RayHandler rayHandler;

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

        hud.setName(GameMaster.getMapName(map));

        gameCam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2,0);

        //textures
        //textureAtlas = new TextureAtlas("climber.pack");


        //box2d variables & lights
        world = new World(new Vector2(0,-15),true);
        rayHandler = new RayHandler(world); //TODO
        b2dr = new Box2DDebugRenderer();

        entities = new ArrayList<InteractiveSpriteEntity>();
        platforms = new ArrayList<Ground>();
        lights = new ArrayList<LightSource>();

        new WorldFactory(world,map,this);

        rayHandler.setAmbientLight(1f);

        //collision detection setup
        world.setContactListener(new WorldContactListener());

        //postWorldSetup();
    }

    //private void postWorldSetup() {
    //    climber.createLight(rayHandler);
   // }


    @Override
    public void show() {

    }

    public void update(float delta){
        world.step(1 / 60f, 6, 2);
        rayHandler.update();

        gameCam.position.y = climber.getB2Body().getPosition().y + 3.5f;


        gameMaster.update(delta);
        updateEntities(delta);
        updatePlatforms(delta);

        hud.setWorldTimer(gameMaster.getTime());
        hud.setScore(gameMaster.getScore());
        hud.setLives(gameMaster.getLives());

        gameCam.update();
        mapRenderer.setView(gameCam);
    }

    private void updatePlatforms(float delta) {
        for(Ground tempPlat : platforms){
            if(tempPlat.getB2Body().getPosition().y + 0.4f < climber.getB2Body().getPosition().y){
                tempPlat.setGrip(true);
            }
            else{
                tempPlat.setGrip(false);
            }
        }
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

        //light
        rayHandler.setCombinedMatrix(gameCam.combined);
        rayHandler.render();

        //render HUD
        game.getBatch().setProjectionMatrix(hud.getStage().getCamera().combined);
        hud.getStage().draw();
    }

    private void renderEntities(){
        game.getBatch().begin();
        InteractiveSpriteEntity lastDraw = null;
        for(InteractiveSpriteEntity ent : entities){
            if(ent instanceof Climber){
                lastDraw = ent;
            }
            else{
                ent.draw(game.getBatch());
            }
        }
        if(lastDraw != null){
            lastDraw.draw(game.getBatch());
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
        rayHandler.dispose();
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

    public ArrayList<Ground> getPlatforms() {
        return platforms;
    }

    public ArrayList<LightSource> getLights() {
        return lights;
    }

    public ArrayList<Switchable> getAllSwitchables(){
        ArrayList<Switchable> switchables = new ArrayList<Switchable>();
        for(InteractiveSpriteEntity ent : entities){
            if(ent instanceof Switchable){
                switchables.add((Switchable) ent);
            }
        }
        return switchables;
    }

    public GameMaster getGameMaster() {
        return gameMaster;
    }

    public RayHandler getRayHandler() {
        return rayHandler;
    }
}
