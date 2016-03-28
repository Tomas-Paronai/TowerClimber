package parohyapp.mario.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import parohyapp.mario.TowerClimber;
import parohyapp.mario.screens.PlayScreen;
import parohyapp.mario.sprites.parent.*;

/**
 * Created by tomas on 3/24/2016.
 */
public class Climber extends InteractiveSpriteEntity implements parohyapp.mario.sprites.parent.HandleInput {
    private static final String TAG = "Climber";

    private Animation runningAnim;
    private float stateTime;
    private State currentState;
    private State previousState;
    private boolean facingRight;

    public Climber(World world, Rectangle bounds, PlayScreen screen) {
        super(world, bounds, screen);
        setCategoryFilter(CLIMBER_BIT);
    }


    @Override
    public void initTexture() {
        setRegion(screen.getGameMaster().getTextureByRegion("idle"));
        setBounds(0, 0, 32 / TowerClimber.PPM, 32 / TowerClimber.PPM);

        Array<TextureRegion> frames = new Array<TextureRegion>();
        TextureRegion in = screen.getGameMaster().getTextureByRegion("running");
        for(int i = 0; i < 6; i++){
            frames.add(new TextureRegion(in.getTexture(),i*32,0,32,32));
        }
        runningAnim = new Animation(0.15f,frames);
        frames.clear();
        currentState = previousState = State.IDLE;
        facingRight = true;
    }

    @Override
    public void update(float delta) {
        handleInput(delta);
        updateTexture(delta);
        super.update(delta);
    }

    private void updateTexture(float delta){
        TextureRegion tx = getFrame(delta);
        setRegion(tx);
    }

    @Override
    public void handleInput(float delta) {
        if(Gdx.input.isTouched() && currentState != State.FALLING && currentState != State.JUMPING){
            getB2Body().applyLinearImpulse(new Vector2(0,7f),getB2Body().getWorldCenter(),true);
            screen.getGameMaster().getAssetManager().get("audio/jump.wav", Sound.class).play();
        }

        if(getB2Body().getLinearVelocity().x <= Math.abs(2)){
            float speed = 0;
            if(Math.abs(Gdx.input.getAccelerometerX()) > 1){
                speed = Gdx.input.getAccelerometerX()*delta*(-1);
            }
            if(speed > 2){
                speed = 2f;
            }

            getB2Body().applyLinearImpulse(new Vector2(speed,0),getB2Body().getWorldCenter(),true);
        }
    }

    public TextureRegion getFrame(float delta){
        currentState = getState();
        TextureRegion tmpTexture;

        if(currentState == State.IDLE || currentState == State.FALLING){
           // Gdx.app.log(TAG,"I am idle.");
            tmpTexture = screen.getGameMaster().getTextureByRegion("idle");
        }
        else if(currentState == State.JUMPING){
            //Gdx.app.log(TAG, "I am jumping. ");
            tmpTexture = screen.getGameMaster().getTextureByRegion("jump");
        }
        else{
            //Gdx.app.log(TAG, "I am running. ");
            tmpTexture = runningAnim.getKeyFrame(stateTime,true);
        }

        if((b2Body.getLinearVelocity().x < 0 || !facingRight) && !tmpTexture.isFlipX()){
            tmpTexture.flip(true,false);
            facingRight = false;
        }
        else if((b2Body.getLinearVelocity().x > 0 || facingRight) && tmpTexture.isFlipX()){
            tmpTexture.flip(true,false);
            facingRight = true;
        }

        stateTime = currentState == previousState ? stateTime += delta : 0;
        previousState = currentState;

        if(tmpTexture == null){
            tmpTexture = screen.getGameMaster().getTextureByRegion("idle");
        }

        return tmpTexture;
    }

    public State getState(){
        if(b2Body.getLinearVelocity().y > 0){
            return State.JUMPING;
        }
        else if(b2Body.getLinearVelocity().y < 0){
            return  State.FALLING;
        }
        else if(b2Body.getLinearVelocity().x != 0){
            return State.RUNNING;
        }
        else{
            return State.IDLE;
        }
    }

    @Override
    public void onColide() {

    }
}
