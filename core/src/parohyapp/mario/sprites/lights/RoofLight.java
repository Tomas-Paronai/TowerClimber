package parohyapp.mario.sprites.lights;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import parohyapp.mario.screens.PlayScreen;
import parohyapp.mario.sprites.lights.tools.LightSource;
import parohyapp.mario.sprites.parent.Entity;
import parohyapp.mario.sprites.standing.switches.SwitchableType;

/**
 * Created by tomas on 4/3/2016.
 */
public class RoofLight extends LightSource {

    public RoofLight(World world, Rectangle bounds, PlayScreen screen) {
        super(world, bounds, screen);
        setLightSource(parohyapp.mario.sprites.lights.tools.LightSource.LightUtil.createConeLight(screen.getWorldManager().getRayHandler(), 30, Color.WHITE, 350, -90, 20, b2Body));
        setCategoryFilter(Entity.LSOURCE_BIT);
        setLightMaskFilter((short) (Entity.DEFAULT_BIT | Entity.CLIMBER_BIT | Entity.CREEP_BIT));
        fixture.setSensor(true);
    }

    public RoofLight(World world, Rectangle bounds, PlayScreen screen, float angle) {
        this(world, bounds, screen);
        getLightSource().remove();
        setLightSource(parohyapp.mario.sprites.lights.tools.LightSource.LightUtil.createConeLight(screen.getWorldManager().getRayHandler(), 30, Color.WHITE, 350, angle, 20, b2Body));
    }
}
