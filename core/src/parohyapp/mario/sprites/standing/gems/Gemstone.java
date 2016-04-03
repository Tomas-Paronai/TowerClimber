package parohyapp.mario.sprites.standing.gems;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import box2dLight.PointLight;
import parohyapp.mario.TowerClimber;
import parohyapp.mario.screens.PlayScreen;
import parohyapp.mario.sprites.lights.tools.LightSource;
import parohyapp.mario.sprites.parent.Entity;
import parohyapp.mario.sprites.parent.InteractiveSpriteEntity;
import parohyapp.mario.tools.Resources;

/**
 * Created by tomas on 3/25/2016.
 */
public abstract class Gemstone extends InteractiveSpriteEntity {
    private static final String TAG = "Gemstone";
    
    private int value;
    private PointLight light;
    private boolean lightSet;

    public Gemstone(World world, Rectangle bounds, PlayScreen screen, int value) {
        super(world, bounds, screen);
        this.value = value;
        setCategoryFilter(Entity.GEMSTONE_BIT);
        fixture.setSensor(true);
        createLight();
    }


    @Override
    public void onColide() {
        if(isVissible()){
            setCategoryFilter(Entity.DISPOSE_BIT);
            setVissible(false);
            screen.getGameMaster().setScore(value);
            screen.getGameMaster().getAssetManager().get(Resources.A_SCORE.toString(), Sound.class).play();
            if(light != null){
                light.remove();
            }
        }
    }


    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void onColideEnd() {

    }

    @Override
    public void initTexture() {
        setBounds(0, 0, 16 / TowerClimber.PPM, 16 / TowerClimber.PPM);
    }


    public void createLight(){
        light = (PointLight) LightSource.LightUtil.createPointLight(screen.getRayHandler(), 30, Color.CYAN, 40,b2Body);
        light.setXray(true);
    }
}
