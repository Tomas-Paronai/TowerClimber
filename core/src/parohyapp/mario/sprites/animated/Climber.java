package parohyapp.mario.sprites.animated;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import box2dLight.Light;
import parohyapp.mario.TowerClimber;
import parohyapp.mario.screens.PlayScreen;
import parohyapp.mario.sprites.lights.tools.LightSource;
import parohyapp.mario.sprites.parent.*;
import parohyapp.mario.tools.data.resources.Resources;
import parohyapp.mario.tools.data.resources.ResourcesUtil;

/**
 * Created by tomas on 3/24/2016.
 */
public class Climber extends InteractiveSpriteEntity implements HandleInput{
    private static final String TAG = "Climber";

    private Animation runningAnim;
    private Texture idle;
    private Texture jump;
    private Light headGlow;

    private Body torchBody;
    private Light torch;


    public Climber(World world, Rectangle bounds, PlayScreen screen) {
        super(world, bounds, screen);
        setCategoryFilter(CLIMBER_BIT);
        initLight();
        PolygonShape shape = (PolygonShape) fixture.getShape();
        setPolygonBox(shape, (bounds.getWidth() - 20) / 2, bounds.getHeight() / 2);

        initFootSensor();

    }

    private void initFootSensor() {
        EdgeShape footSensor = new EdgeShape();
        footSensor.set(new Vector2(-2 / TowerClimber.PPM, (-bounds.height/2) / TowerClimber.PPM), new Vector2(2 / TowerClimber.PPM, (-bounds.height/2) / TowerClimber.PPM));

        FixtureDef fixDef = new FixtureDef();
        fixDef.shape = footSensor;
        fixDef.isSensor = true;
        Fixture footFixture = b2Body.createFixture(fixDef);
        footFixture.setUserData("foot");

        Filter filter = new Filter();
        filter.maskBits = (short) (DEFAULT_BIT | CREEP_BIT);
        footFixture.setFilterData(filter);

    }

    private void initLight(){
        headGlow = LightSource.LightUtil.createPointLight(screen.getWorldManager().getRayHandler(), 30, Color.GREEN, 30, b2Body,0,5);
        Filter filter = new Filter();
        filter.maskBits = (short) (DEFAULT_BIT | CREEP_BIT);
        headGlow.setContactFilter(filter);


        //torch = LightSource.LightUtil.createConeLight(screen.getWorldManager().getRayHandler(),30,Color.WHITE, 150, 0, 20, b2Body);
        //initTorchLight();
    }

    private void initTorchLight(){
        //TODO set up torch
        BodyDef bDef = new BodyDef();
        FixtureDef fixDef = new FixtureDef();
        PolygonShape pShape = new PolygonShape();

        bDef.type = BodyDef.BodyType.DynamicBody;
        bDef.position.set((bounds.getX() + bounds.getWidth() / 2) / TowerClimber.PPM, (bounds.getY() + bounds.getHeight() / 2) / TowerClimber.PPM);
        torchBody = world.createBody(bDef);

        //(bounds.getWidth() - 20) / 2, bounds.getHeight() / 2
        pShape.setAsBox((bounds.getWidth() - 20) / TowerClimber.PPM, (bounds.getHeight() / 2) / TowerClimber.PPM);
        fixDef.shape = pShape;
        torchBody.createFixture(fixDef).setFilterData(fixture.getFilterData());

        torch = LightSource.LightUtil.createConeLight(screen.getWorldManager().getRayHandler(),30,Color.WHITE, 150, 0, 20, torchBody);
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
        if(Gdx.input.isTouched() && isOnGround()){
            jump();
        }

        if(getB2Body().getLinearVelocity().x <= Math.abs(2)){
            float speed = 0;
            if(Math.abs(Gdx.input.getAccelerometerX()) > 1){
                speed = Gdx.input.getAccelerometerX()*delta*(-1);
            }
            if(speed > 2){
                speed = 2f;
            }

            setVectorImpulse(speed,0);
        }
    }

    public void jump(){
        setVectorImpulse(0,7f);
        screen.getGameMaster().getAssetManager().get(Resources.A_JUMP.toString(), Sound.class).play();
    }

    private void setVectorImpulse(float x, float y){
        b2Body.applyLinearImpulse(new Vector2(x,y),b2Body.getWorldCenter(),true);
        //torchBody.applyLinearImpulse(new Vector2(x,y),torchBody.getWorldCenter(),true);
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
