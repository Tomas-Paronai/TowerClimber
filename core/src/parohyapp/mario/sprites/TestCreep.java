package parohyapp.mario.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import parohyapp.mario.screens.PlayScreen;

/**
 * Created by tomas on 3/30/2016.
 */
public class TestCreep extends Creep {

    public TestCreep(World world, Rectangle bounds, PlayScreen screen) {
        super(world, bounds, screen);
    }

    @Override
    public void initTexture() {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        TextureRegion in = screen.getGameMaster().getTextureByRegion32("running");
        for(int i = 0; i < 6; i++){
            frames.add(new TextureRegion(in.getTexture(),i*32,0,32,32));
        }
        setRunningAnim(new Animation(0.15f,frames),32,32);
    }

    @Override
    public void onColide() {
        super.onColide();
    }
}
