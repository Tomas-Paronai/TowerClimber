package parohyapp.mario.sprites.standing.gems;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import parohyapp.mario.screens.PlayScreen;
import parohyapp.mario.tools.Resources;

/**
 * Created by tomas on 4/2/2016.
 */
public class Ruby extends Gemstone {

    public Ruby(World world, Rectangle bounds, PlayScreen screen) {
        super(world, bounds, screen, 5);
    }

    @Override
    public void initTexture() {
        super.initTexture();
        setRegion(screen.getGameMaster().getAssetManager().get(Resources.RUBY.toString(), Texture.class));
    }
}
