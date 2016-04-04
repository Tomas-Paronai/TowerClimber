package parohyapp.mario.sprites.standing.misc;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import parohyapp.mario.sprites.parent.Entity;

/**
 * Created by tomas on 4/3/2016.
 */
public class NonInteractiveEntity extends Entity{

    public NonInteractiveEntity(World world, Rectangle bounds) {
        super(world, bounds,null);
        fixture.setSensor(true);
    }
}
