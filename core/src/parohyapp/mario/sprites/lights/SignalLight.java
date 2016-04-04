package parohyapp.mario.sprites.lights;

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

    public static int amount;
    private int id;
    private LightStatus status;
    private Switchable listener;


    public SignalLight(World world, Rectangle bounds, PlayScreen screen) {
        super(world, bounds, screen);

        setCategoryFilter(Entity.LSOURCE_BIT);
        fixture.setSensor(true);

        id = SignalLight.amount++;
    }

    public void initLight(LightStatus status){
        this.status = status;
        switch(status){
            case LOCK:
                setLightSource(LightUtil.createPointLight(screen.getWorldManager().getRayHandler(),20, Color.RED,30,b2Body));
                break;
            case OPEN:
                setLightSource(LightUtil.createPointLight(screen.getWorldManager().getRayHandler(),20, Color.GREEN,30,b2Body));
                break;
        }

        setLightMaskFilter((short) (Entity.DEFAULT_BIT | Entity.CLIMBER_BIT | Entity.CREEP_BIT));
    }

    public void setStatus(LightStatus status) {
        this.status = status;
        initLight(status);
    }

    public int getId() {
        return id;
    }

    @Override
    public void changeLightStatus(LightStatus status) {

        if(status == LightStatus.DOOR){
            if(getLightStatus() == LightStatus.LOCK){
                initLight(LightStatus.OPEN);
            }
            else{
                initLight(LightStatus.LOCK);
            }
            return;
        }

        initLight(status);
    }

    @Override
    public LightStatus getLightStatus() {
        return status;
    }

    @Override
    public SwitchableType getTag() {
        return SwitchableType.SIGNAL;
    }
}
