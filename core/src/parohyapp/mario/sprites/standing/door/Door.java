package parohyapp.mario.sprites.standing.door;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import parohyapp.mario.TowerClimber;
import parohyapp.mario.screens.PlayScreen;
import parohyapp.mario.sprites.parent.InteractiveSpriteEntity;
import parohyapp.mario.tools.Resources;

/**
 * Created by tomas on 4/2/2016.
 */
public class Door extends InteractiveSpriteEntity{

    private DoorZ type;
    private boolean exit;
    private boolean open;

    public Door(World world, Rectangle bounds, PlayScreen screen, DoorZ type) {
        super(world, bounds, screen);
        this.type = type;
        initTexture();
        fixture.setSensor(true);
    }

    @Override
    public void initTexture() {
        if (type != null) {
            setBounds(0, 0, 16 / TowerClimber.PPM, 32 / TowerClimber.PPM);
            switch (type) {
                case FRONT:
                    setRegion(screen.getGameMaster().getAssetManager().get(Resources.DOOR_FRONT.toString(), Texture.class));
                    break;
                case BACK:
                    setRegion(screen.getGameMaster().getAssetManager().get(Resources.DOOR_BACK.toString(), Texture.class));
                    break;
                case BG:
                    setRegion(screen.getGameMaster().getAssetManager().get(Resources.DOOR_BG.toString(), Texture.class));
            }
        }
    }

    @Override
    public void onColide() {
        if(screen.getGameMaster().isFinished() && isExit() && isOpen()){
            screen.getGameMaster().nextLevel();
        }
    }

    @Override
    public void onColideEnd() {

    }

    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

    public boolean isOpen() {
        //return open;
        return true;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
