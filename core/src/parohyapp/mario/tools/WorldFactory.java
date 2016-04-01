package parohyapp.mario.tools;

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
import parohyapp.mario.sprites.standing.Diamond;
import parohyapp.mario.sprites.standing.Ground;
import parohyapp.mario.sprites.animated.TestCreep;
import parohyapp.mario.sprites.parent.Entity;

/**
 * Created by tomas on 3/24/2016.
 */
public class WorldFactory {
    private static final String TAG = "WorldFactory";

    public final static String L_GROUND = "ground";
    public final static String L_PLAYER = "player";
    public final static String L_DIAMONDS = "diamonds";
    public final static String L_CREEP = "creep";
    public final static String L_MARK = "mark";



    public WorldFactory(World world, TiledMap map, PlayScreen screen){

        //ground
        for(MapObject object : map.getLayers().get(L_GROUND).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Ground(world,rect);
        }

        //diamonds
        for(MapObject object : map.getLayers().get(L_DIAMONDS).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            screen.getEntities().add(new Diamond(world,rect,screen));
            screen.getGameMaster().setNumberOfDiamonds(1);
        }

        //player
        for(MapObject object : map.getLayers().get(L_PLAYER).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            screen.setClimber(new Climber(world, rect, screen));
        }

        //creeps
        for(MapObject object : map.getLayers().get(L_CREEP).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            screen.getEntities().add(new TestCreep(world,rect,screen));
        }

        //marks
        for(MapObject object : map.getLayers().get(L_MARK).getObjects().getByType(RectangleMapObject.class)){
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
