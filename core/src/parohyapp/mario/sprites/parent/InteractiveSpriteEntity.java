package parohyapp.mario.sprites.parent;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import parohyapp.mario.screens.PlayScreen;
import parohyapp.mario.sprites.State;

/**
 * Created by tomas on 3/24/2016.
 */
public abstract class InteractiveSpriteEntity extends Entity{

    protected PlayScreen screen;
    private boolean vissible;

    protected float stateTime;
    protected State currentState;
    protected State previousState;
    protected boolean facingRight;

    public InteractiveSpriteEntity(World world, Rectangle bounds, PlayScreen screen){
        super(world,bounds);
        this.screen = screen;
        initTexture();
        setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
        vissible = true;
    }

    public abstract void initTexture();
    public abstract void onColide();

    public void getFrame(float delta){
        currentState = getState();
        stateTime = currentState == previousState ? stateTime += delta : 0;
        previousState = currentState;
    }

    public void flippingFrame(TextureRegion tmpTexture){
        if((b2Body.getLinearVelocity().x < 0 || !facingRight) && !tmpTexture.isFlipX()){
            tmpTexture.flip(true,false);
            facingRight = false;
        }
        else if((b2Body.getLinearVelocity().x > 0 || facingRight) && tmpTexture.isFlipX()){
            tmpTexture.flip(true,false);
            facingRight = true;
        }
    }


    @Override
    public void draw(Batch batch) {
        if(vissible){
            super.draw(batch);
        }
    }

    public void setVissible(boolean vissible) {
        this.vissible = vissible;
    }

    public boolean isVissible() {
        return vissible;
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
}
