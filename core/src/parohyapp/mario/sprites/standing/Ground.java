package parohyapp.mario.sprites.standing;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import parohyapp.mario.sprites.parent.Entity;

/**
 * Created by tomas on 3/25/2016.
 */
public class Ground extends Entity{


    public Ground(World world, Rectangle bounds) {
        super(world, bounds);
    }
}
