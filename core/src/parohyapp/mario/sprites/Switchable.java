package parohyapp.mario.sprites;

import parohyapp.mario.sprites.standing.switches.SwitchableType;

/**
 * Created by tomas on 4/3/2016.
 */
public interface Switchable {
    public void toggle();
    public SwitchableType getTag();
    public void setTag(SwitchableType tag);
    public void setConnectable(boolean conn);
    public boolean isConnectable();
}
