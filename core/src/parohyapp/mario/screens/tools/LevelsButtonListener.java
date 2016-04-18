package parohyapp.mario.screens.tools;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import parohyapp.mario.GameMaster;

/**
 * Created by tomas on 4/18/2016.
 */
public class LevelsButtonListener extends ChangeListener {

    private int value;
    private MenuControl control;

    public LevelsButtonListener(int value, MenuControl control) {
        this.value = value;
        this.control = control;
    }

    @Override
    public void changed(ChangeEvent event, Actor actor) {
        new GameMaster(value);
        control.changeScreen(ScreenSet.PLAY);
    }
}
