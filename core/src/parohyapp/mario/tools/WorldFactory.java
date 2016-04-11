package parohyapp.mario.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

import box2dLight.ConeLight;
import parohyapp.mario.TowerClimber;
import parohyapp.mario.screens.PlayScreen;
import parohyapp.mario.sprites.animated.Climber;
import parohyapp.mario.sprites.lights.SignalLight.OneWaySignalLight;
import parohyapp.mario.sprites.lights.RoofLight;
import parohyapp.mario.sprites.lights.SignalLight.SignalLight;
import parohyapp.mario.sprites.lights.WallLight;
import parohyapp.mario.sprites.lights.tools.LightSource;
import parohyapp.mario.sprites.lights.tools.LightStatus;
import parohyapp.mario.sprites.standing.door.Door;
import parohyapp.mario.sprites.standing.door.DoorZ;
import parohyapp.mario.sprites.standing.gems.Diamond;
import parohyapp.mario.sprites.standing.Ground;
import parohyapp.mario.sprites.animated.TestCreep;
import parohyapp.mario.sprites.parent.Entity;
import parohyapp.mario.sprites.standing.gems.Ruby;
import parohyapp.mario.sprites.standing.misc.NonInteractiveEntity;
import parohyapp.mario.sprites.standing.switches.Switch;
import parohyapp.mario.sprites.standing.switches.SwitchableType;
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

    private WorldManager worldManager;
    private World world;
    private TiledMap map;
    private PlayScreen screen;

    public WorldFactory(WorldManager worldManager, World world, TiledMap map, PlayScreen screen){
        this.worldManager = worldManager;
        this.world = world;
        this.map = map;
        this.screen = screen;

        createGround();
        createPlayer();
        createGems();
        createCreeps();
        createDoors();
        createLights();
        createSwitches();
        createMisc();

        //ground
        /*if(map.getLayers().get(L_GROUND) != null){
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

                Door door;
                if(object.getProperties().containsKey("doorz")){
                    String property = (String) object.getProperties().get("doorz");
                    if(property.equals("back")){
                        door = new Door(world, rect, screen, DoorZ.BACK);
                    }
                    else if(property.equals("bg")){
                        door = new Door(world, rect, screen, DoorZ.BG);
                    }
                    else{
                        door = new Door(world, rect, screen, DoorZ.FRONT);
                    }
                }
                else{
                    door = new Door(world, rect, screen, DoorZ.FRONT);
                }

                if(object.getProperties().containsKey("exit")){
                    door.setExit(true);
                }
                if(object.getProperties().containsKey("lock")){
                    if(object.getProperties().get("lock").equals("false")){
                        door.setOpen(true);
                    }
                    else{
                        door.setOpen(false);
                    }
                }

                if(object.getProperties().containsKey("connect")){
                    door.setConnectable(true);
                }

                Gdx.app.log(TAG,"DOOR: Conn:"+door.isConnectable()+" Exit:"+door.isExit()+" Open:"+door.isOpen());
                worldManager.getEntities().add(door);
            }
        }

        //creeps
        if(map.getLayers().get(L_CREEP) != null){
            MapLayer layer = map.getLayers().get(L_CREEP);
            for(MapObject object : layer.getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                worldManager.getEntities().add(new TestCreep(world, rect, screen));
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

                LightSource light;
                if(object.getProperties().containsKey("type")){
                    String type = (String) object.getProperties().get("type");

                    if(type.equals("roof")){
                        if(object.getProperties().containsKey("angle")){
                            light = new RoofLight(world,rect,screen, Integer.parseInt((String) object.getProperties().get("angle")) );
                        }
                        else{
                            light = new RoofLight(world,rect,screen);
                        }

                        if(object.getProperties().containsKey("power")){
                            light.getLightSource().setDistance(Integer.parseInt((String) object.getProperties().get("power")));
                        }

                        if(object.getProperties().containsKey("dirangle")){
                            ((ConeLight)light.getLightSource()).setConeDegree( Float.parseFloat((String) object.getProperties().get("dirangle")) );
                        }
                    }

                    else if (type.equals("signal")){


                        if(object.getProperties().containsKey("lock")){
                            Gdx.app.log(TAG,"Creating signal light with status");
                            if(object.getProperties().get("lock").equals("false")){
                                light = new OneWaySignalLight(world,rect,screen,LightStatus.OPEN);
                            }
                            else{
                                light = new OneWaySignalLight(world,rect,screen,LightStatus.LOCK);
                            }
                        }
                        else{
                            Gdx.app.log(TAG,"Creating signal light");
                            light = new SignalLight(world,rect,screen);
                        }
                    }

                    else{
                        light = new WallLight(world,rect,screen);
                    }

                }

                else{
                    light = new WallLight(world,rect,screen);
                }

                if(object.getProperties().containsKey("connect")){
                    light.setConnectable(true);
                }

                worldManager.getLights().add(light);
            }
        }

        //switches
        if(map.getLayers().get(L_SWITCH) != null){
            MapLayer layer = map.getLayers().get(L_SWITCH);
            for(MapObject object : layer.getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                Switch tmpSwitch = null;
                MapProperties objProperties = object.getProperties();
                if(objProperties.containsKey("type")){
                    String type = (String) objProperties.get("type");
                    if(type.equals("lever")){
                        tmpSwitch = new Lever(world,rect,screen);
                    }
                }
                else{
                    tmpSwitch = new Lever(world,rect,screen);
                }

                connectDevices(object, tmpSwitch);

                screen.getWorldManager().getEntities().add(tmpSwitch);

            }
        }

        //misc
        if(map.getLayers().get(L_MISC) != null){
            MapLayer layer = map.getLayers().get(L_MISC);
            for(MapObject object : layer.getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                new NonInteractiveEntity(world,rect);
            }
        }*/



    }

    private void createGround(){
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
    }

    private void createPlayer(){
        if(map.getLayers().get(L_PLAYER) != null){
            MapLayer layer = map.getLayers().get(L_PLAYER);
            for(MapObject object : layer.getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                worldManager.setClimber(new Climber(world, rect, screen));
                break;
            }
        }
    }

    private void createGems(){
        //diamonds
        if(map.getLayers().get(L_DIAMONDS) != null){
            MapLayer layer = map.getLayers().get(L_DIAMONDS);
            for(MapObject object : layer.getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                worldManager.getEntities().add(new Diamond(world, rect, screen));
                screen.getGameMaster().setNumberOfGemstones(1);
            }
        }
        //ruby
        if(map.getLayers().get(L_RUBY) != null){
            MapLayer layer = map.getLayers().get(L_RUBY);
            for(MapObject object : layer.getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                worldManager.getEntities().add(new Ruby(world, rect, screen));
            }
        }
    }

    private void createCreeps(){
        if(map.getLayers().get(L_CREEP) != null){
            MapLayer layer = map.getLayers().get(L_CREEP);
            for(MapObject object : layer.getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                worldManager.getEntities().add(new TestCreep(world, rect, screen));
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
    }
    private void createDoors(){
        if(map.getLayers().get(L_DOOR) != null){
            MapLayer layer = map.getLayers().get(L_DOOR);
            for(MapObject object : layer.getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                Door door;
                if(object.getProperties().containsKey("doorz")){
                    String property = (String) object.getProperties().get("doorz");

                    //proper door texture selection
                    if(property.equals("back")){
                        door = new Door(world, rect, screen, DoorZ.BACK);
                    }
                    else if(property.equals("bg")){
                        door = new Door(world, rect, screen, DoorZ.BG);
                    }
                    else{
                        door = new Door(world, rect, screen, DoorZ.FRONT);
                    }

                }
                else{
                    door = new Door(world, rect, screen, DoorZ.FRONT);
                }

                //set exit door
                if(object.getProperties().containsKey("exit")){
                    door.setExit(true);
                }

                //door locked or unlocked
                if(object.getProperties().containsKey("lock")){
                    if(object.getProperties().get("lock").equals("false")){
                        door.setOpen(true);
                    }
                    else{
                        door.setOpen(false);
                    }
                }

                //if door can be connected to switch
                if(object.getProperties().containsKey("connect")){
                    door.setConnectable(true);
                }

                worldManager.getEntities().add(door);
            }
        }
    }

    private void createLights(){
        if(map.getLayers().get(L_LIGHT) != null){
            MapLayer layer = map.getLayers().get(L_LIGHT);
            for(MapObject object : layer.getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                LightSource light;
                if(object.getProperties().containsKey("type")){
                    String type = (String) object.getProperties().get("type");

                    //roof light
                    if(type.equals("roof")){
                        //angle of pointing
                        if(object.getProperties().containsKey("angle")){
                            light = new RoofLight(world,rect,screen, Integer.parseInt((String) object.getProperties().get("angle")) );
                        }
                        else{
                            light = new RoofLight(world,rect,screen);
                        }

                        //distance of light rays
                        if(object.getProperties().containsKey("power")){
                            light.getLightSource().setDistance(Integer.parseInt((String) object.getProperties().get("power")));
                        }

                        //angle of light
                        if(object.getProperties().containsKey("dirangle")){
                            ((ConeLight)light.getLightSource()).setConeDegree( Float.parseFloat((String) object.getProperties().get("dirangle")) );
                        }
                    }

                    //signal light
                    else if (type.equals("signal")){

                        //light status: green-open, red-locked, yellow-neutral
                        if(object.getProperties().containsKey("lock")){
                            if(object.getProperties().get("lock").equals("false")){
                                light = new OneWaySignalLight(world,rect,screen,LightStatus.OPEN);
                            }
                            else{
                                light = new OneWaySignalLight(world,rect,screen,LightStatus.LOCK);
                            }
                        }
                        else{
                            light = new SignalLight(world,rect,screen);
                        }
                    }

                    //default wall light
                    else{
                        light = new WallLight(world,rect,screen);
                    }

                }

                else{
                    light = new WallLight(world,rect,screen);
                }

                //check light connectivity
                if(object.getProperties().containsKey("connect")){
                    light.setConnectable(true);
                }

                worldManager.getLights().add(light);
            }
        }
    }

    private void createSwitches(){
        if(map.getLayers().get(L_SWITCH) != null){
            MapLayer layer = map.getLayers().get(L_SWITCH);
            for(MapObject object : layer.getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                Switch tmpSwitch = null;
                MapProperties objProperties = object.getProperties();
                if(objProperties.containsKey("type")){
                    String type = (String) objProperties.get("type");
                    if(type.equals("lever")){
                        tmpSwitch = new Lever(world,rect,screen);
                    }
                }
                else{
                    tmpSwitch = new Lever(world,rect,screen);
                }

                //connect listed devices as properties to this switch
                connectDevices(object, tmpSwitch);

                screen.getWorldManager().getEntities().add(tmpSwitch);

            }
        }
    }

    private void createMisc(){
        if(map.getLayers().get(L_MISC) != null){
            MapLayer layer = map.getLayers().get(L_MISC);
            for(MapObject object : layer.getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                new NonInteractiveEntity(world,rect);
            }
        }
    }

    private void connectDevices(MapObject object, Switch tmpSwitch) {
        int index = 1;
        String identifier = "conn"+index++;
        ArrayList<SwitchableType> types = new ArrayList<SwitchableType>();
        while(object.getProperties().containsKey(identifier)){
            String value = (String) object.getProperties().get(identifier);

            if(value.equals("signal")){
                types.add(SwitchableType.SIGNAL);
            }
            else if(value.equals("door")){
                types.add(SwitchableType.DOOR);
            }
            else if(value.equals("light")){
                types.add(SwitchableType.LIGHT);
            }

            identifier = "conn"+index++;
        }

        tmpSwitch.initConnection(types);
    }
}
