package parohyapp.mario.tools;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;

import box2dLight.RayHandler;
import parohyapp.mario.GameMaster;
import parohyapp.mario.TowerClimber;
import parohyapp.mario.screens.PlayScreen;
import parohyapp.mario.sprites.Switchable;
import parohyapp.mario.sprites.animated.Climber;
import parohyapp.mario.sprites.lights.tools.LightSource;
import parohyapp.mario.sprites.parent.InteractiveSpriteEntity;
import parohyapp.mario.sprites.standing.Ground;

/**
 * Created by tomas on 4/3/2016.
 */
public class WorldManager implements Disposable{

    private GameMaster gameMaster;
    private PlayScreen screen;
    private TowerClimber game;

    //box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private Climber climber;
    private ArrayList<InteractiveSpriteEntity> entities;
    private ArrayList<Ground> platforms;
    private ArrayList<LightSource> lights;
    private TiledMap map;
    private RayHandler rayHandler;

    public WorldManager(TowerClimber game, GameMaster gameMaster, PlayScreen screen){
        this.game = game;
        this.gameMaster = gameMaster;
        this.screen = screen;
    }

    public void loadWorld() {
        map = gameMaster.getLevelMap();

        world = new World(new Vector2(0,-15),true);

        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(1f);

        b2dr = new Box2DDebugRenderer();

        entities = new ArrayList<InteractiveSpriteEntity>();
        platforms = new ArrayList<Ground>();
        lights = new ArrayList<LightSource>();

        new WorldFactory(this,world,map,screen);

        world.setContactListener(new WorldContactListener(this));
    }

    public TiledMap getMap(){
        return map;
    }

    public String getMapName(){
        if(map.getProperties().containsKey("Name")){
            return (String) map.getProperties().get("Name");
        }
        return "unknown";
    }

    public void update(float delta){
        world.step(1 / 60f, 6, 2);
        rayHandler.update();

        updateEntities(delta);
        updatePlatforms(delta);
    }

    private void updateEntities(float delta){
        for(InteractiveSpriteEntity ent : entities){
            ent.update(delta);
        }
    }

    private void updatePlatforms(float delta) {
        for(Ground tempPlat : platforms){
            if(tempPlat.getB2Body().getPosition().y < climber.getB2Body().getPosition().y){
                tempPlat.setGrip(true);
            }
            else{
                tempPlat.setGrip(false);
            }
        }
    }

    public void render(SpriteBatch batch, OrthographicCamera camera){
        b2dr.render(world, camera.combined);

        game.getBatch().setProjectionMatrix(camera.combined);
        renderEntities();

        rayHandler.setCombinedMatrix(camera.combined);
        rayHandler.render();
    }

    private void renderEntities(){
        game.getBatch().begin();
        InteractiveSpriteEntity lastDraw = null;
        for(InteractiveSpriteEntity ent : entities){
            if(ent.getTexture() != null){
                if(ent instanceof Climber){
                    lastDraw = ent;
                }
                else{
                    ent.draw(game.getBatch());
                }
            }
        }
        if(lastDraw != null){
            lastDraw.draw(game.getBatch());
        }
        game.getBatch().end();
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

        switchables.addAll(lights);

        return switchables;
    }

    public RayHandler getRayHandler() {
        return rayHandler;
    }

    @Override
    public void dispose() {
        map.dispose();
        world.dispose();
        b2dr.dispose();
        rayHandler.dispose();
    }
}
