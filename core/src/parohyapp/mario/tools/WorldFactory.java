package parohyapp.mario.tools;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import parohyapp.mario.TowerClimber;
import parohyapp.mario.screens.PlayScreen;
import parohyapp.mario.sprites.animated.Climber;
import parohyapp.mario.sprites.lights.RoofLight;
import parohyapp.mario.sprites.lights.SignalLight;
import parohyapp.mario.sprites.lights.WallLight;
import parohyapp.mario.sprites.standing.door.Door;
import parohyapp.mario.sprites.standing.door.DoorZ;
import parohyapp.mario.sprites.standing.gems.Diamond;
import parohyapp.mario.sprites.standing.Ground;
import parohyapp.mario.sprites.animated.TestCreep;
import parohyapp.mario.sprites.parent.Entity;
import parohyapp.mario.sprites.standing.gems.Ruby;
import parohyapp.mario.sprites.standing.misc.NonInteractiveEntity;
import parohyapp.mario.sprites.standing.switches.lever.Lever;

/**
 * Created by tomas on 3/24/2016.
 */
public class WorldFactory {
    private static final String TAG = "WorldFactory";

    public final static String L_GROUND = "ground";
    public final static String L_PLAYER = "player";
    public final static String L_DIAMONDS = "diamonds";
    public final static String L_RUBY = "ruby";
    public final static String L_CREEP = "creep";
    public final static String L_MARK = "mark";
    public final static String L_DOOR= "door";
    public final static String L_LIGHT = "light";
    public final static String L_SWITCH = "switch";
    public final static String L_MISC = "misc";



    public WorldFactory(WorldManager worldManager, World world, TiledMap map, PlayScreen screen){

        //ground
        if(map.getLayers().get(L_GROUND) != null){
            MapLayer layer = map.getLayers().get(L_GROUND);
            for(MapObject object : layer.getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                if (object.getProperties().containsKey("platform")) {
                    worldManager.getPlatforms().add(new Ground(world, rect, screen, true));
                } else {
                    new Ground(world, rect, screen);
                }
            }
        }

        //gems
        if(map.getLayers().get(L_DIAMONDS) != null){
            MapLayer layer = map.getLayers().get(L_DIAMONDS);
            for(MapObject object : layer.getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                worldManager.getEntities().add(new Diamond(world, rect, screen));
                screen.getGameMaster().setNumberOfGemstones(1);
            }
        }

        if(map.getLayers().get(L_RUBY) != null){
            MapLayer layer = map.getLayers().get(L_RUBY);
            for(MapObject object : layer.getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                worldManager.getEntities().add(new Ruby(world, rect, screen));
            }
        }

        //player
        if(map.getLayers().get(L_PLAYER) != null){
            MapLayer layer = map.getLayers().get(L_PLAYER);
            for(MapObject object : layer.getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                worldManager.setClimber(new Climber(world, rect, screen));
                break;
            }
        }

        //doors
        if(map.getLayers().get(L_DOOR) != null){
            MapLayer layer = map.getLayers().get(L_DOOR);
            for(MapObject object : layer.getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                Door door = new Door(world, rect, screen, DoorZ.FRONT);
                if(object.getProperties().containsKey("doorz")){
                    String property = (String) object.getProperties().get("doorz");
                    if(property.equals("back")){
                        door = new Door(world, rect, screen, DoorZ.BACK);
                    }
                    else if(property.equals("bg")){
                        door = new Door(world, rect, screen, DoorZ.BG);
                    }
                }

                if(object.getProperties().containsKey("exit")){
                    door.setExit(true);
                }
                if(object.getProperties().containsKey("lock")){
                    door.setOpen(false);
                }

                worldManager.getEntities().add(door);
            }
        }

        //creeps
        if(map.getLayers().get(L_CREEP) != null){
            MapLayer layer = map.getLayers().get(L_CREEP);
            for(MapObject object : layer.getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                worldManager.getEntities().add(new TestCreep(world,rect,screen));
            }
        }


        //marks
        if(map.getLayers().get(L_MARK) != null){
            MapLayer layer = map.getLayers().get(L_MARK);
            for(MapObject object : layer.getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                BodyDef bDef = new BodyDef();
                FixtureDef fixDef = new FixtureDef();
                PolygonShape pShape = new PolygonShape();
                Fixture fixture;
                Body body;

                fixDef.filter.categoryBits = Entity.MARK_BIT;
                fixDef.filter.maskBits = (Entity.CREEP_BIT);
                bDef.type = BodyDef.BodyType.StaticBody;
                bDef.position.set((rect.getX() + rect.getWidth() / 2) / TowerClimber.PPM, (rect.getY() + rect.getHeight() / 2) / TowerClimber.PPM);
                body = world.createBody(bDef);

                pShape.setAsBox(rect.getWidth() / 2 / TowerClimber.PPM, rect.getHeight() / 2 / TowerClimber.PPM);
                fixDef.shape = pShape;
                fixture = body.createFixture(fixDef);
                fixture.setUserData("mark");
            }
        }

        //lights
        if(map.getLayers().get(L_LIGHT) != null){
            MapLayer layer = map.getLayers().get(L_LIGHT);
            for(MapObject object : layer.getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                if(object.getProperties().containsKey("type")){
                    String type = (String) object.getProperties().get("type");
                    if(type.equals("roof")){
                        worldManager.getLights().add(new RoofLight(world,rect,screen));
                    }
                    else if (type.equals("signal")){
                        worldManager.getLights().add(new SignalLight(world,rect,screen));
                    }

                }
                else{
                    worldManager.getLights().add(new WallLight(world,rect,screen));
                }

            }
        }

        //switches
        if(map.getLayers().get(L_SWITCH) != null){
            MapLayer layer = map.getLayers().get(L_SWITCH);
            for(MapObject object : layer.getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                new Lever(world,rect,screen);
            }
        }

        //misc
        if(map.getLayers().get(L_MISC) != null){
            MapLayer layer = map.getLayers().get(L_MISC);
            for(MapObject object : layer.getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                new NonInteractiveEntity(world,rect);
            }
        }



    }
}
