package parohyapp.mario.sprites.animated;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import parohyapp.mario.TowerClimber;
import parohyapp.mario.screens.PlayScreen;
import parohyapp.mario.sprites.parent.InteractiveSpriteEntity;

/**
 * Created by tomas on 3/30/2016.
 */
public abstract class Creep extends InteractiveSpriteEntity {
    private static final String TAG = "Creep";



    private Vector2 moveVector;

    public Creep(World world, Rectangle bounds, PlayScreen screen) {
        super(world, bounds, screen);
        facingRight = true;
        moveVector = new Vector2(1f,0);
    }


    public void onColide() {
        switchDirection();
    }

    @Override
    public void onColideEnd() {

    }

    @Override
    public void update(float delta) {
        getFrame(delta);
        if(currentState != State.DYING){
            move();
        }
        act(delta);
        super.update(delta);
    }

    public void move(){
        b2Body.setLinearVelocity(moveVector);
    }

    public abstract void act(float delta);

    public void switchDirection(){
        facingRight = facingRight == true ? false : true;
        if(moveVector.x > 0 && !facingRight){
            moveVector.x = -moveVector.x;
        }
        else if(moveVector.x < 0 && facingRight){
            moveVector.x = -moveVector.x;
        }
    }
}
