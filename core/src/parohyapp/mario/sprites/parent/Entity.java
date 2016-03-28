package parohyapp.mario.sprites.parent;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import parohyapp.mario.TowerClimber;
import parohyapp.mario.screens.PlayScreen;
import parohyapp.mario.sprites.Climber;
import parohyapp.mario.tools.Update;

/**
 * Created by tomas on 3/25/2016.
 */
public abstract class Entity extends Sprite implements Update{
    protected Rectangle bounds;
    protected World world;
    protected Body b2Body;

    protected Fixture fixture;

    public static short DEFAULT_BIT = 1;
    public static short CLIMBER_BIT = 2;
    public static short DIAMOND_BIT = 4;
    public static short DISPOSE_BIT = 8;

    public Entity(World world, Rectangle bounds) {
        this.bounds = bounds;
        this.world = world;

        initRectangle();
    }

    private void initCircle() {
        BodyDef bDef = new BodyDef();
        bDef.position.set(bounds.getX() / TowerClimber.PPM,bounds.getY() / TowerClimber.PPM);
        bDef.type = BodyDef.BodyType.DynamicBody;
        b2Body = world.createBody(bDef);

        FixtureDef fixDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(12 / TowerClimber.PPM);
        fixDef.shape = shape;
        b2Body.createFixture(fixDef);
    }

    private void initRectangle(){
        BodyDef bDef = new BodyDef();
        FixtureDef fixDef = new FixtureDef();
        PolygonShape pShape = new PolygonShape();

        if(this instanceof Climber){
            bDef.type = BodyDef.BodyType.DynamicBody;
            fixDef.filter.categoryBits = CLIMBER_BIT;
            fixDef.filter.maskBits = (short) (DEFAULT_BIT | DIAMOND_BIT);
        }
        else{
            fixDef.filter.categoryBits = DEFAULT_BIT;
            bDef.type = BodyDef.BodyType.StaticBody;
        }
        bDef.position.set((bounds.getX() + bounds.getWidth() / 2) / TowerClimber.PPM, (bounds.getY() + bounds.getHeight() / 2) / TowerClimber.PPM);
        b2Body = world.createBody(bDef);

        pShape.setAsBox(bounds.getWidth() / 2 / TowerClimber.PPM, bounds.getHeight() / 2 / TowerClimber.PPM);
        fixDef.shape = pShape;
        fixture = b2Body.createFixture(fixDef);
        fixture.setUserData(this);
    }

    public Body getB2Body() {
        return b2Body;
    }

    @Override
    public void update(float delta){
        setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
    }

    public void setCategoryFilter(short bit){
        Filter filter = new Filter();
        filter.categoryBits = bit;
        fixture.setFilterData(filter);
    }



}
