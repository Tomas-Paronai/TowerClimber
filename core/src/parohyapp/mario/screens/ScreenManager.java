package parohyapp.mario.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import parohyapp.mario.TowerClimber;

/**
 * Created by tomas on 3/31/2016.
 */
public class ScreenManager {

    private ScreenSet currentScreen;
    private TowerClimber game;

    public ScreenManager(TowerClimber game) {
        currentScreen = ScreenSet.MENU;
        this.game = game;
    }

    public ScreenSet getCurrentScreen() {
        return currentScreen;
    }

    public void setCurrentScreen(ScreenSet currentScreen) {
        this.currentScreen = currentScreen;
        game.setScreen(getScreen());
    }

    public Screen getScreen(){
        switch(currentScreen){
            case MENU:
                return new MenuScreen(this);
            case PLAY:
                return new PlayScreen(game,this);
            case EXIT:
                Gdx.app.exit();
            default:
                return null;
        }
    }
}
