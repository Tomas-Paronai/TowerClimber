package parohyapp.mario.sprites.lights.tools;

import parohyapp.mario.sprites.Switchable;

/**
 * Created by tomas on 4/3/2016.
 */
public interface LightChangeListener extends Switchable{
    public void changeLightStatus(LightStatus status);
    public LightStatus getLightStatus();
}
