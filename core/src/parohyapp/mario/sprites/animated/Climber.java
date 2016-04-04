package parohyapp.mario.sprites.animated;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import box2dLight.Light;
import parohyapp.mario.TowerClimber;
import parohyapp.mario.screens.PlayScreen;
import parohyapp.mario.sprites.lights.tools.LightSource;
import parohyapp.mario.sprites.parent.*;
import parohyapp.mario.tools.Resources;
import parohyapp.mario.tools.ResourcesUtil;

/**
 * Created by tomas on 3/24/2016.
 */
public class Climber extends InteractiveSpriteEntity implements HandleInput{
    private static final String TAG = "Climber";

    private Animation runningAnim;
    private Texture idle;
    private Texture jump;
    private Light headGlow;
    private Light torch;

    public Climber(World world, Rectangle bounds, PlayScreen screen) {
        super(world, bounds, screen);
        setCategoryFilter(CLIMBER_BIT);
        initLight();
        PolygonShape shape = (PolygonShape) fixture.getShape();
        setPolygonBox(shape,(bounds.getWidth()-20) / 2,bounds.getHeight() / 2 );
    }

    private void initLight(){
        headGlow = LightSource.LightUtil.createPointLight(screen.getWorldManager().getRayHandler(), 30, Color.GREEN, 30, b2Body,0,5);
        Filter filter = new Filter();
        filter.maskBits = (short) (DEFAULT_BIT | CREEP_BIT);
        headGlow.setContactFilter(filter);

        //TODO set up torch
        //torch = LightSource.LightUtil.createConeLight(screen.getRayHandler(),30,Color.WHITE, 150, 0, 20, b2Body);
    }

    @Override
    public void initTexture() {
        setBounds(0, 0, 32 / TowerClimber.PPM, 32 / TowerClimber.PPM);
        setRegion(screen.getGameMaster().getAssetManager().get(Resources.CLIMBER_IDLE.toString(), Texture.class));

        Array<TextureRegion> frames = ResourcesUtil.getFramesArray(screen.getGameMaster().getAssetManager().get(Resources.CLIMBER_RUNNING.toString(), Texture.class), 32);
        runningAnim = new Animation(0.15f,frames);
        idle = screen.getGameMaster().getAssetManager().get(Resources.CLIMBER_IDLE.toString(), Texture.class);
        jump = screen.getGameMaster().getAssetManager().get(Resources.CLIMBER_JUMP.toString(), Texture.class);
    }

    @Override
    public void update(float delta) {
        handleInput(delta);
        getFrame(delta);
        changeLightDirection();
        super.update(delta);
    }

    @Override
    public void handleInput(float delta) {
        //TODO fix double jump
        if(Gdx.input.isTouched() && currentState != State.FALLING && currentState != State.JUMPING){
            getB2Body().applyLinearImpulse(new Vector2(0,7f),getB2Body().getWorldCenter(),true);
            screen.getGameMaster().getAssetManager().get(Resources.A_JUMP.toString(), Sound.class).play();
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

    @Override
    public void getFrame(float delta){
        super.getFrame(delta);
        //currentState = getState();
        TextureRegion tmpTexture;

        if(currentState == State.IDLE){
            //Gdx.app.log(TAG,"I am idle.");
            tmpTexture = new TextureRegion(idle);
        }
        else if(currentState == State.JUMPING || currentState == State.FALLING){
            //Gdx.app.log(TAG, "I am jumping. ");
            tmpTexture = new TextureRegion(jump);
        }
        else{
            //Gdx.app.log(TAG, "I am running. ");
            tmpTexture = runningAnim.getKeyFrame(stateTime,true);
        }

        //stateTime = currentState == previousState ? stateTime += delta : 0;
        //previousState = currentState;

        if(tmpTexture == null){
            tmpTexture = new TextureRegion(idle);
        }

        setRegion(tmpTexture);
    }

    @Override
    public void onColide() {
        screen.getGameMaster().setLives(-1);
    }

    @Override
    public void onColideEnd() {

    }

    private void changeLightDirection(){
        //TODO change torch direction
    }
}
