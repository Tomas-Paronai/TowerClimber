package parohyapp.mario.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import parohyapp.mario.screens.PlayScreen;
import parohyapp.mario.sprites.Climber;
import parohyapp.mario.sprites.Diamond;
import parohyapp.mario.sprites.Ground;

/**
 * Created by tomas on 3/24/2016.
 */
public class WorldFactory {
    private static final String TAG = "WorldFactory";

    public final static int L_GROUND = 2;
    public final static int L_DIAMONDS = 4;
    public final static int L_PLAYER = 3;

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
    }
}
