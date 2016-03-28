package parohyapp.mario.sprites.parent;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import parohyapp.mario.screens.PlayScreen;

/**
 * Created by tomas on 3/24/2016.
 */
public abstract class InteractiveSpriteEntity extends Entity{

    protected PlayScreen screen;
    private boolean vissible;

    public InteractiveSpriteEntity(World world, Rectangle bounds, PlayScreen screen){
        super(world,bounds);
        this.screen = screen;
        initTexture();
        setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
        vissible = true;
    }

    public abstract void initTexture();
    public abstract void onColide();

    @Override
    public void draw(Batch batch) {
        if(vissible){
            super.draw(batch);
        }
    }

    public void setVissible(boolean vissible) {
        this.vissible = vissible;
    }


}
