package parohyapp.mario.sprites.animated;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import parohyapp.mario.TowerClimber;
import parohyapp.mario.screens.PlayScreen;
import parohyapp.mario.tools.Resources;
import parohyapp.mario.tools.ResourcesUtil;

/**
 * Created by tomas on 3/30/2016.
 */
public class TestCreep extends Creep {

    private Animation runningAnimation;
    private Animation idleAnimation;
    private Animation dieAnimation;


    public TestCreep(World world, Rectangle bounds, PlayScreen screen) {
        super(world, bounds, screen);
    }

    @Override
    public void initTexture() {
        setBounds(0, 0, 32 / TowerClimber.PPM, 32 / TowerClimber.PPM);
        Array<TextureRegion> frames = ResourcesUtil.getFramesArray(screen.getGameMaster().getAssetManager().get(Resources.ROBOT_GO.toString(),Texture.class), 32);
        runningAnimation = new Animation(0.15f,frames);
        frames.clear();

        frames = ResourcesUtil.getFramesArray(screen.getGameMaster().getAssetManager().get(Resources.ROBOT_IDLE.toString(), Texture.class), 32);
        idleAnimation = new Animation(0.15f,frames);
        frames.clear();

        frames = ResourcesUtil.getFramesArray(screen.getGameMaster().getAssetManager().get(Resources.ROBOT_DIE.toString(), Texture.class), 32);
        dieAnimation = new Animation(0.15f,frames);
        frames.clear();
    }

    @Override
    public void getFrame(float delta) {
        //TODO creep animation
        super.getFrame(delta);
        TextureRegion tmpTexture;

        if(currentState == State.IDLE || currentState == State.FALLING){
            //Gdx.app.log(TAG,"I am idle.");
            tmpTexture = idleAnimation.getKeyFrame(stateTime,true);
        }
        else if(currentState == State.JUMPING){
            //Gdx.app.log(TAG, "I am jumping. ");
            tmpTexture = idleAnimation.getKeyFrame(stateTime,true);
        }
        else if(currentState == State.DYING){
            tmpTexture = dieAnimation.getKeyFrame(stateTime,false);
        }
        else{
            //Gdx.app.log(TAG, "I am running. ");
            tmpTexture = runningAnimation.getKeyFrame(stateTime,true);
        }
        setRegion(tmpTexture);

        /*if((b2Body.getLinearVelocity().x < 0 || !facingRight) && !tmpTexture.isFlipX()){
            tmpTexture = runningAnimation.getKeyFrame(stateTime,true);
            tmpTexture.flip(true,false);
            facingRight = false;
        }
        else if((b2Body.getLinearVelocity().x > 0 || facingRight) && tmpTexture.isFlipX()){
            tmpTexture.flip(true,false);
            facingRight = true;
        }
        flippingFrame(tmpTexture);
        setRegion(tmpTexture);*/
    }

    @Override
    public void act(float delta) {
        if(currentState == State.DYING && dieAnimation.isAnimationFinished(stateTime)){
            setVissible(false);
            fixture.setSensor(true);
        }
    }
}
