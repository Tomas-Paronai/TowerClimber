package parohyapp.mario.sprites.standing.switches.lever;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import parohyapp.mario.TowerClimber;
import parohyapp.mario.screens.PlayScreen;
import parohyapp.mario.sprites.standing.switches.Switch;
import parohyapp.mario.tools.data.resources.Resources;
import parohyapp.mario.tools.data.resources.ResourcesUtil;

/**
 * Created by tomas on 4/3/2016.
 */
public class Lever extends Switch {

    public Lever(World world, Rectangle bounds, PlayScreen screen) {
        super(world, bounds, screen);
    }

    @Override
    public void initTexture() {
        setBounds(0, 0, 16 / TowerClimber.PPM, 16 / TowerClimber.PPM);

        Array<TextureRegion> frames = ResourcesUtil.getFramesArray(screen.getGameMaster().getAssetManager().get(Resources.LEVER.toString(), Texture.class),16);
        toggleAnimation = new Animation(0.15f,frames);
        setRegion(toggleAnimation.getKeyFrames()[0]);
    }

    @Override
    public void update(float delta) {
        if(toggle){
            getFrame(delta);
        }
    }

    @Override
    public void getFrame(float delta) {
        super.getFrame(delta);

        if(toggle && !toggleAnimation.isAnimationFinished(stateTime)){
            setRegion(toggleAnimation.getKeyFrame(stateTime));
        }
    }

    @Override
    public void onColideEnd() {

    }
}
