package parohyapp.mario.sprites.lights.SignalLight;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import parohyapp.mario.screens.PlayScreen;
import parohyapp.mario.sprites.lights.tools.LightStatus;

/**
 * Created by tomas on 4/4/2016.
 */
public class OneWaySignalLight extends SignalLight {

    public OneWaySignalLight(World world, Rectangle bounds, PlayScreen screen, LightStatus status) {
        super(world, bounds, screen, status);
    }

    public OneWaySignalLight(World world, Rectangle bounds, PlayScreen screen) {
        super(world, bounds, screen);
    }

    @Override
    public void toggle() {
        super.toggle();
    }
}
