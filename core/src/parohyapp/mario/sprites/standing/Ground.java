package parohyapp.mario.sprites.standing;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import parohyapp.mario.screens.PlayScreen;
import parohyapp.mario.sprites.parent.Entity;
import parohyapp.mario.sprites.parent.InteractiveSpriteEntity;

/**
 * Created by tomas on 3/25/2016.
 */
public class Ground extends InteractiveSpriteEntity{

    private boolean platform;

    public Ground(World world, Rectangle bounds, PlayScreen screen) {
        super(world, bounds, screen);
    }

    public Ground(World world, Rectangle bounds, PlayScreen screen, boolean platform) {
        super(world, bounds, screen);
        this.platform = platform;
        if(platform){
            fixture.setSensor(true);
        }
    }


    @Override
    public void initTexture() {

    }

    @Override
    public void onColide() {

    }

    @Override
    public void onColideEnd() {
    }


    public void setGrip(boolean grip){
        if(grip){
            fixture.setSensor(false);
        }
        else{
            fixture.setSensor(true);
        }
    }

    public boolean isPlatform() {
        return platform;
    }


}
