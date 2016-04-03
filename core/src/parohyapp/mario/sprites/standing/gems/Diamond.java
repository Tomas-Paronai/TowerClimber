package parohyapp.mario.sprites.standing.gems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import parohyapp.mario.screens.PlayScreen;
import parohyapp.mario.tools.Resources;

/**
 * Created by tomas on 4/2/2016.
 */
public class Diamond extends Gemstone {
    private final static String TAG = "Diamond";

    public Diamond(World world, Rectangle bounds, PlayScreen screen) {
        super(world, bounds, screen, 1);
    }

    @Override
    public void onColide() {
        if(isVissible()){
            screen.getGameMaster().setNumberOfGemstones(-1);
            super.onColide();
        }
    }

    @Override
    public void initTexture() {
        super.initTexture();
        setRegion(screen.getGameMaster().getAssetManager().get(Resources.DIAMOND.toString(), Texture.class));
    }
}
