package parohyapp.mario.sprites.lights.SignalLight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import parohyapp.mario.screens.PlayScreen;
import parohyapp.mario.sprites.Switchable;
import parohyapp.mario.sprites.lights.tools.*;
import parohyapp.mario.sprites.lights.tools.LightStatus;
import parohyapp.mario.sprites.parent.Entity;
import parohyapp.mario.sprites.standing.switches.SwitchableType;

/**
 * Created by tomas on 4/3/2016.
 */
public class SignalLight extends LightSource implements LightChangeListener {
    private final static String TAG = "SignalLight";

    private LightStatus status;
    private Switchable listener;


    public SignalLight(World world, Rectangle bounds, PlayScreen screen, LightStatus status) {
        this(world, bounds, screen);
        initLight(status);
    }

    public SignalLight(World world, Rectangle bounds, PlayScreen screen) {
        super(world, bounds, screen);

        initLight(LightStatus.NEUTRAL);
        setTag(SwitchableType.SIGNAL);
        setCategoryFilter(Entity.LSOURCE_BIT);
        fixture.setSensor(true);
    }

    @Override
    public void initLight(LightStatus status){
        this.status = status;

        if(getLightSource() == null){
            setLightSource(LightUtil.createPointLight(worldManager.getRayHandler(),20, Color.RED,50,b2Body));
        }

        switch(status){
            case LOCK:
                getLightSource().setColor(Color.RED);
                break;
            case OPEN:
                getLightSource().setColor(Color.GREEN);
                break;
            default:
                getLightSource().setColor(Color.YELLOW);
        }

        setLightMaskFilter((short) (Entity.DEFAULT_BIT | Entity.CLIMBER_BIT | Entity.CREEP_BIT));
    }

    @Override
    public LightStatus getLightStatus() {
        return status;
    }

}
