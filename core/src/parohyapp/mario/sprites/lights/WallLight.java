package parohyapp.mario.sprites.lights;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import parohyapp.mario.screens.PlayScreen;
import parohyapp.mario.sprites.parent.Entity;

/**
 * Created by tomas on 4/3/2016.
 */
public class WallLight extends parohyapp.mario.sprites.lights.tools.LightSource {

    public WallLight(World world, Rectangle bounds, PlayScreen screen) {
        super(world, bounds, screen);
        setLightSource(parohyapp.mario.sprites.lights.tools.LightSource.LightUtil.createPointLight(screen.getWorldManager().getRayHandler(),10,Color.WHITE,40,b2Body));
        setCategoryFilter(Entity.LSOURCE_BIT);
        setLightMaskFilter((short) (Entity.DEFAULT_BIT | Entity.CLIMBER_BIT | Entity.CREEP_BIT));
        fixture.setSensor(true);
    }
}
