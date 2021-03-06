package parohyapp.mario.sprites.standing.switches;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

import parohyapp.mario.screens.PlayScreen;
import parohyapp.mario.sprites.Switchable;
import parohyapp.mario.sprites.lights.SignalLight.OneWaySignalLight;
import parohyapp.mario.sprites.lights.tools.LightChangeListener;
import parohyapp.mario.sprites.lights.tools.LightStatus;
import parohyapp.mario.sprites.parent.Entity;
import parohyapp.mario.sprites.parent.InteractiveSpriteEntity;

/**
 * Created by tomas on 4/3/2016.
 */
public abstract class Switch extends InteractiveSpriteEntity {
    private final static String TAG = "Switch";

    protected Animation toggleAnimation;
    protected boolean toggle;
    protected boolean lastToggle;
    private ArrayList<Switchable> listenerSwicth;
    private ArrayList<LightChangeListener> lightChangeListener;

    public Switch(World world, Rectangle bounds, PlayScreen screen) {
        super(world, bounds, screen);
        setCategoryFilter(Entity.SWITCH_BIT);
        fixture.setSensor(true);
    }

    public void initConnection(SwitchableType... type) {
        ArrayList<Switchable> switchables = worldManager.getAllSwitchables();
        for(Switchable tmpSwitchable : switchables){

            for(SwitchableType tmpType : type){

                if(tmpSwitchable.getTag() == tmpType && tmpSwitchable.isConnectable()){

                    if(tmpType == SwitchableType.SIGNAL){
                        setLightChangeListener((LightChangeListener) tmpSwitchable);
                    }
                    else{
                        setListener(tmpSwitchable);
                    }

                    break;
                }
            }

        }
    }

    public void initConnection(ArrayList<SwitchableType> type) {
        ArrayList<Switchable> switchables = worldManager.getAllSwitchables();
        for(Switchable tmpSwitchable : switchables){

            for(SwitchableType tmpType : type){

                if(tmpSwitchable.getTag() == tmpType && tmpSwitchable.isConnectable()){

                    if(tmpType == SwitchableType.SIGNAL){
                        Gdx.app.log(TAG,"Found Signal: "+tmpSwitchable);
                        setLightChangeListener((LightChangeListener) tmpSwitchable);
                    }
                    else{
                        Gdx.app.log(TAG,"Found Other: "+tmpSwitchable);
                        setListener(tmpSwitchable);
                    }
                    break;
                }
            }

        }
    }

    public void toggle(){
        toggle = !toggle;
    }



    @Override
    public void onColide() {
        toggle();

        if(listenerSwicth != null){
            Gdx.app.log(TAG,"toggle all switchables");
            for(Switchable tmpSwitch : listenerSwicth){
                tmpSwitch.toggle();
            }
        }

        if(lightChangeListener != null){
            Gdx.app.log(TAG,"toggle all light changers");
            for(LightChangeListener tmpSwitch : lightChangeListener){

                if(tmpSwitch instanceof OneWaySignalLight){
                    tmpSwitch.initLight(LightStatus.OPEN);
                }
                else{

                    if(tmpSwitch.getLightStatus() == LightStatus.LOCK){
                        tmpSwitch.initLight(LightStatus.OPEN);
                    }
                    else{
                        tmpSwitch.initLight(LightStatus.LOCK);
                    }
                }

            }
        }
    }

    public void setListener(Switchable listener) {
        if(listenerSwicth == null){
            listenerSwicth = new ArrayList<Switchable>();
        }
        listenerSwicth .add(listener);
    }

    public void setLightChangeListener(LightChangeListener listener) {
        if(lightChangeListener == null){
            lightChangeListener = new ArrayList<LightChangeListener>();
        }
        lightChangeListener.add(listener);
    }
}
