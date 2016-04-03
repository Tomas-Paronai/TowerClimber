package parohyapp.mario.sprites.lights.tools;

/**
 * Created by tomas on 4/3/2016.
 */
public interface LightChangeListener {
    public void changeLightStatus(LightStatus status);
    public LightStatus getLightStatus();
}
