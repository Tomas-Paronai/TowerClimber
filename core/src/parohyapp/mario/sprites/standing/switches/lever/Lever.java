package parohyapp.mario.sprites.standing.switches.lever;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

import parohyapp.mario.screens.PlayScreen;
import parohyapp.mario.sprites.standing.switches.Switch;

/**
 * Created by tomas on 4/3/2016.
 */
public class Lever extends Switch {

    public Lever(World world, Rectangle bounds, PlayScreen screen) {
        super(world, bounds, screen);
    }

    @Override
    public void initConnection() {

    }

    @Override
    public void initTexture() {

    }

    @Override
    public void onColideEnd() {

    }
}
