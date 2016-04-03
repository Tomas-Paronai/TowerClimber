package parohyapp.mario.sprites.standing.door;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

import parohyapp.mario.screens.PlayScreen;
import parohyapp.mario.sprites.Switchable;
import parohyapp.mario.sprites.standing.switches.Switch;

/**
 * Created by tomas on 4/3/2016.
 */
public class LockDoor extends Door implements Switchable{

    private ArrayList<Switch> switches;

    public static int amount = 0;
    private int id;

    public LockDoor(World world, Rectangle bounds, PlayScreen screen, DoorZ type) {
        super(world, bounds, screen, type);
        LockDoor.amount++;
        id = amount;
    }

    public void addControlReference(Switch control){
        switches.add(control);
    }

    public boolean isUnlocked(){
        if(switches != null){
            for(Switch tmpSwitch : switches){
                if(!tmpSwitch.isToggle()){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void toggle() {
        if(isUnlocked()){
            setOpen(true);
        }
    }
}
