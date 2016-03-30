package parohyapp.mario.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import parohyapp.mario.TowerClimber;
import parohyapp.mario.screens.PlayScreen;
import parohyapp.mario.sprites.parent.Entity;
import parohyapp.mario.sprites.parent.InteractiveSpriteEntity;

/**
 * Created by tomas on 3/25/2016.
 */
public class Diamond extends InteractiveSpriteEntity {
    private static final String TAG = "Diamond";

    public Diamond(World world, Rectangle bounds, PlayScreen screen) {
        super(world, bounds, screen);
        setCategoryFilter(Entity.DIAMOND_BIT);
        fixture.setSensor(true);
    }


    @Override
    public void onColide() {
        Gdx.app.log(TAG, "Colided with " + TAG);
        setCategoryFilter(Entity.DISPOSE_BIT);
        setVissible(false);
        screen.getGameMaster().setScore(1);
        screen.getGameMaster().setNumberOfDiamonds(-1);
        screen.getGameMaster().getAssetManager().get("audio/score.mp3", Sound.class).play();
    }

    @Override
    public void initTexture() {
        Gdx.app.log(TAG,"Before "+getTexture());
        setRegion(screen.getGameMaster().getTextureByRegion32("diamond"));
        Gdx.app.log(TAG, "After " + getTexture());
        setBounds(0, 0, 32 / TowerClimber.PPM, 32 / TowerClimber.PPM);
    }
}
