package parohyapp.mario.sprites.standing.switches;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import parohyapp.mario.screens.PlayScreen;
import parohyapp.mario.sprites.Switchable;
import parohyapp.mario.sprites.lights.tools.LightChangeListener;
import parohyapp.mario.sprites.parent.InteractiveSpriteEntity;

/**
 * Created by tomas on 4/3/2016.
 */
public abstract class Switch extends InteractiveSpriteEntity {

    private boolean toggle;
    private Switchable lightListener;
    private LightChangeListener lightChangeListener;

    public Switch(World world, Rectangle bounds, PlayScreen screen) {
        super(world, bounds, screen);
        initConnection();
    }

    public abstract void initConnection();

    public void toggleSwitch(){
        toggle = isToggle() ? false : true;
    }

    public boolean isToggle() {
        return toggle;
    }

    public void setToggle(boolean toggle) {
        this.toggle = toggle;
    }

    @Override
    public void onColide() {
        if(lightListener != null){
            lightListener.toggle();

            if(lightChangeListener != null){

            }
        }
    }

    public void setLightListener(Switchable lightListener) {
        this.lightListener = lightListener;
    }

    public void setLightChangeListener(LightChangeListener lightChangeListener) {
        this.lightChangeListener = lightChangeListener;
    }
}
